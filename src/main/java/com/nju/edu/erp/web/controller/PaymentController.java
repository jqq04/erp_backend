package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.payment.PaymentSheetVO;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {
    private final PaymentService paymentService;
    
    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    /**
     * 财务人员制作付款单
     */
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    @PostMapping(value = "/sheet-make")
    public Response createPaymentSheet(UserVO userVO, @RequestBody PaymentSheetVO paymentSheetVO){
        paymentService.makeSheet(userVO, paymentSheetVO);
        return Response.buildSuccess();
    }
    /**
     *总经理审批付款单
     */
    @Authorized(roles = {Role.ADMIN, Role.GM})
    @GetMapping(value = "/approval")
    public Response approval(@RequestParam("paymentSheetId") String paymentSheetId, @RequestParam("state") PaymentSheetState state){
        paymentService.approval(paymentSheetId, state);
        return Response.buildSuccess();
    }
    /**
     * 根据状态获取付款单列表
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) PaymentSheetState state)  {
        return Response.buildSuccess(paymentService.getSheetByState(state));
    }
    /**
     * 根据付款单Id搜索付款单
     * @param id 付款单Id
     * @return 付款单信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(paymentService.getSheetById(id));
    }
}
