package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/findByType")
    public Response findByType(@RequestParam CustomerType type) {
        return Response.buildSuccess(customerService.getCustomersByType(type));
    }

    @GetMapping("/findAll")
    public Response findAll() {
        return Response.buildSuccess(customerService.getAllCustomers());
    }


    @PostMapping("/add")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.SALE_MANAGER, Role.SALE_STAFF})
    public Response addCustomer(@RequestBody CustomerVO customerVO){
        return Response.buildSuccess(customerService.addCustomer(customerVO));
    }


    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.SALE_MANAGER, Role.SALE_STAFF})
    public Response deleteCustomer(@RequestParam Integer id) {
        customerService.deleteById(id);
        return Response.buildSuccess();
    }
}
