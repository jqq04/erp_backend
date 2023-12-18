package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EmployeeDao {
    /**
     * 存入一条员工基本信息
     */
    int saveEmployee(EmployeePO employeePO);
    /**
     * 存入一条员工薪资信息
     */
    int saveEmployeeSalaryInfo(EmployeePO employeePO);
    /**
     * 修改一条员工基本信息
     */
    int updateEmployee(EmployeePO employeePO);
    /**
     * 修改一条员工薪资信息
     */
    int updateEmployeeSalaryInfo(EmployeePO employeePO);
    /**
     * 根据编号删除一条员工基本信息
     * @param id 员工编号
     */
    void deleteEmployeeById(String id);
    /**
     * 根据编号删除一条员工薪资信息
     * @param id 员工编号
     */
    void deleteEmployeeSalaryInfoById(String id);
    /**
     * 根据编号获取员工信息
     */
    EmployeePO getEmployeeById(String id);
    /**
     * 根据姓名获取员工信息
     */
    EmployeePO getEmployeeByName(String Name);
    /**
     * 根据岗位获取员工信息,只会返回未离职的员工
     */
    List<EmployeePO> getEmployeeByRole(Role role);
    /**
     * 获取全部员工信息,只会返回未离职的员工
     */
    List<EmployeePO> getAllEmployee();
    /**
     * 获取最近一条员工信息
     */
    EmployeePO getLatest();
    /**
     * 根据员工编号，找到对应的用户账号
     * @param employeeId 员工编号
     * @return 对应的用户账号
     */
    User findUserByEmployeeId(String employeeId);
    /**
     * 根据用户id，找到对应的员工信息
     * @param userId 用户id(UserVO.id)
     * @return 对应的员工信息
     */
    EmployeePO findEmployeeByUserId(Integer userId);
    /**
     * 将员工与账号关联绑定
     * @param employeeId 员工编号
     * @param userId 账号id
     */
    void bind(String employeeId, Integer userId);
    /**
     * 设置员工在岗信息
     * @param employeeId 员工编号
     * @param onPost 员工在岗还是离职
     */
    int setPost(String employeeId, boolean onPost);
    /**
     * 查询在岗信息
     * @param employeeId 员工编号
     * @return 是否在岗
     */
    boolean isOnPost(String employeeId);

    /**
     * 设置员工的默认年终奖为null（员工入职时）
     */
    void setDefaultFinalBonus(String employeeId);
}
