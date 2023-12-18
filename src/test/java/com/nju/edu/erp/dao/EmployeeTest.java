package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.Gender;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.EmployeePO;
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
    private EmployeeDao employeeDao;

    @Test
    @Transactional
    @Rollback(value = true)
    public void saveTest(){
        EmployeePO employeePO = EmployeePO.builder()
                .id("A0000")
                .name("TEST")
                .gender(Gender.MALE)
                .birthday(new Date())
                .role(Role.GM)
                .basicSalary(new BigDecimal("100500"))
                .postSalary(new BigDecimal("80000"))
                .salaryComputeStrategy("年薪计算")
                .salaryPaymentStrategy("年薪发放")
                .phone("123456")
                .account("TEST的工资卡账户")
                .build();
        employeeDao.saveEmployee(employeePO);
        employeeDao.saveEmployeeSalaryInfo(employeePO);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findTest(){
        EmployeePO employeePO = employeeDao.getEmployeeByName("刘钦");
        Assertions.assertEquals(employeePO.getId(),"A0006");
        Assertions.assertNotNull(employeePO.getBirthday());
        Assertions.assertEquals(employeePO.getSalaryComputeStrategy(),"按年薪计算");
    }
}
