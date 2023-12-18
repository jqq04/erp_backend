package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.SaleReturnsSheetDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.dao.WarehouseOutputSheetDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.enums.sheetState.WarehouseOutputSheetState;
import com.nju.edu.erp.model.po.saleReturns.*;
import com.nju.edu.erp.model.po.sale.*;
import com.nju.edu.erp.model.po.warehouse.WarehouseOutputSheetPO;
import com.nju.edu.erp.model.vo.sale.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.saleReturns.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SaleReturnsServiceTest {

    @Autowired
    SaleService saleService;
    @Autowired
    SaleReturnsService saleReturnsService;
    @Autowired
    SaleSheetDao saleSheetDao;
    @Autowired
    SaleReturnsSheetDao saleReturnsSheetDao;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    WarehouseOutputSheetDao warehouseOutputSheetDao;

    @Test
    @Transactional
    @Rollback(value = true)
    public void makeSheetTest(){
        UserVO xiaoshoujingli = UserVO.builder()
                .name("xiaoshoujingli")
                .role(Role.SALE_MANAGER)
                .build();
        UserVO kucun = UserVO.builder()
                .name("kucun")
                .role(Role.INVENTORY_MANAGER)
                .build();
        //先做销售单
        List<SaleSheetContentVO> saleSheetContentVOS = new ArrayList<>();
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400000")
                .quantity(50)
                .remark("Test XSTH-product1")
                .unitPrice(BigDecimal.valueOf(3200))
                .build());
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400001")
                .quantity(60)
                .remark("Test XSTH-product2")
                .unitPrice(BigDecimal.valueOf(4200))
                .build());
        SaleSheetVO saleSheetVO = SaleSheetVO.builder()
                .saleSheetContent(saleSheetContentVOS)
                .supplier(2)
                .discount(BigDecimal.valueOf(0.8))
                .voucherAmount(BigDecimal.valueOf(300))
                .remark("Test XSTH")
                .build();
        saleService.makeSaleSheet(xiaoshoujingli,saleSheetVO);

        //找到刚刚做的销售单
        SaleSheetPO saleSheet= saleSheetDao.getLatest();
        //进行销售单审批
        saleService.approval(saleSheet.getId(), SaleSheetState.PENDING_LEVEL_2);
        saleService.approval(saleSheet.getId(), SaleSheetState.SUCCESS);
        //将生成的对应的出库单审批
        WarehouseOutputSheetPO warehouseOutputSheet = warehouseOutputSheetDao.getLatest();
        warehouseService.approvalOutputSheet(kucun, warehouseOutputSheet.getId(),WarehouseOutputSheetState.SUCCESS);

        //做销售退货单
        List<SaleReturnsSheetContentVO> saleReturnsSheetContentVOS = new ArrayList<>();
        saleReturnsSheetContentVOS.add(SaleReturnsSheetContentVO.builder()
                .pid("0000000000400000")
                .quantity(50)
                .remark("TH Test XSTH-product1")
                .build());
        saleReturnsSheetContentVOS.add(SaleReturnsSheetContentVO.builder()
                .pid("0000000000400001")
                .quantity(60)
                .remark("TH Test XSTH-product2")
                .build());
        SaleReturnsSheetVO saleReturnsSheetVO = SaleReturnsSheetVO.builder()
                .saleReturnsSheetContent(saleReturnsSheetContentVOS)
                .saleSheetId(saleSheet.getId())
                .remark("Test XSTH: rnm 退钱")
                .build();
        saleReturnsService.makeSaleReturnsSheet(xiaoshoujingli,saleReturnsSheetVO);

        //验证销售退货单是否存在，是否为空
        SaleReturnsSheetPO saleReturnsSheet = saleReturnsSheetDao.getLatest();
        Assertions.assertNotNull(saleReturnsSheet);
        List<SaleReturnsSheetContentPO> saleReturnsSheetContentPOS = saleReturnsSheetDao.findContentBySheetId(saleReturnsSheet.getId());
        Assertions.assertNotNull(saleReturnsSheetContentPOS);

        //验证查找功能
        SaleReturnsSheetVO saleReturnsSheetVO1 = saleReturnsService.getSaleReturnsSheetById(saleReturnsSheet.getId());
        Assertions.assertNotNull(saleReturnsSheetVO1);
        Assertions.assertNotNull(saleReturnsSheetVO1.getSaleReturnsSheetContent());
    }
}
