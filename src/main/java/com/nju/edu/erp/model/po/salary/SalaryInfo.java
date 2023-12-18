package com.nju.edu.erp.model.po.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryInfo {

    /**
     * 基本工资
     */
    private BigDecimal basicSalary;
    /**
     * 岗位工资
     */
    private BigDecimal postSalary;
    /**
     * 提成工资，非提成制工资计算策略的这一栏为0
     */
    private BigDecimal commission;
    /**
     * 应扣工资, 与缺勤天数有关
     */
    private BigDecimal deductSalary;

    /**
     * 应发工资 = 基本工资 + 岗位工资 + 提成 - 应扣工资
     */
    private BigDecimal expectedSalary;


    /**
     * 扣除个人所得税
     */
    private BigDecimal personalIncomeTax;
    /**
     * 扣除失业保险
     */
    private BigDecimal unemploymentInsurance;
    /**
     * 扣除住房公积金
     */
    private BigDecimal housingProvidentFund;
    /**
     * 应扣税款总计 = 个人所得税 + 失业保险 + 住房公积金
     */
    private BigDecimal totalTax;


    /**
     * 实发工资 = 应发工资 - 应扣税款
     */
    private BigDecimal finalSalary;
}
