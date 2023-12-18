package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;

import java.util.List;

public interface CustomerService {
    /**
     * 根据id更新客户信息
     * @param customerPO 客户信息
     */
    void updateCustomer(CustomerPO customerPO);

    /**
     * 新增一个客户
     * @param customerVO 客户信息
     */
    CustomerPO addCustomer(CustomerVO customerVO);
    /**
     * 根据id删除一个客户
     */
     void deleteById(Integer id);
    /**
     * 根据type查找对应类型的客户
     * @param type 客户类型
     * @return 客户列表
     */
    List<CustomerPO> getCustomersByType(CustomerType type);

    /**
     * 返回所有客户列表
     */
    List<CustomerPO> getAllCustomers();


    CustomerPO findCustomerById(Integer supplier);
}
