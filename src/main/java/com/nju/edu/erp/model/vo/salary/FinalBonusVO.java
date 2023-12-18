package com.nju.edu.erp.model.vo.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinalBonusVO {
    /**
     * 员工编号
     */
    private String employeeId;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 从1月到11月的工资总额
     */
    private BigDecimal yearSalarySum;
    /**
     * 年终奖金额
     */
    private BigDecimal finalBonus;
}
