package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.enums.Gender;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.EmployeeVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class EmployeeTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    @Transactional
    @Rollback(value = true)
    public void inductionTest(){
        EmployeeVO employeeVO = EmployeeVO.builder()
                .name("闻博")
                .gender(Gender.MALE)
                .birthday(new Date())
                .role(Role.GM)
                .basicSalary(new BigDecimal("100500"))
                .salaryComputeStrategy("按年薪计算")
                .salaryPaymentStrategy("按年薪发放")
                .phone("123")
                .account("wb的工资卡账户")
                .build();
        employeeService.induction(employeeVO);

        EmployeeVO employeeVO1 = employeeService.getEmployeeByName("闻博");
        Assertions.assertNotNull(employeeVO1);

    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void updateTest(){
        EmployeeVO employeeVO = EmployeeVO.builder()
                .id("A0006")
                .name("刘钦")
                .gender(Gender.FEMALE)
                .birthday(new Date())
                .role(Role.INVENTORY_MANAGER)
                .basicSalary(new BigDecimal("40500"))
                .salaryComputeStrategy("按月薪计算")
                .salaryPaymentStrategy("按月薪发放")
                .phone("123")
                .account("67换了张卡")
                .build();
        employeeService.updateEmployee(employeeVO);
        EmployeeVO employeeVO1 = employeeService.getEmployeeByName("刘钦");
        Assertions.assertNotNull(employeeVO1);
        Assertions.assertEquals("123",employeeVO1.getPhone());
    }

}
