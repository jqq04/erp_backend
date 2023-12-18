package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.service.PromotionService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/promotion")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    /**
     * 创建一个促销策略
     * @param promotionVO 促销策略
     */
    @PostMapping("/create")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response createPromotion(@RequestBody PromotionVO promotionVO){
        promotionService.createPromotion(promotionVO);
        return Response.buildSuccess();
    }

    /**
     * 获取所有促销策略
     * @return List<PromotionVO>
     */
    @GetMapping("/show/all")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.SALE_MANAGER,Role.SALE_STAFF})
    public Response getAllPromotion(){
        return Response.buildSuccess(promotionService.getAllPromotion());
    }

    /**
     * 获取所有的有效的促销策略
     * @return List<PromotionVO>
     */
    @GetMapping("/show/valid")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.SALE_MANAGER,Role.SALE_STAFF})
    public Response getValidPromotion(){
        return Response.buildSuccess(promotionService.getValidPromotion());
    }

    /**
     * 2023.12
     * 删除一个促销策略
     * @param id 促销策略编号
     */
    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response deletePromotion(@RequestParam String id){
        promotionService.deletePromotion(id);
        return  Response.buildSuccess();
    }
}
