package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.payment.PaymentSheetVO;

import java.util.List;

public interface PaymentService {
    /**
     * 制作付款单
     * @param userVO 用户信息
     * @param paymentSheetVO 付款单编号
     */
    void makeSheet(UserVO userVO, PaymentSheetVO paymentSheetVO);

    /**
     * 根据id审批单据
     * @param paymentSheetId 付款单编号
     * @param state
     * if code = 'F0001' then msg = '更改状态失败!不能更改状态为PENDING'
     *    code = 'F0002'      msg = '更新状态失败!单据已经被审批！'
     */
    void approval(String paymentSheetId, PaymentSheetState state);

    /**
     * 根据状态获取单据
     * @param state 单据状态
     * @return 所有单据列表
     */
    List<PaymentSheetVO> getSheetByState(PaymentSheetState state);

    /**
     * 根据id获取付款单
     * @param paymentSheetId 付款单编号
     * @return 对于付款单
     */
    PaymentSheetVO getSheetById(String paymentSheetId);
}
