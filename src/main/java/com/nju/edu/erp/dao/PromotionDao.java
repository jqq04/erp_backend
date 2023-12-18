package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.promotion.PromotionPO;
import com.nju.edu.erp.model.po.promotion.PromotionPackagePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PromotionDao {

    /**
     * 存入一个策略
     * @param promotionPO 策略信息
     * @return 影响的行数
     */
    int savePromotion(PromotionPO promotionPO);

    /**
     * 将促销策略中的商品要求包存入
     * @param promotionPackagePOList 商品要求包列表
     * @return 影响的行数
     */
    int savePackages(List<PromotionPackagePO> promotionPackagePOList);

    /**
     * 将促销策略中的赠品包存入
     * @param promotionGiftPOList 赠品列表
     * @return 影响的行数
     */
    int saveGifts(List<PromotionPackagePO> promotionGiftPOList);


    /**
     * 获取最新策略
     * @return 最新策略PO
     */
    PromotionPO getLatest();

    /**
     * 获取全部策略
     */
    List<PromotionPO> getAllPromotion();
    /**
     * 根据策略编号获取策略
     * @param promotionId 策略编号
     * @return 策略PO
     */
    PromotionPO getPromotionById(String promotionId);

    /**
     * 根据策略编号获取对应的商品要求包
     */
    List<PromotionPackagePO> getPackagesByPromotionId(String promotionId);

    /**
     * 根据策略编号获取对应的赠品包
     */
    List<PromotionPackagePO> getGiftsByPromotionId(String promotionId);

    /**
     * 2023.12
     * 根据促销策略编号删除对应策略 (还会删除附带的要求包和赠品包）
     * @param promotionId 促销策略编号
     * @return 影响的行数
     */
    int deletePromotion(String promotionId);

}
