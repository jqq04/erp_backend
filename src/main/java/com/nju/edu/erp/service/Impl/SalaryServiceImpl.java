package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.dao.SalaryDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.po.salary.SalaryComputeInfoNeeded;
import com.nju.edu.erp.model.po.salary.SalaryInfo;
import com.nju.edu.erp.model.po.salary.SalarySheetPO;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.model.vo.EmployeeVO;
import com.nju.edu.erp.model.vo.salary.FinalBonusVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.ClockInService;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.strategy.salary.compute.SalaryComputeStrategy;
import com.nju.edu.erp.strategy.salary.compute.SalaryComputeStrategyContext;
import com.nju.edu.erp.strategy.salary.payment.SalaryPaymentStrategy;
import com.nju.edu.erp.strategy.salary.payment.SalaryPaymentStrategyContext;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryDao salaryDao;

    private final EmployeeDao employeeDao;

    private final ClockInService clockInService;

    private final SaleSheetDao saleSheetDao;

    private final AccountService accountService;

    private final EmployeeService employeeService;

    private final SalaryComputeStrategyContext salaryComputeStrategyContext;

    private final SalaryPaymentStrategyContext salaryPaymentStrategyContext;

    @Autowired
    public SalaryServiceImpl(SalaryDao salaryDao, EmployeeDao employeeDao, ClockInService clockInService, SaleSheetDao saleSheetDao, AccountService accountService, SalaryComputeStrategyContext salaryComputeStrategyContext, EmployeeService employeeService, SalaryPaymentStrategyContext salaryPaymentStrategyContext) {
        this.salaryDao = salaryDao;
        this.employeeDao = employeeDao;
        this.clockInService = clockInService;
        this.saleSheetDao = saleSheetDao;
        this.accountService = accountService;
        this.salaryComputeStrategyContext = salaryComputeStrategyContext;
        this.employeeService = employeeService;
        this.salaryPaymentStrategyContext = salaryPaymentStrategyContext;
    }

    @Override
    @Transactional
    public void makeSheet(String employeeId, Date beginDate, Date endDate) {
        SalarySheetPO salarySheetPO = new SalarySheetPO();
        //填写单据编号
        SalarySheetPO latest = salaryDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null?null:latest.getId(), "GZD");
        salarySheetPO.setId(id);

        //填写单据创建时间
        salarySheetPO.setCreateTime(new Date());
        //填写工资单计算的起始日期和结束日期
        salarySheetPO.setBeginDate(beginDate);
        salarySheetPO.setEndDate(endDate);

        //填写出勤天数，缺勤天数
        Integer attendanceDays = clockInService.getAttendanceDays(employeeId, beginDate, endDate);
        Integer absenceDays = clockInService.getAbsenceDays(employeeId, beginDate, endDate);
        salarySheetPO.setAttendanceDays(attendanceDays);
        salarySheetPO.setAbsenceDays(absenceDays);

        //填写工资单中的员工编号、姓名、银行卡账户
        EmployeePO employee = employeeDao.getEmployeeById(employeeId);
        salarySheetPO.setEmployeeId(employeeId);
        salarySheetPO.setName(employee.getName());
        salarySheetPO.setAccount(employee.getAccount());

        //获取提成比例和销售额总计，如果不是销售人员，销售额总计的结果是0
        User user = employeeDao.findUserByEmployeeId(employeeId);//将员工编号转为用户名
        BigDecimal totalSaleAmount = saleSheetDao.getTotalSaleAmount(user.getName(), beginDate, endDate);
        BigDecimal commissionRate = salaryDao.getCommissionRate();
        //获取年终奖信息
        BigDecimal yearEndBonus = salaryDao.getFinalBonus(employeeId);
        //通过策略资源池上下文，生产对应薪资计算策略，计算出对应薪资信息并填写
        SalaryComputeStrategy salaryComputeStrategy = salaryComputeStrategyContext.getResource(employee.getSalaryComputeStrategy());
        //封装薪资计算策略需要的信息
        SalaryComputeInfoNeeded infoNeeded = SalaryComputeInfoNeeded.builder()
                .employee(employee)
                .absencesDays(absenceDays)
                .totalSaleAmount(totalSaleAmount)
                .commissionRate(commissionRate)
                .yearEndBonus(yearEndBonus)
                .build();
        SalaryInfo salaryInfo = salaryComputeStrategy.compute(infoNeeded);
        BeanUtils.copyProperties(salaryInfo, salarySheetPO);

        //填写单据状态
        salarySheetPO.setState(SalarySheetState.PENDING);
        //保存单据
        salaryDao.saveSheet(salarySheetPO);
    }

    @Override
    @Transactional
    public void approval(String salarySheetId, SalarySheetState state) {
        SalarySheetPO salarySheetPO = salaryDao.findOneById(salarySheetId);

        if (state.equals(SalarySheetState.PENDING))
            throw new MyServiceException("F0001","更改状态失败!不能更改状态为PENDING");
        if (salarySheetPO.getState().equals(SalarySheetState.FAILURE) || salarySheetPO.getState().equals(SalarySheetState.SUCCESS))
            throw new MyServiceException("F0002","更新状态失败!单据已经被审批！");

        salaryDao.updateState(salarySheetId, state);
        if (state.equals(SalarySheetState.SUCCESS)){
            //审核成功，修改公司专用工资发放账户数据
            AccountVO salary_payment_account = accountService.getSalaryPaymentAccount();
            salary_payment_account.setAmount(salary_payment_account.getAmount().subtract(salarySheetPO.getExpectedSalary())); //公司支付应付薪水
            accountService.updateAccount(salary_payment_account);

            //如果是十二月份发放的工资，会将年终奖信息进行重置
            if (salarySheetPO.getBeginDate().getMonth() == Calendar.DECEMBER)
                salaryDao.setFinalBonus(salarySheetPO.getEmployeeId(), null);
        }
        if (state.equals(SalarySheetState.FAILURE)){
            //如果审核失败，系统重新生成新的工资单
            makeSheet(salarySheetPO.getEmployeeId(), salarySheetPO.getBeginDate(), salarySheetPO.getEndDate());
        }
    }

    @Override
    public List<SalarySheetPO> getSheetByState(SalarySheetState state) {
        if (state == null)
            return salaryDao.findAll();
        else
            return salaryDao.findAllByState(state);
    }

    @Override
    public void setFinalBonus(String employeeId, BigDecimal finalBonus){
        salaryDao.setFinalBonus(employeeId,finalBonus);
    }

    @Override
    public List<FinalBonusVO> getFinalBonusList() {
        List<FinalBonusVO> res = new ArrayList<>();
        List<EmployeePO> employeeList = employeeDao.getAllEmployee();
        for (EmployeePO employee:employeeList)
        if (!employee.getRole().equals(Role.GM) && !employee.getRole().equals(Role.ADMIN)){ //得不是总经理或者admin
            FinalBonusVO finalBonusVO = new FinalBonusVO();
            finalBonusVO.setEmployeeId(employee.getId());
            finalBonusVO.setName(employee.getName());

            Date date = new Date();
            Date beginDate = new Date(date.getYear(), Calendar.JANUARY, 1);
            Date endDate = new Date(date.getYear(), Calendar.DECEMBER,20);
            finalBonusVO.setYearSalarySum(salaryDao.computeSalarySum(employee.getId(),beginDate,endDate));

            finalBonusVO.setFinalBonus(salaryDao.getFinalBonus(employee.getId()));
            res.add(finalBonusVO);
        }
        return res;
    }

    /**
     *
     */
    @Override
    @Scheduled(cron = "0 1 0 * * ?") //每天24:01都自动触发一次
    public void paySalary(){
        System.out.println("零点，开始检查是否有需要员工需要发薪。");
        List<EmployeeVO> employeeVOS = employeeService.getAllEmployee();
        for (EmployeeVO employee:employeeVOS){
            //通过context获取工资发放策略
            SalaryPaymentStrategy salaryPaymentStrategy = salaryPaymentStrategyContext.getResource(employee.getSalaryPaymentStrategy());
            //如果是发薪日，则调用制作工资单
            if (salaryPaymentStrategy.isPayDay())
                makeSheet(employee.getId(),salaryPaymentStrategy.beginDate(),salaryPaymentStrategy.endDate());
        }
    }
}
