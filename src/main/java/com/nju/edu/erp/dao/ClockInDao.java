package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.ClockInRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface ClockInDao {
    /**
     * 员工打卡，会更改数据库中对应打卡记录信息
     * @param employeeId 员工编号
     * @return 影响的行数
     */
    int clockIn(String employeeId, Date date);

    /**
     * 向数据库插入一条新的打卡记录
     * @param clockInRecordPO 打卡记录
     * @return 影响的行数
     */
    int makeRecord(ClockInRecordPO clockInRecordPO);

    /**
     * 根据员工编号寻找所有打卡记录
     * @param employeeId 员工编号
     * @return 该员工的所有打卡记录
     */
    List<ClockInRecordPO> findAllRecordByEmployeeId(String employeeId);

    /**
     * 根据员工编号寻找指定日期的打卡记录
     * @param employeeId 员工编号
     * @param date 日期
     * @return 该员工的指定日期的打卡记录
     */
    ClockInRecordPO findRecordByEmployeeIdByDate(String employeeId, Date date);

    /**
     * 根据员工编号寻找制定日期的打卡记录
     * @param employeeId 员工编号
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 该员工的指定日期的打卡记录
     */
    List<ClockInRecordPO> findRecordByEmployeeIdByDateRange(String employeeId, Date beginDate, Date endDate);

    /**
     * 寻找所有员工的所有打卡记录
     * @return 所有员工的所有打卡记录，按日期与员工编号从新到旧排序
     */
    List<ClockInRecordPO> findAllRecord();

    /**
     * 寻找所有员工的制定日期的打卡记录
     * @param date 日期
     * @return 制定日期的打卡记录，按员工编号排序
     */
    List<ClockInRecordPO> findRecordByDate(Date date);

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
