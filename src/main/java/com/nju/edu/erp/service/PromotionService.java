package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.promotion.PromotionVO;

import java.util.List;

public interface PromotionService {
    /**
     * 创建促销策略
     * @param promotionVO 促销策略信息
     */
    void createPromotion(PromotionVO promotionVO);

    /**
     * 根据促销策略编号获取促销策略
     * @param promotionId 促销策略编号
     * @return 促销策略
     */
    PromotionVO getPromotionById(String promotionId);

    /**
     * 获取全部销售策略
     */
    List<PromotionVO> getAllPromotion();

    /**
     * 获取全部有效的销售策略（在活动时间内的）
     */
    List<PromotionVO> getValidPromotion();
}
