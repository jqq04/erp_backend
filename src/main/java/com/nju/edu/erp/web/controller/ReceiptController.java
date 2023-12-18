package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptSheetVO;
import com.nju.edu.erp.service.ReceiptService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * 财务人员制作收款单
     */
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    @PostMapping(value = "/sheet-make")
    public Response createReceiptSheet(UserVO userVO, @RequestBody ReceiptSheetVO receiptSheetVO){
        receiptService.makeSheet(userVO, receiptSheetVO);
        return Response.buildSuccess();
    }
    /**
     *总经理审批收款单
     */
    @Authorized(roles = {Role.ADMIN, Role.GM})
    @GetMapping(value = "/approval")
    public Response approval(@RequestParam("receiptSheetId") String receiptSheetId, @RequestParam("state") ReceiptSheetState state){
        receiptService.approval(receiptSheetId, state);
        return Response.buildSuccess();
    }
    /**
     * 根据状态获取收款单列表
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) ReceiptSheetState state)  {
        return Response.buildSuccess(receiptService.getSheetByState(state));
    }
    /**
     * 根据收款单Id搜索收款单
     * @param id 收款单Id
     * @return 收款单信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(receiptService.getSheetById(id));
    }
}
