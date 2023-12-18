package com.nju.edu.erp.strategy.promotion.offer;

import com.nju.edu.erp.model.vo.promotion.PromotionOfferInfo;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;

public interface PromotionOfferStrategy {
    /**
     * @param promotionVO 促销信息
     * @return 促销给到的优惠
     */
    PromotionOfferInfo offer(PromotionVO promotionVO);
}
