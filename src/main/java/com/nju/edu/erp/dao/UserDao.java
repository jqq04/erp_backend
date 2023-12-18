package com.nju.edu.erp.dao;


import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {

    User findByUsernameAndPassword(String username, String password);

    int createUser(User user);

    User findByUsername(String username);

    /**
     * 注销账户，删除账户信息
     * @param id
     */
    void deleteUserById(Integer id);
}
