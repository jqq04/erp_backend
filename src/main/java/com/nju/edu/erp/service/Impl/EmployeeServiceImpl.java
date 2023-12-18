package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.dao.SalaryDao;
import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.EmployeeVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.service.UserService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;

    private final UserService userService;

    private final UserDao userDao;

    private final SalaryDao salaryDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao, UserService userService, UserDao userDao, SalaryDao salaryDao) {
        this.employeeDao = employeeDao;
        this.userService = userService;
        this.userDao = userDao;
        this.salaryDao = salaryDao;
    }

    /**
     * 员工入职,添加员工信息，创建账号
     * 会为员工创建一个默认账户，其中账户名为员工编号，密码为123456
     */
    @Override
    public void induction(EmployeeVO employeeVO) {
        //登记员工信息
        EmployeePO employeePO = new EmployeePO();
        BeanUtils.copyProperties(employeeVO, employeePO);
        employeePO.setPostSalary(salaryDao.getPostSalary(employeePO.getRole()));//设置岗位工资
        //设置员工编号
        EmployeePO latest = employeeDao.getLatest();
        String id = IdGenerator.generateEmployeeId(latest == null? null : latest.getId());
        employeePO.setId(id);
        //存入员工信息,设为在岗
        employeeDao.saveEmployee(employeePO);
        employeeDao.saveEmployeeSalaryInfo(employeePO);
        employeeDao.setPost(id,true);
        //设置默认年终奖为null
        employeeDao.setDefaultFinalBonus(id);
        //为员工创建账户
        //默认用户名为员工编号，密码为123456
        UserVO userVO = UserVO.builder()
                .role(employeeVO.getRole())
                .name(id)
                .password("123456")
                .build();
        userService.register(userVO);
        //将员工与账号关联绑定起来
        User user = userDao.findByUsername(id);
        employeeDao.bind(id, user.getId());
    }

    /**
     * 员工离职，注销账号
     * @param id 员工编号
     */
    @Override
    public void employeeQuit(String id) {
        //设置为离职状态
        employeeDao.setPost(id, false);
        //注销账号
        User user = employeeDao.findUserByEmployeeId(id);
        userService.delete(user.getId());
    }

    /**
     * 更改员工信息
     */
    @Override
    public void updateEmployee(EmployeeVO employeeVO) {
        EmployeePO employeePO = new EmployeePO();
        BeanUtils.copyProperties(employeeVO, employeePO);
        employeePO.setPostSalary(salaryDao.getPostSalary(employeePO.getRole()));//根据岗位设置岗位工资
        employeeDao.updateEmployee(employeePO);
        employeeDao.updateEmployeeSalaryInfo(employeePO);
    }

    /**
     * 根据编号删除员工
     */
    @Override
    public void deleteEmployeeById(String id) {
        //如果员工在职，抛出报错信息
        if (employeeDao.isOnPost(id))
            throw new MyServiceException("G0001", "员工还在职，无法删除信息");
        //删除员工信息
        employeeDao.deleteEmployeeById(id);
        employeeDao.deleteEmployeeSalaryInfoById(id);
    }

    /**
     * 根据岗位返回员工列表,只会返回未离职的员工
     */
    @Override
    public List<EmployeeVO> getEmployeeByRole(Role role) {
        List<EmployeePO> employeePOS = employeeDao.getEmployeeByRole(role);
        List<EmployeeVO> employeeVOS = new ArrayList<>();
        for (EmployeePO employeePO: employeePOS){
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employeePO,employeeVO);
            employeeVOS.add(employeeVO);
        }
        return employeeVOS;
    }

    /**
     * 获取全部员工信息列表,只会返回未离职的员工
     */
    @Override
    public List<EmployeeVO> getAllEmployee() {
        List<EmployeePO> employeePOS = employeeDao.getAllEmployee();
        List<EmployeeVO> employeeVOS = new ArrayList<>();
        for (EmployeePO employeePO: employeePOS){
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employeePO,employeeVO);
            employeeVOS.add(employeeVO);
        }
        return employeeVOS;
    }

    /**
     * 根据姓名返回员工信息
     */
    @Override
    public EmployeeVO getEmployeeByName(String name) {
        EmployeePO employeePO = employeeDao.getEmployeeByName(name);
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employeePO,employeeVO);
        return employeeVO;
    }
}
