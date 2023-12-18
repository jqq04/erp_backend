package com.nju.edu.erp.strategy.promotion.offer.Impl;

import com.nju.edu.erp.model.vo.promotion.PromotionOfferInfo;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.strategy.promotion.offer.PromotionOfferStrategy;
import org.springframework.stereotype.Component;

@Component("折扣促销")
public class DiscountPromotionOfferStrategy implements PromotionOfferStrategy {

    @Override
    public PromotionOfferInfo offer(PromotionVO promotionVO) {
        PromotionOfferInfo promotionOfferInfo = new PromotionOfferInfo();
        promotionOfferInfo.setDiscount(promotionVO.getDiscount());
        return promotionOfferInfo;
    }
}
