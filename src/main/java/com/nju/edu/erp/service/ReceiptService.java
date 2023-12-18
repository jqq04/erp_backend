package com.nju.edu.erp.service;


import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptSheetVO;

import java.util.List;

public interface ReceiptService {
    /**
     * 制作收款单
     * @param userVO 用户信息
     * @param receiptSheetVO 收款单编号
     */
    void makeSheet(UserVO userVO, ReceiptSheetVO receiptSheetVO);

    /**
     * 根据id审批单据
     * @param receiptSheetId 收款单编号
     * @param state
     *  if code = 'F0001' then msg = '更改状态失败!不能更改状态为PENDING'
     *     code = 'F0002'      msg = '更新状态失败!单据已经被审批！'
     */
    void approval(String receiptSheetId, ReceiptSheetState state);

    /**
     * 根据状态获取单据
     * @param state 单据状态
     * @return 所有单据列表
     */
    List<ReceiptSheetVO> getSheetByState(ReceiptSheetState state);

    /**
     * 根据id获取收款单
     * @param receiptSheetId 收款单编号
     * @return 对于收款单
     */
    ReceiptSheetVO getSheetById(String receiptSheetId);
}
