package com.nju.edu.erp.strategy.promotion.offer;

import com.nju.edu.erp.model.vo.promotion.PromotionOfferInfo;
import com.nju.edu.erp.model.vo.promotion.PromotionPackageVO;

import java.util.List;

/**
 * 用以整合两个优惠信息为一个
 */
public class PromotionOfferInfoGenerator {

    public static PromotionOfferInfo generate(PromotionOfferInfo info1, PromotionOfferInfo info2){
        PromotionOfferInfo res = new PromotionOfferInfo();

        if (info1.getDiscount() == null && info2.getDiscount()==null)
            res.setDiscount(null);
        else if (info1.getDiscount() == null)
            res.setDiscount(info2.getDiscount());
        else if (info2.getDiscount() == null)
            res.setDiscount(info1.getDiscount());
        else
            res.setDiscount(info1.getDiscount().multiply(info2.getDiscount())); //新折扣 = 折扣1 * 折扣2

        if (info1.getVoucher() == null && info2.getVoucher()==null)
            res.setVoucher(null);
        else if (info1.getVoucher() == null)
            res.setVoucher(info2.getVoucher());
        else if (info2.getVoucher() == null)
            res.setVoucher(info1.getVoucher());
        else
            res.setVoucher(info1.getVoucher().add(info2.getVoucher())); //代金券叠加

        if (info1.getGifts() == null && info2.getGifts()==null)
            res.setGifts(null);
        else if (info1.getDiscount() == null)
            res.setGifts(info2.getGifts());
        else if (info2.getGifts() == null)
            res.setGifts(info1.getGifts());
        else{
            List<PromotionPackageVO> promotionPackageVOList = info1.getGifts();
            promotionPackageVOList.addAll(info2.getGifts());
            res.setGifts(promotionPackageVOList);
        }

        return res;
    }
}
