package com.nju.edu.erp.dao;


import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.sale.*;
import com.nju.edu.erp.model.po.CustomerPurchaseAmountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SaleSheetDao {

    /**
     * 获取最近一条销售单
     * @return 单据
     */
    SaleSheetPO getLatest();

    /**
     * 存入一条销售单记录
     * @param toSave 一条销售单记录
     * @return 影响的行数
     */
    int saveSheet(SaleSheetPO toSave);

    /**
     * 把销售单上的具体内容存入数据库
     * @param saleSheetContent 入销售单上的具体内容
     * @return 影响行数
     */
    int saveBatchSheetContent(List<SaleSheetContentPO> saleSheetContent);

    /**
     * 查找所有销售单
     */
    List<SaleSheetPO> findAllSheet();

    /**
     * 此方法为新增
     * 根据state返回销售单
     * @param state 销售单状态
     * @return 销售单列表
     */
    List<SaleSheetPO> findAllByState(SaleSheetState state);

    /**
     * 查找指定id的销售单
     * @param id 单据id
     */
    SaleSheetPO findSheetById(String id);

    /**
     * 查找指定销售单下具体的商品内容
     * @param sheetId 单据id
     */
    List<SaleSheetContentPO> findContentBySheetId(String sheetId);

    /**
     * 更新指定销售单的状态
     * @param sheetId 单据id
     * @param state 目的状态
     * @return 影响行数
     */
    int updateSheetState(String sheetId, SaleSheetState state);

    /**
     * 根据当前状态更新销售单状态
     * @param sheetId 单据id
     * @param prev 当前状态
     * @param state 目的状态
     * @return 影响行数
     */
    int updateSheetStateOnPrev(String sheetId, SaleSheetState prev, SaleSheetState state);

    /**
     * 通过saleSheetId和 saleSheetContentId找到商品的批次
     * @param saleSheetId 销售单id
     * @param saleSheetContentId 销售单内容id
     * @return 批次号
     */
    Integer findBatchId(String saleSheetId, Integer saleSheetContentId);

    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     * @param salesman 销售人员的账户名
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 客户消费总额信息
     */
    CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman, Date beginTime,Date endTime);

    /**
     * 查询某个销售人员某段时间内的销售总额
     * @param salesman 销售人员的账户名
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 销售总额
     */
    BigDecimal getTotalSaleAmount(String salesman, Date beginTime, Date endTime);
}
