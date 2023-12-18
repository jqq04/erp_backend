package com.nju.edu.erp.model.vo.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDetailVO {
    /**
     * 类型 "销售" / "销售退货"
     */
    private String type;
    /**
     * 销售（退货）日期 yyyy-MM-dd HH:mm:ss
     */
    private Date date;
    /**
     * 客户id
     */
    private Integer supplier;
    /**
     * 销售员
     */
    private String salesman;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品型号
     */
    private String productType;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
}
