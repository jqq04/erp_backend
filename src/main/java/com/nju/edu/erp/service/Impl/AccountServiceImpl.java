package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final static String salaryPaymentAccount = "salary_payment_account";

    private final AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @Override
    public void createAccount(AccountVO accountVO) {
        AccountPO accountPO = new AccountPO();
        BeanUtils.copyProperties(accountVO, accountPO);
        try {
            accountDao.createAccount(accountPO);
        }
        catch (DuplicateKeyException exception){
            throw new MyServiceException("E0001", "创建失败! 账户名重复!");
        }
    }

    @Override
    public void deleteById(Integer id) {
        int lines = accountDao.deleteById(id);
        if (lines == 0)
            throw new MyServiceException("E0003", "账户不存在");
    }

    @Override
    public void updateAccount(AccountVO accountVO) {
        AccountPO accountPO = new AccountPO();
        BeanUtils.copyProperties(accountVO, accountPO);
        try {
            accountDao.update(accountPO);
        }catch (DuplicateKeyException exception){
            throw new MyServiceException("E0002", "更新失败！新账户名与其他账户名冲突");
        }
    }

    @Override
        public List<AccountVO> findByName(String name) {
        List<AccountVO> res = new ArrayList<>();

        List<AccountPO> pos = accountDao.findByName(name);
        if (pos.isEmpty())
            return null;
        for (AccountPO po: pos){
            AccountVO vo = new AccountVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    @Override
    public AccountVO findByNameExactly(String name) {
        AccountPO accountPO = accountDao.findByNameExactly(name);
        if (accountPO == null)
            return null;
        AccountVO accountVO = new AccountVO();
        BeanUtils.copyProperties(accountPO,accountVO);
        return accountVO;
    }

    @Override
    public List<AccountVO> queryAllAccount() {
        List<AccountVO> allVOs = new ArrayList<>();
        List<AccountPO> allPOs = accountDao.findAll();
        for (AccountPO po: allPOs){
            AccountVO vo = new AccountVO();
            BeanUtils.copyProperties(po, vo);
            allVOs.add(vo);
        }
        return allVOs;
    }

    @Override
    public AccountVO getSalaryPaymentAccount(){
        return findByNameExactly(salaryPaymentAccount);
    }
}
