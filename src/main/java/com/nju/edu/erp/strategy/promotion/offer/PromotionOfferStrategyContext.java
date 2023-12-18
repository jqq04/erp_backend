package com.nju.edu.erp.strategy.promotion.offer;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PromotionOfferStrategyContext {

    private final Map<String, PromotionOfferStrategy> promotionOfferStrategyMap = new ConcurrentHashMap<>();

    public PromotionOfferStrategyContext(Map<String, PromotionOfferStrategy> strategyMap){
        this.promotionOfferStrategyMap.clear();
        strategyMap.forEach((k,v)->this.promotionOfferStrategyMap.put(k,v));
    }

    public PromotionOfferStrategy getResource(String strategyName){return this.promotionOfferStrategyMap.get(strategyName);}
}
