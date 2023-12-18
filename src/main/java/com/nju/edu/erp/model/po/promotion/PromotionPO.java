package com.nju.edu.erp.model.po.promotion;

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
public class PromotionPO {
    /**
     * 促销策略编号，格式 PRO-yyyyMMdd-xxxxx 其中年月日为促销策略的创建日期
     */
    private String id;
    /**
     * 活动开始时间 yyyy-MM-dd HH:mm:ss
     */
    private Date beginTime;
    /**
     * 活动结束时间 yyyy-MM-dd HH:mm:ss
     */
    private Date endTime;


    /**
     * 表示促销针对的客户的最低级别，null表示针对所有客户
     */
    private Integer customerLevel;
    /**
     * 表示促销需求的最低总价，null表示对总价不做要求
     */
    private BigDecimal totalAmount;


    /**
     * 表示促销提供的折扣，null表示没有折扣
     */
    private BigDecimal discount;
    /**
     * 表示促销提供的代金券，null表示没有代金券
     */
    private BigDecimal voucher;
}
