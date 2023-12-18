package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.exception.MyServiceExceptionHandler;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    /**
     * 创建账户
     * @param accountVO
     * if code = 'E0001' then msg = '创建失败! 账户名重复!'
     */
    @PostMapping("/create")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response createAccount(@RequestBody AccountVO accountVO) {
        accountService.createAccount(accountVO);
        return Response.buildSuccess();
    }

    /**
     * 更新账户
     * @param accountVO 更新的账户信息
     * if code = 'E0002' then msg = '更新失败！新账户名与其他账户名冲突‘
     */
    @PostMapping("/update")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response updateAccount(@RequestBody AccountVO accountVO) {
        accountService.updateAccount(accountVO);
        return Response.buildSuccess();
    }

    /**
     * 获取所有账户
     * @return List<AccountVO>
     */
    @GetMapping("/queryAll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response findAllAccount() {
        return Response.buildSuccess(accountService.queryAllAccount());
    }

    /**
     * 通过名称关键词模糊查找账户
     * @param name 名称关键词
     * @return List<AccountVO> / null
     */
    @GetMapping("/findByName")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response findByName(@RequestParam String name) {
        return Response.buildSuccess(accountService.findByName(name));
    }

    /**
     * 通过账户id删除账户
     * @param id 账户id
     * if code = 'E0003' then msg = '账户不存在'
     */
    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response deleteAccount(@RequestParam Integer id) {
        accountService.deleteById(id);
        return Response.buildSuccess();
    }
}
