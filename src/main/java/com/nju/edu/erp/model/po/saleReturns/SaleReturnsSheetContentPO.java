package com.nju.edu.erp.model.po.saleReturns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleReturnsSheetContentPO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 销售退货单id
     */
    private String saleReturnsSheetId;
    /**
     * 商品id
     */
    private String pid;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 商品原价，对应于销售原价
     */
    private BigDecimal unitPrice;
    /**
     * 算到单件商品上退回的单价
     */
    private BigDecimal returnsPrice;
    /**
     * 退回总价，与销售对应，=单价 * 数量 * 折扣 - 消费券作用在该商品上的优惠价格
     */
    private BigDecimal returnsAmount;
    /**
     * 对应商品在销售前在库存中的批次
     */
    private Integer batchId;
    /**
     * 备注
     */
    private String remark;
}
