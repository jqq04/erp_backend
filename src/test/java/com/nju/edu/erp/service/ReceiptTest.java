package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.ReceiptDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.po.receipt.ReceiptSheetPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptSheetContentVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptSheetVO;
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
public class ReceiptTest {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ReceiptDao receiptDao;

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
        List<ReceiptSheetContentVO> receiptSheetContentVOs = new ArrayList<>();
        receiptSheetContentVOs.add(ReceiptSheetContentVO.builder()
                .account("test")
                .amount(new BigDecimal("10000.00"))
                .remark("第一笔转账")
                .build());
        receiptSheetContentVOs.add(ReceiptSheetContentVO.builder()
                .account("bank_account_1")
                .amount(new BigDecimal("40000.00"))
                .remark("第二笔转账")
                .build());
        ReceiptSheetVO receiptSheetVO = ReceiptSheetVO.builder()
                .supplier(3)
                .remark("从测试中的销售商收款测试，测试1")
                .receiptSheetContent(receiptSheetContentVOs)
                .build();

        //获取原有数据
        BigDecimal old_receivable = customerDao.findOneById(3).getReceivable();
        BigDecimal old_remain1 = accountDao.findByNameExactly("test").getAmount();
        BigDecimal old_remain2 = accountDao.findByNameExactly("bank_account_1").getAmount();

        //开始制作收款单
        receiptService.makeSheet(userVO,receiptSheetVO);
        ReceiptSheetPO receiptSheetPO = receiptDao.getLatest();
        Assertions.assertEquals(receiptSheetPO.getRemark(), receiptSheetVO.getRemark());
        //审批收款单
        receiptService.approval(receiptSheetPO.getId(), ReceiptSheetState.SUCCESS);

        //获取新数据
        BigDecimal new_receivable = customerDao.findOneById(3).getReceivable();
        BigDecimal new_remain1 = accountDao.findByNameExactly("test").getAmount();
        BigDecimal new_remain2 = accountDao.findByNameExactly("bank_account_1").getAmount();

        Assertions.assertEquals(0, old_receivable.subtract(new BigDecimal("50000.00")).compareTo(new_receivable));
        Assertions.assertEquals(0, old_remain1.add(new BigDecimal("10000.00")).compareTo(new_remain1));
        Assertions.assertEquals(0, old_remain2.add(new BigDecimal("40000.00")).compareTo(new_remain2));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findTest(){
        List<ReceiptSheetVO> receiptSheetVOS = receiptService.getSheetByState(ReceiptSheetState.PENDING);
        Assertions.assertNotNull(receiptSheetVOS);
        List<ReceiptSheetContentVO> receiptSheetContentVOList = receiptSheetVOS.get(0).getReceiptSheetContent();
        Assertions.assertNotNull(receiptSheetContentVOList);
    }
}
