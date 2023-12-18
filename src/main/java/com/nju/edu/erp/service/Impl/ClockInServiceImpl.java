package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.ClockInDao;
import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.ClockInRecordPO;
import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.EmployeeVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.ClockInService;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ClockInServiceImpl implements ClockInService {

    private final ClockInDao clockInDao;

    private final EmployeeService employeeService;

    private final EmployeeDao employeeDao;

    private final UserDao userDao;

    @Autowired
    public ClockInServiceImpl(ClockInDao clockInDao, EmployeeService employeeService, EmployeeDao employeeDao, UserDao userDao) {
        this.clockInDao = clockInDao;
        this.employeeService = employeeService;
        this.employeeDao = employeeDao;
        this.userDao = userDao;
    }

    @Override
    public void clockIn(UserVO userVO) {
        //先根据用户信息，找到对应的员工信息，再进行打卡
        User user = userDao.findByUsername(userVO.getName());
        EmployeePO employeePO = employeeDao.findEmployeeByUserId(user.getId());
        if (employeePO == null)
            throw new MyServiceException("H0001","找不到用户对应的员工信息");
        String employeeId = employeePO.getId();

        //查询是否已经打过卡
        ClockInRecordPO clockInRecordPO = clockInDao.findRecordByEmployeeIdByDate(employeeId, new Date());
        if (clockInRecordPO.isHasClockIn())
            throw new MyServiceException("H0002","该用户今日已完成打卡");
        clockInDao.clockIn(employeeId, new Date());
    }

    /**
     * 系统在每日零点为所有员工自动生成默认打卡记录（即是否打卡一栏为否）
     */
    @Override
    @Scheduled(cron = "0 0 0 * * ?") //每天零点自动执行
    @Transactional
    public void makeDefaultRecordOnTime(){
        makeDefaultRecord();
        System.out.println(new Date() + "零点，所有员工默认打卡记录已生成。");
    }

    /**
     * 该方法会被调用
     * 系统在启动时，检查数据库是否存在当日的打卡记录，若不存在，则生成默认打卡记录
     */
    @Override
    @Transactional
    public void makeDefaultRecordOnStartup(){
        List<ClockInRecordPO> clockInRecordPOList = clockInDao.findRecordByDate(new Date());
        if (clockInRecordPOList.isEmpty()){
            System.out.println("项目启动，开始生成所有员工默认打卡记录。");
            makeDefaultRecord();
        }
        System.out.println("所有员工默认打卡记录已生成。");
    }

    /**
     * 为所有员工生成一个当日的默认打卡记录
     * 该方法用以减少重复耦合，被其他方法调用
     */
    private void makeDefaultRecord() {
        List<EmployeeVO> employeeVOS = employeeService.getAllEmployee();
        for (EmployeeVO employeeVO : employeeVOS) {
            Date date = new Date();
            //获取打卡记录编号
            String id = IdGenerator.generateClockInRecordId(employeeVO.getId(), date);
            ClockInRecordPO clockInRecordPO = ClockInRecordPO.builder()
                    .id(id)
                    .employeeId(employeeVO.getId())
                    .date(date)
                    .time(null)
                    .hasClockIn(false)
                    .build();
            clockInDao.makeRecord(clockInRecordPO);
        }
    }

    /**
     * 获取用户全部打卡记录
     * @param userVO 用户信息
     * @return 用户全部打卡记录
     */
    @Override
    public List<ClockInRecordPO> getAllPersonalRecord(UserVO userVO) {
        //先根据用户信息，找到对应的员工信息
        User user = userDao.findByUsername(userVO.getName());
        EmployeePO employeePO = employeeDao.findEmployeeByUserId(user.getId());
        if (employeePO == null)
            throw new MyServiceException("H0001","找不到用户对应的员工信息");
        return clockInDao.findAllRecordByEmployeeId(employeePO.getId());
    }


    /**
     * 获取用户当日打卡记录
     * @param userVO 用户信息
     * @return 用户当日打卡记录
     */
    @Override
    public ClockInRecordPO getPersonalRecordOnCurrentDay(UserVO userVO) {
        //先根据用户信息，找到对应的员工信息
        User user = userDao.findByUsername(userVO.getName());
        EmployeePO employeePO = employeeDao.findEmployeeByUserId(user.getId());
        if (employeePO == null)
            throw new MyServiceException("H0001","找不到用户对应的员工信息");
        return clockInDao.findRecordByEmployeeIdByDate(employeePO.getId(), new Date());
    }

    /**
     * 获取用户在某一时间段内的打卡记录
     * @param userVO 用户信息
     * @param beginDateStr 开始日期 "yyyy-MM-dd"
     * @param endDateStr 结束日期 "yyyy-MM-dd"
     * @return 用户在该时间段内的打卡记录
     */
    @Override
    public List<ClockInRecordPO> getPersonalRecordByDate(UserVO userVO, String beginDateStr, String endDateStr) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginDate = simpleDateFormat.parse(beginDateStr);
            Date endDate = simpleDateFormat.parse(endDateStr);
            if (beginDate.compareTo(endDate)>0)
                return null;

            //先根据用户信息，找到对应的员工信息
            User user = userDao.findByUsername(userVO.getName());
            EmployeePO employeePO = employeeDao.findEmployeeByUserId(user.getId());
            if (employeePO == null)
                throw new MyServiceException("H0001","找不到用户对应的员工信息");

            return clockInDao.findRecordByEmployeeIdByDateRange(employeePO.getId(), beginDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取全体员工打卡记录信息
     * @return 全体员工打卡记录信息
     */
    @Override
    public List<ClockInRecordPO> getAllRecord() {
        return clockInDao.findAllRecord();
    }


    /**
     * 获取全体员工当日打卡记录信息
     * @return 全体员工当日打卡记录信息
     */
    @Override
    public List<ClockInRecordPO> getAllRecordOnCurrentDay() {
        return clockInDao.findRecordByDate(new Date());
    }

    /**
     * 获取员工在一段日期内的缺勤天数
     * @param employeeId 员工编号
     * @param beginDate 起始日期
     * @param endDate 截止日期
     * @return 缺勤天数
     */
    @Override
    public Integer getAbsenceDays(String employeeId, Date beginDate, Date endDate) {
        return clockInDao.getAbsenceDays(employeeId,beginDate,endDate);
    }

    /**
     * 获取员工在一段日期内的出勤天数
     * @param employeeId 员工编号
     * @param beginDate 起始日期
     * @param endDate 截止日期
     * @return 出勤天数
     */
    @Override
    public Integer getAttendanceDays(String employeeId, Date beginDate, Date endDate) {
        return clockInDao.getAttendanceDays(employeeId,beginDate,endDate);
    }
}
