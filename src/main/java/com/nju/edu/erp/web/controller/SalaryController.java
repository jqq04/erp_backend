package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/salary-sheet")
public class SalaryController {

    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    /**
     * 审批工资单
     */
    @GetMapping("/approval")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response approval(@RequestParam("salarySheetId") String salarySheetId, @RequestParam("state") SalarySheetState state){
       salaryService.approval(salarySheetId,state);
       return Response.buildSuccess();
    }

    /**
     * 根据状态展示工资单
     * 如果state = null，则展示所有工资单
     */
    @GetMapping("/sheet-show")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response showSheetByState(@RequestParam(value = "state", required = false) SalarySheetState state){
        return Response.buildSuccess(salaryService.getSheetByState(state));
    }

    /**
     * 获取所有非GM和admin员工的有关年终奖的信息
     * @return List<FinalBonusVO>
     */
    @GetMapping("/bonus/queryAll")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response getFinalBonusList(){
        return Response.buildSuccess(salaryService.getFinalBonusList());
    }

    /**
     * 给员工设置年终奖
     * @param id 员工编号
     * @param bonus 年终奖金额
     */
    @GetMapping("/bonus/set")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response setYearEndBonus(@RequestParam String id, @RequestParam BigDecimal bonus){
        salaryService.setFinalBonus(id,bonus);
        return Response.buildSuccess();
    }
}
