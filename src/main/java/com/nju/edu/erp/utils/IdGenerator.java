package com.nju.edu.erp.utils;

import com.nju.edu.erp.enums.Role;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class IdGenerator {
    /**
     * 获取新单号
     * @param id 上一次的单号
     * @return 新的单号
     */
    public static String generateSheetId(String id, String prefix) { // "{prefix}-20220216-00000"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());
        if(id == null) {
            return prefix + "-" + today + "-" + String.format("%05d", 0);
        }
        String lastDate = id.split("-")[1];
        if(lastDate.equals(today)) {
            String prevNum = id.split("-")[2];
            return prefix + "-" + today + "-" + String.format("%05d", Integer.parseInt(prevNum) + 1);
        } else {
            return prefix + "-" + today + "-" + String.format("%05d", 0);
        }
    }

    /**
     * 获取员工新编号
     * @param id 最新的员工编号
     * @return 新的员工编号
     */
    public static String generateEmployeeId(String id){
        if (id == null)
            return "A0001";
        //只处理编号形式为‘A4396’形式的
        String alpha = id.substring(0,1);
        String number = id.substring(1);
        if (Integer.parseInt(number) < 9999){
            return alpha + String.format("%04d", Integer.parseInt(number) + 1);
        } else{
            return String.valueOf(alpha.charAt(0) + 1) + String.format("%04d",1);
        }
    }

    /**
     * 获取打卡记录编号
     * @param employeeId 员工编号
     * @param date 日期
     * @return 打卡记录编号
     */
    public static String generateClockInRecordId(String employeeId, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(date);
        return "CIR" + "-" + employeeId + "-" + dateString;
    }
}
