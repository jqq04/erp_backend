package com.nju.edu.erp.model.po.payment;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
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
public class PaymentSheetPO {
    /**
     * 收款单单据编号，格式为：FKD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 客户/销售商/供应商
     */
    private Integer supplier;
    /**
     * 操作员，为当前操作者
     */
    private String operator;
    /**
     * 付款总额
     */
    private BigDecimal totalAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 单据状态
     */
    private PaymentSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
