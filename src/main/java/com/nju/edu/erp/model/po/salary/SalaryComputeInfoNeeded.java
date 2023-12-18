package com.nju.edu.erp.model.po.salary;

import com.nju.edu.erp.model.po.EmployeePO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * 用来封装传输计算薪资时需要的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryComputeInfoNeeded {
    /**
     * 员工信息
     */
    private EmployeePO employee;
    /**
     * 缺勤天数
     */
    private Integer absencesDays;
    /**
     * 销售额总计，非提成制为0
     */
    private BigDecimal totalSaleAmount;
    /**
     * 提成比例
     */
    private BigDecimal commissionRate;
    /**
     * 年终奖金额，只有当
     */
    private BigDecimal yearEndBonus;
}
