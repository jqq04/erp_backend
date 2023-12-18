package com.nju.edu.erp.model.po.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSheetContentPO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 关联的收款单id
     */
    private String paymentSheetId;
    /**
     * 转账账户
     */
    private String account;
    /**
     * 转账金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
}
