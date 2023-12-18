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
public class BusinessSituationItem {
    /**
     *  "收入"/"支出"
     */
    private String type;
    /**
     * "销售收入"/"进货退货收入"/
     * "进货支出"/"销售退货支出"/"薪资支出"
     */
    private String description;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 折让比例，当且仅当 description = "销售收入"时，有意义不为空
     */
    private BigDecimal discount;
    /**
     * 日期
     */
    private Date date;
}
