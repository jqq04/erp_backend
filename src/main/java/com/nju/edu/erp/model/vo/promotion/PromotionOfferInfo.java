package com.nju.edu.erp.model.vo.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 促销策略中，所给予的优惠信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionOfferInfo {
    /**
     * 表示促销提供的折扣，null表示没有折扣
     */
    private BigDecimal discount;
    /**
     * 表示促销提供的代金券，null表示没有代金券
     */
    private BigDecimal voucher;
    /**
     * 表示促销赠送的赠品，null表示没有赠品
     */
    private List<PromotionPackageVO> gifts;

}
