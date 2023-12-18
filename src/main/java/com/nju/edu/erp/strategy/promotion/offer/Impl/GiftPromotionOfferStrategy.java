package com.nju.edu.erp.strategy.promotion.offer.Impl;

import com.nju.edu.erp.model.vo.promotion.PromotionOfferInfo;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.strategy.promotion.offer.PromotionOfferStrategy;
import org.springframework.stereotype.Component;

@Component("赠品促销")
public class GiftPromotionOfferStrategy implements PromotionOfferStrategy {

    @Override
    public PromotionOfferInfo offer(PromotionVO promotionVO) {
        PromotionOfferInfo promotionOfferInfo = new PromotionOfferInfo();
        promotionOfferInfo.setGifts(promotionOfferInfo.getGifts());
        return promotionOfferInfo;
    }
}
