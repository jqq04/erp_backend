package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.SalaryDao;
import com.nju.edu.erp.model.vo.EmployeeVO;
import com.nju.edu.erp.model.vo.salary.FinalBonusVO;
import com.nju.edu.erp.strategy.promotion.offer.PromotionOfferStrategyContext;
import com.nju.edu.erp.strategy.salary.payment.Impl.AnnuallySalaryPaymentStrategy;
import com.nju.edu.erp.strategy.salary.payment.Impl.MonthlySalaryPaymentStrategy;
import com.nju.edu.erp.strategy.salary.payment.SalaryPaymentStrategy;
import com.nju.edu.erp.strategy.salary.payment.SalaryPaymentStrategyContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class SalaryTest {

    @Autowired
    private SalaryService salaryService;
    @Autowired
    private SalaryDao salaryDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SalaryPaymentStrategyContext salaryPaymentStrategyContext;

    @Test
    @Transactional
    @Rollback(value = true)
    public void makeSheetTest() throws ParseException {
        try {
            List<EmployeeVO> employeeVOS = employeeService.getAllEmployee();
            for (EmployeeVO employee : employeeVOS) {
                if (employee.getSalaryPaymentStrategy().equals("按月薪发放")) {
                    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate = simpleDateFormat.parse("2022-06-01");
                    Date endDate = simpleDateFormat.parse("2022-06-30");

                    salaryService.makeSheet(employee.getId(), beginDate, endDate);
                } else if (employee.getSalaryPaymentStrategy().equals("按年薪发放")) {
                    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate = simpleDateFormat.parse("2020-12-16");
                    Date endDate = simpleDateFormat.parse("2021-12-15");

                    salaryService.makeSheet(employee.getId(), beginDate, endDate);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void getFinalBonusTest() {
        List<FinalBonusVO> finalBonusList = salaryService.getFinalBonusList();
        Assertions.assertNotNull(finalBonusList);
        Assertions.assertFalse(finalBonusList.isEmpty());
        // Assertions.assertNotEquals(BigDecimal.ZERO,yearSalarySumList.get(0).getYearSalarySum());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void setFinalBonusTest1() {
        salaryService.setFinalBonus("A0001", new BigDecimal("30000.00"));
        Assertions.assertEquals(0, new BigDecimal("30000").compareTo(salaryDao.getFinalBonus("A0001")));
    }

    /**
     * 不用计时器，直接触发paySalary()
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void Driver_paySalary() {
        List<EmployeeVO> employeeVOS = employeeService.getAllEmployee();
        for (EmployeeVO employee : employeeVOS) {
            // 通过context获取工资发放策略
            SalaryPaymentStrategy salaryPaymentStrategy = salaryPaymentStrategyContext
                    .getResource(employee.getSalaryPaymentStrategy());

            // 验证获取的工资发放策略是否正确
            if (employee.getSalaryPaymentStrategy().equals("按月薪发放"))
                Assertions.assertEquals(salaryPaymentStrategy.getClass(), MonthlySalaryPaymentStrategy.class);
            if (employee.getSalaryPaymentStrategy().equals("按年薪发放"))
                Assertions.assertEquals(salaryPaymentStrategy.getClass(), AnnuallySalaryPaymentStrategy.class);

            if (salaryPaymentStrategy.isPayDay())
                salaryService.makeSheet(employee.getId(), salaryPaymentStrategy.beginDate(),
                        salaryPaymentStrategy.endDate());
        }
    }
    // @Test
    // @Transactional
    // @Rollback(value = false)
    // public void makeSheet() throws ParseException {
    // List<EmployeeVO> employeeVOS = employeeService.getAllEmployee();
    // for (EmployeeVO employee:employeeVOS){
    // if (employee.getSalaryPaymentStrategy().equals("按月薪发放")) {
    // DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // Date beginDate = simpleDateFormat.parse("2022-05-01");
    // Date endDate = simpleDateFormat.parse("2022-05-30");
    //
    // salaryService.makeSheet(employee.getId(), beginDate, endDate);
    // }
    // else if (employee.getSalaryPaymentStrategy().equals("按年薪发放")){
    // DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // Date beginDate = simpleDateFormat.parse("2019-12-16");
    // Date endDate = simpleDateFormat.parse("2020-12-15");
    //
    // salaryService.makeSheet(employee.getId(), beginDate, endDate);
    // }
    // }
    // }
}
