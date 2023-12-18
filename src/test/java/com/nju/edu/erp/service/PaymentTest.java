package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.PaymentDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.payment.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.payment.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PaymentTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AccountDao accountDao;

    @Test
    @Transactional
    @Rollback(value = true)
    public void makeAndApproveSheetTest(){ //测试能否正常生成和审批收款单，测试能否对客户和账户信息产生影响
        //生成vo
        UserVO userVO = UserVO.builder()
                .name("caiwu")
                .role(Role.FINANCIAL_STAFF)
                .build();
        List<PaymentSheetContentVO> paymentSheetContentVOS = new ArrayList<>();
        paymentSheetContentVOS.add(PaymentSheetContentVO.builder()
                .account("test")
                .amount(new BigDecimal("10000.00"))
                .remark("第一笔转账")
                .build());
        paymentSheetContentVOS.add(PaymentSheetContentVO.builder()
                .account("bank_account_1")
                .amount(new BigDecimal("40000.00"))
                .remark("第二笔转账")
                .build());
        PaymentSheetVO paymentSheetVO = PaymentSheetVO.builder()
                .supplier(3)
                .remark("从测试中的销售商收款测试，测试1")
                .paymentSheetContent(paymentSheetContentVOS)
                .build();

        //获取原有数据
        BigDecimal old_payable = customerDao.findOneById(3).getPayable();
        BigDecimal old_remain1 = accountDao.findByNameExactly("test").getAmount();
        BigDecimal old_remain2 = accountDao.findByNameExactly("bank_account_1").getAmount();

        //开始制作收款单
        paymentService.makeSheet(userVO, paymentSheetVO);
        PaymentSheetPO paymentSheetPO = paymentDao.getLatest();
        Assertions.assertEquals(paymentSheetPO.getRemark(), paymentSheetVO.getRemark());
        //审批收款单
        paymentService.approval(paymentSheetPO.getId(), PaymentSheetState.SUCCESS);

        //获取新数据
        BigDecimal new_payable = customerDao.findOneById(3).getPayable();
        BigDecimal new_remain1 = accountDao.findByNameExactly("test").getAmount();
        BigDecimal new_remain2 = accountDao.findByNameExactly("bank_account_1").getAmount();

        Assertions.assertEquals(0, old_payable.subtract(new BigDecimal("50000.00")).compareTo(new_payable));
        Assertions.assertEquals(0, old_remain1.subtract(new BigDecimal("10000.00")).compareTo(new_remain1));
        Assertions.assertEquals(0, old_remain2.subtract(new BigDecimal("40000.00")).compareTo(new_remain2));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findTest(){
        List<PaymentSheetVO> paymentSheetVOS = paymentService.getSheetByState(PaymentSheetState.PENDING);
        Assertions.assertNotNull(paymentSheetVOS);
        List<PaymentSheetContentVO> paymentSheetContentVOList = paymentSheetVOS.get(0).getPaymentSheetContent();
        Assertions.assertNotNull(paymentSheetContentVOList);
    }
}
