package com.nju.edu.erp.strategy.promotion.offer.Impl;

import com.nju.edu.erp.model.vo.promotion.PromotionOfferInfo;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.strategy.promotion.offer.PromotionOfferStrategy;
import org.springframework.stereotype.Component;

@Component("代金券促销")
public class VoucherPromotionOfferStrategy implements PromotionOfferStrategy {

    @Override
    public PromotionOfferInfo offer(PromotionVO promotionVO) {
        PromotionOfferInfo promotionOfferInfo = new PromotionOfferInfo();
        promotionOfferInfo.setVoucher(promotionVO.getVoucher());
        return promotionOfferInfo;
    }
}
