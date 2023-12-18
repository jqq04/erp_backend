package com.nju.edu.erp.strategy.salary.payment;

import java.util.Date;

public interface SalaryPaymentStrategy {

    /**
     * 判断是否到发薪日
     */
    boolean isPayDay( );

    /**
     * @return 薪资计算的开始日
     */
    Date beginDate();

    /**
     * @return 薪资计算的结束日
     */
    Date endDate();
}
