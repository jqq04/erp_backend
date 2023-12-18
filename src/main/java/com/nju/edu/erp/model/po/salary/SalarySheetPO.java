package com.nju.edu.erp.model.po.salary;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.SheetVO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalarySheetPO extends SheetVO {
    /**
     * 工资单单据编号（格式为：GZD-yyyyMMdd-xxxxx）
     * 在父类SheetVO中
     */
    //private String id;
    /**
     * 工资计算的起始日期 yyyy-MM-dd
     */
    private Date beginDate;
    /**
     * 工资计算的截止日期 yyyy-MM-dd
     */
    private Date endDate;
    /**
     * 单据创建时间 yyyy-MM-dd HH:mm:ss
     * 在父类SheetVO中
     */
    //private Date createTime;
    /**
     * 员工编号 A0001
     */
    private String employeeId;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 员工银行账户
     */
    private String account;

    /**
     * 出勤天数
     */
    private Integer attendanceDays;
    /**
     * 缺勤天数
     */
    private Integer absenceDays;

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
     * 应发工资 = 基本工资 + 岗位工资 + 提成工资 - 应扣工资
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

    /**
     * 单据状态
     */
    private SalarySheetState state;
}
