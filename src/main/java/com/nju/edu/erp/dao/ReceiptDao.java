package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.po.receipt.ReceiptSheetContentPO;
import com.nju.edu.erp.model.po.receipt.ReceiptSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReceiptDao {
    /**
     * 保存收款单
     * @return 影响的行数
     */
    int saveSheet(ReceiptSheetPO receiptSheetPO);
    /**
     * 保存收款单上的具体条目列表
     */
    int saveList(List<ReceiptSheetContentPO> receiptSheetContent);
    /**
     * 更新单据（的状态）
     */
    int updateState(String receiptSheetId, ReceiptSheetState state);
    /**
     * 获取最新单据
     */
    ReceiptSheetPO getLatest();
    /**
     * 返回所有单据
     */
    List<ReceiptSheetPO> findAll();
    /**
     * 根据状态返回单据
     */
    List<ReceiptSheetPO> findAllByState(ReceiptSheetState receiptSheetState);
    /**
     * 根据单据编号获取单据
     */
    ReceiptSheetPO findOneById(String receiptSheetId);
    /**
     * 根据收款单编号获取具体条目列表
     */
    List<ReceiptSheetContentPO> findContentBySheetId(String receiptSheetId);
}
