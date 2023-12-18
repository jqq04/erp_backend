package com.nju.edu.erp.strategy.salary.payment.Impl;

import com.nju.edu.erp.strategy.salary.payment.SalaryPaymentStrategy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component("按年薪发放")
public class AnnuallySalaryPaymentStrategy implements SalaryPaymentStrategy {

    /**
     * 每年12月5日是发薪日
     */
    @Override
    public boolean isPayDay(){
        Date date = new Date();
        return date.getMonth() == Calendar.DECEMBER && date.getDate() == 15;
    }
    /**
     * @return 去年12月1日
     */
    public Date beginDate(){
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        int year = date.getYear() - 1;
        int month = Calendar.DECEMBER;
        int day = 1;
        return new Date(year, month, day);
    }
    /**
     * @return 该年11月30日
     */
    @Override
    public Date endDate() {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        int year = date.getYear();
        int month = Calendar.NOVEMBER;
        int day = 30;
        return new Date(year, month, day);
    }
}
