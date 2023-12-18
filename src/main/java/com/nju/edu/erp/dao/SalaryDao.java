package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.salary.SalarySheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface    SalaryDao {
    /**
     * 保存工资单
     * @param salarySheetPO 工资单信息
     * @return 影响的行数
     */
    int saveSheet(SalarySheetPO salarySheetPO);

    /**
     * 更新工资单单据状态
     * @param salarySheetId 工资单据id
     * @param state 目的状态
     * @return 影响的行数
     */
    int updateState(String salarySheetId, SalarySheetState state);

    /**
     * 获取最新单据
     */
    SalarySheetPO getLatest();

    /**
     * 获取所有工资单
     * @return 工资单
     */
    List<SalarySheetPO> findAll();

    /**
     * 根据单据状态，返回工资单
     * @param state 单据状态
     * @return 单据列表
     */
    List<SalarySheetPO> findAllByState(SalarySheetState state);

    /**
     * 根据单据编号获取工资单
     * @param salarySheetId 单据编号
     * @return 工资单
     */
    SalarySheetPO findOneById(String salarySheetId);

    /**
     * 根据岗位获取岗位工资
     * @param role 岗位
     * @return 岗位工资
     */
    BigDecimal getPostSalary(Role role);

    /**
     * 获取提成比例
     * @return 提成比例
     */
    BigDecimal getCommissionRate();

    /**
     * 根据员工编号，获取该员工的年终奖金额
     */
    BigDecimal getFinalBonus(String employeeId);

    /**
     * 给员工设置年终奖信息
     * @param employeeId 员工编号
     * @param finalBonus 年终奖金额
     * @return 影响的行数
     */
    int setFinalBonus(String employeeId, BigDecimal finalBonus);

    /**
     * 计算给定员工在指定日期范围内的（应发）薪资总和
     */
    BigDecimal computeSalarySum(String employeeId, Date beginDate, Date endDate);
}
