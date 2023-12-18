package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.vo.AccountVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDao accountDao;

    @Test
    @Transactional
    @Rollback(value = true)
    public void createTest1(){  //测试正常创建账户
        AccountVO accountVO = AccountVO.builder()
                .name("公司账户1")
                .amount(BigDecimal.ZERO)
                .build();
        accountService.createAccount(accountVO);

        AccountPO accountPO = accountDao.findByNameExactly("公司账户1");
        Assertions.assertEquals(accountVO.getName(),accountVO.getName());
        Assertions.assertEquals(0,accountPO.getAmount().compareTo(accountVO.getAmount()));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void createTest2(){ //测试新账户名与原有的用户名冲突
        AccountVO accountVO = AccountVO.builder()
                .name("test") //与id=3的账户名重复
                .amount(BigDecimal.ZERO)
                .build();
        Assertions.assertThrowsExactly(MyServiceException.class,()->accountService.createAccount(accountVO));

    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void deleteTest(){
        Integer id = 3;
        accountService.deleteById(3);

        Assertions.assertNull(accountService.findByName("test"));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void updateTest1(){ //测试能正常更新的情况
        AccountVO accountVO = AccountVO.builder()
                .id(2)
                .name("账户的新名字")
                .amount(new BigDecimal("1.00"))
                .build();
        accountService.updateAccount(accountVO);

        AccountPO accountPO1 = new AccountPO();
        BeanUtils.copyProperties(accountVO, accountPO1);

        AccountPO accountPO2 = accountDao.findById(accountVO.getId());
        Assertions.assertEquals(accountPO1, accountPO2);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void updateTest2(){ //测试更新时新账户名与其他账户名冲突的情况
        AccountVO accountVO = AccountVO.builder()
                .id(3)
                .name("bank_account_1") //与id=1的账户名冲突
                .amount(new BigDecimal("1.00"))
                .build();
        Assertions.assertThrowsExactly(MyServiceException.class, ()->accountService.updateAccount(accountVO));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findTest(){ //测试根据名称模糊查找
        String name = "bank";
        List<AccountVO> accountVOList = accountService.findByName(name);
        Assertions.assertEquals(2, accountVOList.size());

        String name2 = "中文123hsiei";
        List<AccountVO> accountVOList2 = accountService.findByName(name2);
        Assertions.assertNull(accountVOList2);
    }
}
