package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.payment.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PaymentDao {
    /**
     * 保存付款单
     */
    int saveSheet(PaymentSheetPO paymentSheetPO);
    /**
     * 保存付款单上的具体条目列表
     */
    int saveList(List<PaymentSheetContentPO> paymentSheetContent);
    /**
     * 更新单据（的状态）
     */
    int updateState(String paymentSheetId, PaymentSheetState state);
    /**
     * 获取最新单据
     */
    PaymentSheetPO getLatest();
    /**
     * 返回所有单据
     */
    List<PaymentSheetPO> findAll();
    /**
     * 根据状态返回单据
     */
    List<PaymentSheetPO> findAllByState(PaymentSheetState paymentSheetState);
    /**
     * 根据单据编号获取单据
     */
    PaymentSheetPO findOneById(String paymentSheetId);
    /**
     * 根据付款单编号获取具体条目列表
     */
    List<PaymentSheetContentPO> findContentBySheetId(String paymentSheetId);

}
