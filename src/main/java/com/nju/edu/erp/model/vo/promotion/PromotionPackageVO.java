package com.nju.edu.erp.model.vo.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionPackageVO {
    /**
     * 商品id
     */
    private String productId;
    /**
     * 数量
     */
    private Integer quantity;
}
