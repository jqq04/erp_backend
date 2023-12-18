package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleReturnsService {
    /**
     * 指定销售退货单
     * @param userVO
     * @param saleReturnsSheetVO
     */
    void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO);

    /**
     * 根据单据状态获取销售退货单
     * @param state
     * @return
     */
    List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state);

    /**
     * 审批单据
     * @param saleReturnsSheetId
     * @param state
     */
    void approval(String saleReturnsSheetId, SaleReturnsSheetState state);


    /**
     * 根据销售单Id搜索销售退货单信息
     * @param saleReturnsSheetId 销售退货单Id
     * @return 销售退货单
     */
    SaleReturnsSheetVO getSaleReturnsSheetById(String saleReturnsSheetId);
}
