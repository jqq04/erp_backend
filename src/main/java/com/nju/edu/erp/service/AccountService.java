package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.AccountVO;

import java.util.List;

public interface AccountService {
    /**
     * 创建一个账户
     * @param accountVO 账户信息
     */
    void createAccount(AccountVO accountVO);
    /**
     * 根据id删除账户
     * @param id 账户id
     */
    void deleteById(Integer id);
    /**
     * 修改账户信息
     */
    void updateAccount(AccountVO accountVO);
    /**
     * 根据名称查询账户，返回一个含有名称关键词的列表（模糊查找）
     * @param name 关键词/账户名
     * @return 所有含有该name的账户的列表
     * 未找到时返回null
     */
    List<AccountVO> findByName(String name);

    /**
     * 根据名称精确查找账户
     * @param name 账户名
     * @return 精确的账户信息
     */
    AccountVO findByNameExactly(String name);

    /**
     * 获取所有账户信息
     */
    List<AccountVO> queryAllAccount();

    /**
     * 获取用于发放工资的账户
     */
    AccountVO getSalaryPaymentAccount();
}
