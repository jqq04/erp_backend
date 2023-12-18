package com.nju.edu.erp.strategy.salary.compute;

import com.nju.edu.erp.model.po.salary.SalaryComputeInfoNeeded;
import com.nju.edu.erp.model.po.salary.SalaryInfo;

import java.math.BigDecimal;
import java.util.Date;

public interface SalaryComputeStrategy {
    /**
     * 计算出薪资信息（基本工资，岗位工资，应扣工资，扣除个人所得税，扣除失业保险，扣除住房公积金，扣除税款合计，实发工资）
     * @param  info 需要的相关信息
     * @return 薪资信息
     */
    SalaryInfo compute(SalaryComputeInfoNeeded info);


}
