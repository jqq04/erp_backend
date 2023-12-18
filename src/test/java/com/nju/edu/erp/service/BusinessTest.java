package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.business.BookVO;
import com.nju.edu.erp.model.vo.MyFilter;
import com.nju.edu.erp.model.vo.SheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.business.BusinessSituationTable;
import com.nju.edu.erp.model.vo.business.SaleDetailVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class BusinessTest {

    @Autowired
    private BusinessService businessService;

    /**
     * 测试查看销售明细表
     * 测试正常情况
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void showSaleDetailsTest1(){
        String beginDateStr = "2022-05-22 00:00:00";
        String endDateStr = "2022-05-26 00:00:00";
        List<SaleDetailVO> saleDetailVOs = businessService.showSaleDetails(beginDateStr,endDateStr);
        Assertions.assertNotNull(saleDetailVOs);
        Assertions.assertFalse(saleDetailVOs.isEmpty());

    }


    /**
     * 测试查看销售明细表
     * 两个时间都为null时，要返回全部的销售明细
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void showSaleDetailsTest2(){
        List<SaleDetailVO> saleDetailVOs = businessService.showSaleDetails(null, null);
        Assertions.assertNotNull(saleDetailVOs);
        Assertions.assertFalse(saleDetailVOs.isEmpty());
    }

    /**
     * 测试查看销售明细表
     * 测试一个时间为空，另一个正常的情况下，应该返回null
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void showSaleDetailsTest3(){
        List<SaleDetailVO> saleDetailVOs = businessService.showSaleDetails(null,"2022-05-22 00:00:00");
        Assertions.assertNull(saleDetailVOs);
    }

    /**
     * 测试查看销售明细表
     * 测试一个时间为空，另一个正常的情况下，应该返回null
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void showSaleDetailsTest4(){
        List<SaleDetailVO> saleDetailVOs = businessService.showSaleDetails("2022-05-22 00:00:00",null);
        Assertions.assertNull(saleDetailVOs);
    }

    /**
     * 测试查看销售明细表
     * 测试传输时间格式错误时，应该返回null
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void showSaleDetailsTest5(){
        List<SaleDetailVO> saleDetailVOs = businessService.showSaleDetails("2022-12-22","2022-05-22 00:00:00");
        Assertions.assertNull(saleDetailVOs);
    }



    /**
     * 测试查看经营历程表
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void showBusinessProcessTest(){
        String beginDateStr = "2022-05-22 00:00:00";
        String endDateStr = "2022-05-26 00:00:00";
        List<MyFilter> sheetVOList = businessService.showBusinessProcess(beginDateStr,endDateStr);
        Assertions.assertNotNull(sheetVOList);
        Assertions.assertFalse(sheetVOList.isEmpty());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void writeBackTest(){
        String beginDateStr = "2022-05-22 00:00:00";
        String endDateStr = "2022-05-26 00:00:00";
        List<MyFilter> sheetVOList = businessService.showBusinessProcess(beginDateStr,endDateStr);
        SheetVO sheetVO = (SheetVO) sheetVOList.get(0).getValue();
        UserVO userVO = UserVO.builder()
                .name("caiwu")
                .role(Role.FINANCIAL_STAFF)
                .build();
        businessService.writeBack(userVO, sheetVO);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void showBusinessSituationTest(){
        BusinessSituationTable businessSituationTable = businessService.showBusinessSituation(null, null);
        Assertions.assertNotNull(businessSituationTable);
        Assertions.assertNotNull(businessSituationTable.getItems());
        Assertions.assertFalse(businessSituationTable.getItems().isEmpty());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void createBookTest(){
        businessService.createBook();

        String id = businessService.getAllBookId().get(0);

        Assertions.assertNotNull(id);
        BookVO bookVO = businessService.getBookById(id);
        Assertions.assertNotNull(bookVO);
        Assertions.assertNotNull(bookVO.getAccountList());
        Assertions.assertFalse(bookVO.getCustomerList().isEmpty());

    }
}
