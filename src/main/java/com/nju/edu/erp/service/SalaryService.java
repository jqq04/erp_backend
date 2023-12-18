package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.salary.SalarySheetPO;
import com.nju.edu.erp.model.vo.salary.FinalBonusVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface SalaryService {
    /**
     * 系统生成员工工资单
     * @param employeeId 员工编号
     * @param beginDate 工资单计算的起始日期
     * @param endDate 工资单计算的终止日期
     */
    void makeSheet(String employeeId, Date beginDate, Date endDate);

    /**
     * 审批工资单
     * @param salarySheetId 单据编号
     * @param state 单据目的状态
     * if code = 'F0001' then msg = '更改状态失败!不能更改状态为PENDING'
     *    code = 'F0002'      msg = '更新状态失败!单据已经被审批！'
     */
    void approval(String salarySheetId, SalarySheetState state);

    /**
     * 根据状态获取单据列表
     * @param state 单据状态
     * @return 符合单据状态的单据列表
     * 如果state为null，则返回所有单据
     */
    List<SalarySheetPO> getSheetByState(SalarySheetState state);

    /**
     * 为员工设置年终奖
     * @param employeeId 员工id
     * @param finalBonus 金额
     */
    void setFinalBonus(String employeeId, BigDecimal finalBonus);

    /**
     * 获取每个（不是GM的）员工的1月到11月的（应发）薪水总和
     * @return 薪水总和信息列表
     */
    List<FinalBonusVO> getFinalBonusList();

    /**
     * 每日零点自动触发，检查是否需要给员工发薪
     */
    void paySalary();
}
