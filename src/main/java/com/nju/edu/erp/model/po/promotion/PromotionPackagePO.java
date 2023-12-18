package com.nju.edu.erp.model.po.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionPackagePO {
    /**
     * 促销包 id
     */
    private Integer id;
    /**
     * 关联的促销的编号
     */
    private String promotionId;

    /**
     * 商品编号
     */
    private String productId;
    /**
     * 数量
     */
    private Integer quantity;
}
