package com.nju.edu.erp.model.vo.receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptSheetContentVO {
    /**
     * 自增id，前端传null
     */
    private Integer id;
    /**
     * 关联的收款单id,前端传null
     */
    private String receiptSheetId;
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
