package com.nju.edu.erp.model.vo.saleReturns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleReturnsSheetContentVO {
    /**
     * 商品id
     */
    private String pid;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 商品原价，对应与销售原价，前端传null
     */
    private BigDecimal unitPrice;
    /**
     * 算到单件商品上退回的单价，前端传null
     */
    private BigDecimal returnsPrice;
    /**
     * 退回总价，与销售对应，前端传null
     */
    private BigDecimal returnsAmount;
    /**
     * 备注
     */
    private String remark;
}
