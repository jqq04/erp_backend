package com.nju.edu.erp.strategy.promotion.require;

import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;

public interface PromotionRequireStrategy {

    /**
     * 根据给定的促销策略和销售单（草稿），判断是否满足促销条件
     * @param promotionVO 促销策略信息
     * @param saleSheetVO 初步填写的销售单（要求填写完毕 客户等级、商品列表、原总价）
     * @return 该次销售是否满足该条促销条件
     */
    boolean satisfied(PromotionVO promotionVO, SaleSheetVO saleSheetVO);
}
