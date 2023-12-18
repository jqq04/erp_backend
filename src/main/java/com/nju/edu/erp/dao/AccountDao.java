package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.AccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountDao {
    /**
     * 创建账户
     */
    int createAccount(AccountPO accountPO);
    /**
     * 根据id删除账户
     */
    int deleteById(Integer id);
    /**
     * 修改账户信息
     */
    int update(AccountPO accountPO);
    /**
     * 根据名称模糊查找账户
     * @param name 名称关键词
     * @return 包含name关键词的所有account列表
     */
    List<AccountPO> findByName(String name);
    /**
     * 根据名称精确查询账户
     */
    AccountPO findByNameExactly(String name);
    /**
     * 根据id查询账户
     */
    AccountPO findById(Integer id);
    /**
     * 获取所有账户信息
     */
    List<AccountPO> findAll();
}
