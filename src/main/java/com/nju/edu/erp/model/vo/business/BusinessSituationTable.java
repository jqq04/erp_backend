package com.nju.edu.erp.model.vo.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessSituationTable {
    /**
     * 起始日期 yyyy-MM-dd HH:mm:ss
     */
    private Date beginDate;
    /**
     * 终止日期 yyyy-MM-dd HH:mm:ss
     */
    private Date endDate;
    /**
     * 总收入
     */
    private BigDecimal totalIncome;
    /**
     * 总支出
     */
    private BigDecimal totalPay;
    /**
     * 利润 = 总收入 - 总支出
     */
    private BigDecimal profit;
    /**
     * 具体条目
     */
    private List<BusinessSituationItem> items;
}
