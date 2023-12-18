package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.ClockInDao;
import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.model.po.ClockInRecordPO;
import com.nju.edu.erp.model.vo.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class ClockInTest {

    @Autowired
    private ClockInService clockInService;

    @Autowired
    private ClockInDao clockInDao;

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    @Rollback(value = true)
    public void findTest1(){
        List<ClockInRecordPO> clockInRecordList = clockInService.getAllRecord();
        Assertions.assertFalse(clockInRecordList.isEmpty());

        List<ClockInRecordPO> clockInRecordListOnCurrentDay = clockInService.getAllRecordOnCurrentDay();
        Assertions.assertFalse(clockInRecordListOnCurrentDay.isEmpty());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findTest2(){
        UserVO userVO = UserVO.builder()
                .name("67")
                .build();
        String beginDateString = "2022-07-06";
        String endDateString = "2022-07-06";
        List<ClockInRecordPO> clockInRecordList = clockInService.getPersonalRecordByDate(userVO, beginDateString, endDateString);
        Assertions.assertFalse(clockInRecordList.isEmpty());
    }


    @Test
    @Transactional
    @Rollback(value = true)
    public void clockInTest1(){
        UserVO userVO = UserVO.builder()
                .name("67")
                .build();
        clockInService.clockIn(userVO);

        ClockInRecordPO clockInRecordPO = clockInService.getPersonalRecordOnCurrentDay(userVO);
        Assertions.assertTrue(clockInRecordPO.isHasClockIn());
    }
}
