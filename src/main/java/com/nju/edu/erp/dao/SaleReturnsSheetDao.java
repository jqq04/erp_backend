package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.saleReturns.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SaleReturnsSheetDao {
    /**
     * 获取最近一条销售退货单
     * @return 最近一条销售退货单
     */
    SaleReturnsSheetPO getLatest();

    /**
     * 存入一条销售退货单记录
     * @param toSave 一条销售退货单记录
     * @return 影响的行数
     */
    int saveSheet(SaleReturnsSheetPO toSave);

    /**
     * 把销售退货单上的具体内容存入数据库
     * @param saleReturnsSheetContent 销售退货单上的具体内容
     */
    int saveBatchSheetContent(List<SaleReturnsSheetContentPO> saleReturnsSheetContent);

    /**
     * 返回所有销售退货单
     * @return 销售退货单列表
     */
    List<SaleReturnsSheetPO> findAllSheet();

    /**
     * 根据state返回销售退货单
     * @param state 销售退货单状态
     * @return 销售退货单列表
     */
    List<SaleReturnsSheetPO> findAllByState(SaleReturnsSheetState state);

    /**
     * 根据 saleReturnsSheetId 找到条目， 并更新其状态为state
     * @param saleReturnsSheetId 进货退货单id
     * @param state 销售退货单状态
     * @return 影响的条目数
     */
    int updateSheetState(String saleReturnsSheetId, SaleReturnsSheetState state);

    /**
     * 根据当前状态更新销售退货单状态
     * @param sheetId
     * @param prev
     * @param state
     * @return
     */
    int updateSheetStateOnPrev(String sheetId, SaleReturnsSheetState prev, SaleReturnsSheetState state);

    /**
     * 查找指定id的销售退货单
     * @param sheetId
     * @return
     */
    SaleReturnsSheetPO findSheetById(String sheetId);

    /**
     * 查找指定销售退货单下具体的商品内容
     * @param sheetId
     */
    List<SaleReturnsSheetContentPO> findContentBySheetId(String sheetId);


}
