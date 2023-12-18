package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.ClockInRecordPO;
import com.nju.edu.erp.model.vo.UserVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ClockInService {
    /**
     * 员工打卡
     * @param userVO 用户信息
     * if code = 'H0001' then msg = '找不到用户对应的员工信息'
     *    code = 'H0002'      msg = '该用户今日已完成打卡'
     */
    void clockIn(UserVO userVO);

    /**
     * 系统在每日零点为所有员工自动生成默认打卡记录（即是否打卡一栏为否）
     */
    void makeDefaultRecordOnTime();

    /**
     * 该方法会被ClockInStartupRunner调用
     * 系统在启动时，检查数据库是否存在当日的打卡记录，若不存在，则生成默认打卡记录
     */
    void makeDefaultRecordOnStartup();

    /**
     * 获取用户全部打卡记录
     * @param userVO 用户信息
     * @return 用户全部打卡记录
     */
    List<ClockInRecordPO> getAllPersonalRecord(UserVO userVO);

    /**
     * 获取用户当日打卡记录
     * @param userVO 用户信息
     * @return 用户当日打卡记录
     */
    ClockInRecordPO getPersonalRecordOnCurrentDay(UserVO userVO);

    /**
     * 获取用户在某一时间段内的打卡记录
     * @param userVO 用户信息
     * @param beginDateStr 开始日期 "yyyy-MM-dd"
     * @param endDateStr 结束日期 "yyyy-MM-dd"
     * @return 用户在该时间段内的打卡记录
     */
    List<ClockInRecordPO> getPersonalRecordByDate(UserVO userVO, String beginDateStr, String endDateStr);

    /**
     * 获取全体员工打卡记录信息
     * @return 全体员工打卡记录信息
     */
    List<ClockInRecordPO> getAllRecord();

    /**
     * 获取全体员工当日打卡记录信息
     * @return 全体员工当日打卡记录信息
     */
    List<ClockInRecordPO> getAllRecordOnCurrentDay();

    /**
     * 获取员工在一段日期内的缺勤天数
     * @param employeeId 员工编号
     * @param beginDate 起始日期
     * @param endDate 截止日期
     * @return 缺勤天数
     */
    Integer getAbsenceDays(String employeeId, Date beginDate, Date endDate);

    /**
     * 获取员工在一段日期内的出勤天数
     * @param employeeId 员工编号
     * @param beginDate 起始日期
     * @param endDate 截止日期
     * @return 出勤天数
     */
    Integer getAttendanceDays(String employeeId, Date beginDate, Date endDate);
}
