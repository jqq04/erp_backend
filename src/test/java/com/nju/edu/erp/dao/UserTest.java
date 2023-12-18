package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserTest {
    @Autowired
    UserDao userDao;

    @Test
    @Transactional
    @Rollback(value = true)
    void deleteUserTest(){
        User user = userDao.findByUsername("seecoder");
        userDao.deleteUserById(user.getId());

        Assertions.assertNull(userDao.findByUsername(user.getName()));
    }
}
