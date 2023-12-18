package com.nju.edu.erp.strategy.salary.payment.Impl;

import com.nju.edu.erp.strategy.salary.payment.SalaryPaymentStrategy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Component("按月薪发放")
public class MonthlySalaryPaymentStrategy implements SalaryPaymentStrategy {

    /**
     * 每月5日是发薪日
     */
    @Override
    public boolean isPayDay(){
        Date date = new Date();
        return date.getDate() == 5;
    }

    /**
     * @return 上个月1日
     */
    @Override
    public Date beginDate(){
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        int year = date.getYear();
        int month = date.getMonth()== Calendar.JANUARY ? Calendar.DECEMBER : date.getMonth()-1;
        int day = 1;
        return new Date(year, month, day);
    }

    /**
     * @return 上个月月底日
     */
    @Override
    public Date endDate(){
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        int year = date.getYear();
        int month = date.getMonth()== Calendar.JANUARY ? Calendar.DECEMBER : date.getMonth()-1;
        int day = new Date(year,month,0).getDate();
        return new Date(year, month, day);
    }
}