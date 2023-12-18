package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class CustomerTest {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDao customerDao;
    @Test
    @Transactional
    @Rollback(value = true)
    public void addCustomerTest(){
        CustomerVO customerVO = CustomerVO.builder()
                .name("测试罢了")
                .type(CustomerType.SELLER.getValue())
                .level(3)
                .address("ks")
                .email("0001@qq.com")
                .zipcode("1234")
                .phone("00000200")
                .payable(BigDecimal.ZERO)
                .lineOfCredit(BigDecimal.ZERO)
                .receivable(BigDecimal.ZERO)
                .operator("xiaoshoujingli")
                .build();
        CustomerPO customerPO1 = customerService.addCustomer(customerVO);
        Integer id = customerPO1.getId();
        Assertions.assertNotNull(customerPO1);
        Assertions.assertNotNull(id);

        CustomerPO customerPO2 = customerService.findCustomerById(id);
        Assertions.assertEquals(customerPO1,customerPO2);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void deleteCustomerTest(){
        Integer id = customerDao.getLatest().getId();
        customerService.deleteById(id);

        Assertions.assertNull(customerService.findCustomerById(id));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findTest(){
        List<CustomerPO> customerPOS = customerService.getAllCustomers();
        Assertions.assertNotNull(customerPOS);
    }
}
