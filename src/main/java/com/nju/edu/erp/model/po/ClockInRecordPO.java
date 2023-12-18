package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClockInRecordPO {
    /**
     * 打卡记录编号, 格式：CIR-A0001-yyyyMMdd
     */
    private String id;
    /**
     * 打卡员工编号，格式：A0001
     */
    private String employeeId;
    /**
     * 日期, yyyy-MM-dd
     */
    private Date date;
    /**
     * 打卡时间 yyyy-MM-dd HH:mm:ss
     */
    private Date time;
    /**
     * 当日是否打卡
     */
    private boolean hasClockIn;
}
