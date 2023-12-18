package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.PaymentDao;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.payment.PaymentSheetContentPO;
import com.nju.edu.erp.model.po.payment.PaymentSheetPO;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.payment.PaymentSheetContentVO;
import com.nju.edu.erp.model.vo.payment.PaymentSheetVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentDao paymentDao;

    private final CustomerService customerService;

    private final AccountService accountService;

    @Autowired
    public PaymentServiceImpl(PaymentDao paymentDao, CustomerService customerService, AccountService accountService) {
        this.paymentDao = paymentDao;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @Override
    public void makeSheet(UserVO userVO, PaymentSheetVO paymentSheetVO) {
        PaymentSheetPO paymentSheetPO = new PaymentSheetPO();
        BeanUtils.copyProperties(paymentSheetVO,paymentSheetPO);
        paymentSheetPO.setOperator((userVO.getName()));//根据当前操作员确定单据中的操作员
        paymentSheetPO.setCreateTime(new Date());
        //根据最近单据 确定新单据编号
        PaymentSheetPO latest = paymentDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null?null: latest.getId(), "FKD");
        paymentSheetPO.setId(id);
        paymentSheetPO.setState(PaymentSheetState.PENDING);

        //以下为处理content
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PaymentSheetContentPO> pContentPOList = new ArrayList<>();
        for (PaymentSheetContentVO content: paymentSheetVO.getPaymentSheetContent()){
            PaymentSheetContentPO pContentPO = new PaymentSheetContentPO();
            BeanUtils.copyProperties(content, pContentPO);
            pContentPO.setPaymentSheetId(id);

            pContentPOList.add(pContentPO);
            totalAmount = totalAmount.add(content.getAmount());
        }
        paymentSheetPO.setTotalAmount(totalAmount);

        //存入数据库
        paymentDao.saveSheet(paymentSheetPO);
        paymentDao.saveList(pContentPOList);
    }

    @Override
    @Transactional
    public void approval(String paymentSheetId, PaymentSheetState state) {
        //首先根据id寻找到对应的收款单，将其状态更改
        PaymentSheetPO paymentSheet = paymentDao.findOneById(paymentSheetId);
        if (state.equals(PaymentSheetState.PENDING)) //如果目的状态是PENDING，不允许
            throw new MyServiceException("F0001","更改状态失败!不能更改状态为PENDING");
        if (paymentSheet.getState().equals(PaymentSheetState.FAILURE) || paymentSheet.getState().equals(PaymentSheetState.SUCCESS))
            throw new MyServiceException("F0002","更新状态失败!单据已经被审批！");
        paymentDao.updateState(paymentSheetId, state);

        //单据审批通过，将更改公司银行账户信息和客户应付信息
        if (state.equals(PaymentSheetState.SUCCESS)){
            //更新客户应付
            CustomerPO customerPO = customerService.findCustomerById(paymentSheet.getSupplier());
            customerPO.setPayable(customerPO.getPayable().subtract(paymentSheet.getTotalAmount()));
            customerService.updateCustomer(customerPO);

            //更新银行账户余额
            List<PaymentSheetContentPO> paymentSheetContentPOS = paymentDao.findContentBySheetId(paymentSheetId);
            for (PaymentSheetContentPO content:paymentSheetContentPOS){
                AccountVO accountVO = accountService.findByNameExactly(content.getAccount());
                accountVO.setAmount(accountVO.getAmount().subtract(content.getAmount()));
                accountService.updateAccount(accountVO);
            }
        }
    }

    @Override
    public List<PaymentSheetVO> getSheetByState(PaymentSheetState state) {
        List<PaymentSheetVO> paymentSheetVOS = new ArrayList<>();
        List<PaymentSheetPO> paymentSheetPOS;
        if (state == null)
            paymentSheetPOS = paymentDao.findAll();
        else
            paymentSheetPOS = paymentDao.findAllByState(state);
        for (PaymentSheetPO po: paymentSheetPOS){
            PaymentSheetVO vo = new PaymentSheetVO();
            BeanUtils.copyProperties(po, vo);
            //从po复制到vo
            List<PaymentSheetContentPO> pContentPOs = paymentDao.findContentBySheetId(po.getId());
            List<PaymentSheetContentVO> pContentVOs = new ArrayList<>();
            for (PaymentSheetContentPO pContentPO: pContentPOs){
                PaymentSheetContentVO pContentVO = new PaymentSheetContentVO();
                BeanUtils.copyProperties(pContentPO, pContentVO);
                pContentVOs.add(pContentVO);
            }
            vo.setPaymentSheetContent(pContentVOs);
            paymentSheetVOS.add(vo);
        }
        return paymentSheetVOS;
    }

    @Override
    public PaymentSheetVO getSheetById(String paymentSheetId) {
        PaymentSheetVO vo = new PaymentSheetVO();
        PaymentSheetPO po = paymentDao.findOneById(paymentSheetId);
        if (po == null)
            return null;
        BeanUtils.copyProperties(po, vo);

        List<PaymentSheetContentPO> pContentPOs = paymentDao.findContentBySheetId(po.getId());
        List<PaymentSheetContentVO> pContentVOs = new ArrayList<>();
        for (PaymentSheetContentPO pContentPO: pContentPOs){
            PaymentSheetContentVO pContentVO = new PaymentSheetContentVO();
            BeanUtils.copyProperties(pContentPO, pContentVO);
            pContentVOs.add(pContentVO);
        }
        vo.setPaymentSheetContent(pContentVOs);

        return vo;
    }
}
