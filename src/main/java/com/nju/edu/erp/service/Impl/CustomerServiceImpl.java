package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * 根据id更新客户信息
     *
     * @param customerPO 客户信息
     */
    @Override
    public void updateCustomer(CustomerPO customerPO) {
        customerDao.updateOne(customerPO);
    }

    /**
     * 新增客户
     * @param customerVO 客户信息
     */
    @Override
    public CustomerPO addCustomer(CustomerVO customerVO) {
        CustomerPO customerPO = new CustomerPO();
        BeanUtils.copyProperties(customerVO,customerPO);
        customerDao.addOne(customerPO);

        return customerDao.getLatest();
    }

    /**
     * 根据id删除客户
     * @param id 客户id
     */
    @Override
    public void deleteById(Integer id) {
        CustomerPO customerPO = customerDao.findOneById(id);
        if (customerPO == null)
            throw new MyServiceException("B0004","客户不存在,删除失败!");
        customerDao.deleteOneById(id);
    }

    /**
     * 根据type查找对应类型的客户
     *
     * @param type 客户类型
     * @return 客户列表
     */
    @Override
    public List<CustomerPO> getCustomersByType(CustomerType type) {
        return customerDao.findAllByType(type);
    }

    @Override
    public List<CustomerPO> getAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    public CustomerPO findCustomerById(Integer supplier) {
        return customerDao.findOneById(supplier);
    }
}
