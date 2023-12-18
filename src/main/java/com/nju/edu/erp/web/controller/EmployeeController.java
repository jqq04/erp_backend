package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.EmployeeVO;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 员工入职
     * @param employeeVO 员工信息
     * 会为员工创建一个默认账户，其中账户名为员工编号，密码为123456
     */
    @PostMapping("/induction")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response induction(@RequestBody EmployeeVO employeeVO){
        employeeService.induction(employeeVO);
        return Response.buildSuccess();
    }

    /**
     * 员工离职
     * @param id 员工编号
     */
    @PostMapping("/quit")
    @Authorized(roles = {Role.HR,Role.ADMIN})
    public Response employeeQuit(String id){
        employeeService.employeeQuit(id);
        return Response.buildSuccess();
    }

    /**
     * 更新员工信息
     * @param employeeVO 员工信息
     */
    @PostMapping("/update")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response updateEmployee(@RequestBody EmployeeVO employeeVO){
        employeeService.updateEmployee(employeeVO);
        return Response.buildSuccess();
    }

    /**
     * 获取全部(在职)员工信息
     * @return List<EmployeeVO>
     */
    @GetMapping("/queryAll")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response getAllEmployee(){
        return Response.buildSuccess(employeeService.getAllEmployee());
    }

    /**
     * 删除员工信息
     * 前端暂时不用设计，因为即使员工离职也要保留员工信息，所以没有删除的必要
     * @param id 员工编号
     * if code = 'G0001' then msg = '员工还在职，无法删除信息'
     */
    @GetMapping("/delete")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response deleteEmployee(@RequestParam String id){
        employeeService.deleteEmployeeById(id);
        return Response.buildSuccess();
    }
}
