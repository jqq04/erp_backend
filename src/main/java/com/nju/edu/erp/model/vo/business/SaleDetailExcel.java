package com.nju.edu.erp.model.vo.business;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SaleDetailExcel {
    /**
     * 类型 "销售" / "销售退货"
     */
    @ExcelProperty(value = {"类型"}, index = 0)
    private String type;

    /**
     * 销售（退货）日期 yyyy-MM-dd HH:mm:ss
     */
    @ExcelProperty(value = {"日期"}, index = 1)
    private Date date;
    /**
     * 客户id
     */
    @ExcelProperty(value = {"客户id"}, index = 2)
    private Integer supplier;

    /**
     * 销售员
     */
    @ExcelProperty(value = {"销售员"}, index = 3)
    private String salesman;

    /**
     * 商品id
     */
    @ExcelProperty(value = {"商品id"}, index = 4)
    private String productId;

    /**
     * 商品名称
     */
    @ExcelProperty(value = {"商品名"}, index = 5)
    private String productName;

    /**
     * 商品型号
     */
    @ExcelProperty(value = {"型号"}, index = 6)
    private String productType;

    /**
     * 数量
     */
    @ExcelProperty(value = {"数量"}, index = 7)
    private Integer quantity;

    /**
     * 单价
     */
    @ExcelProperty(value = {"单价"}, index = 8)
    private BigDecimal unitPrice;

    /**
     * 总价
     */
    @ExcelProperty(value = {"总价"}, index = 9)
    private BigDecimal totalPrice;
}
