package com.nju.edu.erp.model.vo;

import com.nju.edu.erp.enums.Gender;
import com.nju.edu.erp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeVO {
    /**
     * 员工编号,格式：A1234, 新建时前端传null
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 工作岗位
     */
    private Role role;
    /**
     * 基本工资
     */
    private BigDecimal basicSalary;
    /**
     * 岗位工资
     */
    private BigDecimal postSalary;
    /**
     * 薪资计算方式
     */
    private String salaryComputeStrategy;
    /**
     * 薪资发放方式
     */
    private String salaryPaymentStrategy;
    /**
     * 工资卡账户
     */
    private String account;
}
