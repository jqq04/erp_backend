package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.ReceiptDao;
import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.receipt.ReceiptSheetContentPO;
import com.nju.edu.erp.model.po.receipt.ReceiptSheetPO;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptSheetContentVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptSheetVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ReceiptService;
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
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptDao receiptDao;

    private final CustomerService customerService;

    private final AccountService accountService;

    @Autowired
    public ReceiptServiceImpl(ReceiptDao receiptDao,CustomerService customerService,AccountService accountService){
        this.receiptDao = receiptDao;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @Override
    public void makeSheet(UserVO userVO, ReceiptSheetVO receiptSheetVO) {
        ReceiptSheetPO receiptSheetPO = new ReceiptSheetPO();
        BeanUtils.copyProperties(receiptSheetVO, receiptSheetPO);
        receiptSheetPO.setOperator(userVO.getName());//根据当前操作员确定单据中的操作员
        receiptSheetPO.setCreateTime(new Date());
        //根据最近单据 确定新单据编号
        ReceiptSheetPO latest = receiptDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "SKD");
        receiptSheetPO.setId(id);
        receiptSheetPO.setState(ReceiptSheetState.PENDING);

        //以下处理content
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<ReceiptSheetContentPO> rContentPOList = new ArrayList<>();
        for (ReceiptSheetContentVO content: receiptSheetVO.getReceiptSheetContent()){
            ReceiptSheetContentPO rContentPO = new ReceiptSheetContentPO();
            BeanUtils.copyProperties(content, rContentPO);
            rContentPO.setReceiptSheetId(id);

            rContentPOList.add(rContentPO);
            totalAmount = totalAmount.add(content.getAmount());
        }
        receiptSheetPO.setTotalAmount(totalAmount);

        //存入数据库
        receiptDao.saveSheet(receiptSheetPO);
        receiptDao.saveList(rContentPOList);
    }

    @Override
    @Transactional
    public void approval(String receiptSheetId, ReceiptSheetState state) {
        //首先根据id寻找到对应的收款单，将其状态更改
        ReceiptSheetPO receiptSheet = receiptDao.findOneById(receiptSheetId);
        if (state.equals(ReceiptSheetState.PENDING)) //如果目的状态是PENDING，不允许
            throw new MyServiceException("F0001","更改状态失败!不能更改状态为PENDING");
        if (receiptSheet.getState().equals(ReceiptSheetState.SUCCESS) || receiptSheet.getState().equals(ReceiptSheetState.FAILURE))
            throw new MyServiceException("F0002","更新状态失败!单据已经被审批！");
        receiptDao.updateState(receiptSheetId, state);

        //单据审批通过，将更改公司银行账户信息和客户应收信息
        if (state.equals(ReceiptSheetState.SUCCESS)){
            //更新客户应收
            CustomerPO customerPO = customerService.findCustomerById(receiptSheet.getSupplier());
            customerPO.setReceivable(customerPO.getReceivable().subtract(receiptSheet.getTotalAmount()));
            customerService.updateCustomer(customerPO);

            //更新银行账户余额
            List<ReceiptSheetContentPO> receiptSheetContentPOS = receiptDao.findContentBySheetId(receiptSheetId);
            for (ReceiptSheetContentPO content:receiptSheetContentPOS){
                AccountVO accountVO = accountService.findByNameExactly(content.getAccount());
                accountVO.setAmount(accountVO.getAmount().add(content.getAmount()));
                accountService.updateAccount(accountVO);
            }
        }
    }

    @Override
    public List<ReceiptSheetVO> getSheetByState(ReceiptSheetState state) {
        List<ReceiptSheetVO> receiptSheetVOs = new ArrayList<>();
        List<ReceiptSheetPO> receiptSheetPOS;
        if (state == null)
            receiptSheetPOS = receiptDao.findAll();
        else
            receiptSheetPOS = receiptDao.findAllByState(state);
        for (ReceiptSheetPO po: receiptSheetPOS){
            ReceiptSheetVO vo = new ReceiptSheetVO();
            BeanUtils.copyProperties(po,vo);
            //从po复制content到vo
            List<ReceiptSheetContentPO> rContentPOs = receiptDao.findContentBySheetId(po.getId());
            List<ReceiptSheetContentVO> rContentVOs = new ArrayList<>();
            for (ReceiptSheetContentPO rContentPO: rContentPOs){
                ReceiptSheetContentVO rContentVO = new ReceiptSheetContentVO();
                BeanUtils.copyProperties(rContentPO, rContentVO);
                rContentVOs.add(rContentVO);
            }
            vo.setReceiptSheetContent(rContentVOs);
            receiptSheetVOs.add(vo);
        }
        return receiptSheetVOs;
    }

    @Override
    public ReceiptSheetVO getSheetById(String receiptSheetId) {
        ReceiptSheetVO vo = new ReceiptSheetVO();
        ReceiptSheetPO po = receiptDao.findOneById(receiptSheetId);
        if (po == null)
            return null;
        BeanUtils.copyProperties(po, vo);

        List<ReceiptSheetContentPO> rContentPOs = receiptDao.findContentBySheetId(po.getId());
        List<ReceiptSheetContentVO> rContentVOs = new ArrayList<>();
        for (ReceiptSheetContentPO rContentPO: rContentPOs){
            ReceiptSheetContentVO rContentVO = new ReceiptSheetContentVO();
            BeanUtils.copyProperties(rContentPO, rContentVO);
            rContentVOs.add(rContentVO);
        }
        vo.setReceiptSheetContent(rContentVOs);

        return vo;
    }
}
