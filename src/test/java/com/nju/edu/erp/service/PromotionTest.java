package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.promotion.PromotionPackageVO;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PromotionTest {
    @Autowired
    private PromotionService promotionService;

    @Test
    @Transactional
    @Rollback(value = true)
    public void createPromotionTest(){
        List<PromotionPackageVO> promotionPackageVOList = new ArrayList<>();
        promotionPackageVOList.add(PromotionPackageVO.builder()
                        .productId("0000000000400000")
                        .quantity(10)
                        .build());
        promotionPackageVOList.add(PromotionPackageVO.builder()
                        .productId("0000000000500001")
                        .quantity(5)
                        .build());
        PromotionVO promotionVO = PromotionVO.builder()
                .beginTime("2022-5-1 00:00:00")
                .endTime("2022-9-1 00:00:00")
                .customerLevel(3)
                .totalAmount(new BigDecimal("10000.00"))
                .packages(null)
                .discount(new BigDecimal("0.7"))
                .voucher(null)
                .gifts(promotionPackageVOList)
                .build();

        promotionService.createPromotion(promotionVO);

        List<PromotionVO> promotionVOList = promotionService.getAllPromotion();
        Assertions.assertNotNull(promotionVOList);
        Assertions.assertFalse(promotionVOList.isEmpty());
    }
}
