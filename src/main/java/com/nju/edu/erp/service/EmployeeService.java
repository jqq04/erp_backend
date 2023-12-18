package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.EmployeeVO;

import java.util.List;

public interface EmployeeService {
    /**
     * 员工入职,添加员工信息，创建账号
     *  会为员工创建一个默认账户，其中账户名为员工编号，密码为123456
     */
    void induction(EmployeeVO employeeVO);
    /**
     * 员工离职，注销账号
     * @param id 员工编号
     */
    void employeeQuit(String id);
    /**
     * 更改员工信息
     */
    void updateEmployee(EmployeeVO employeeVO);
    /**
     * 根据编号删除员工
     * @param id 员工编号
     */
    void deleteEmployeeById(String id);
    /**
     * 根据岗位返回员工列表,只会返回未离职的员工
     */
    List<EmployeeVO> getEmployeeByRole(Role role);
    /**
     * 获取全部员工信息列表,只会返回未离职的员工
     */
    List<EmployeeVO> getAllEmployee();
    /**
     * 根据姓名返回员工信息
     */
    EmployeeVO getEmployeeByName(String name);
}
