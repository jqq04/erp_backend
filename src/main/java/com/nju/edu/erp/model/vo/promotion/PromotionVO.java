package com.nju.edu.erp.model.vo.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionVO {
    /**
     * 促销策略编号，格式 PRO-yyyyMMdd-xxxxx 其中年月日为促销策略的创建日期
     */
    private String id;
    /**
     * 活动开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String beginTime;
    /**
     * 活动结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime;


    /**
     * 表示促销针对的客户的最低级别，null表示针对所有客户
     */
    private Integer customerLevel;
    /**
     * 表示促销需求的最低总价，null表示对总价不做要求
     */
    private BigDecimal totalAmount;
    /**
     * 表示促销针对的商品包（要每一个都全部满足）, null表示对此无要求
     */
    private List<PromotionPackageVO> packages;



    /**
     * 表示促销提供的折扣，null表示没有折扣
     */
    private BigDecimal discount;
    /**
     * 表示促销提供的代金券，null表示没有代金券
     */
    private BigDecimal voucher;
    /**
     * 表示促销赠送的赠品，null表示没有赠品
     */
    private List<PromotionPackageVO> gifts;
}
