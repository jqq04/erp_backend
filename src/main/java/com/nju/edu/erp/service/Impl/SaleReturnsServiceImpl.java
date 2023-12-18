package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.*;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.po.sale.SaleSheetContentPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import com.nju.edu.erp.model.po.saleReturns.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.saleReturns.SaleReturnsSheetPO;
import com.nju.edu.erp.model.po.warehouse.WarehousePO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.*;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaleReturnsServiceImpl implements SaleReturnsService {

    private final SaleSheetDao saleSheetDao;

    private final SaleReturnsSheetDao saleReturnsSheetDao;

    private final WarehouseDao warehouseDao;

    private final ProductDao productDao;

    private final CustomerDao customerDao;

    private final ProductService productService;

    private final CustomerService customerService;

    private final WarehouseService warehouseService;

    @Autowired
    public SaleReturnsServiceImpl(SaleSheetDao saleSheetDao, SaleReturnsSheetDao saleReturnsSheetDao, WarehouseDao warehouseDao, ProductDao productDao, CustomerDao customerDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService) {
        this.saleSheetDao = saleSheetDao;
        this.saleReturnsSheetDao = saleReturnsSheetDao;
        this.warehouseDao = warehouseDao;
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
    }

    @Override
    @Transactional
    public void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO) {
        SaleReturnsSheetPO saleReturnsSheetPO = new SaleReturnsSheetPO();
        BeanUtils.copyProperties(saleReturnsSheetVO,saleReturnsSheetPO);
        saleReturnsSheetPO.setOperator(userVO.getName()); // 此处根据指定单据人员确定操作员
        saleReturnsSheetPO.setCreateTime(new Date());

        // 根据最近的一条销售退货单据编号确定此次单据编号
        SaleReturnsSheetPO latest = saleReturnsSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");

        saleReturnsSheetPO.setId(id);
        saleReturnsSheetPO.setState(SaleReturnsSheetState.PENDING_LEVEL_1);

        //找到与本次销售退货对应的销售单
        SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleReturnsSheetVO.getSaleSheetId());
        List<SaleSheetContentPO> saleSheetContentPOList = saleSheetDao.findContentBySheetId(saleSheetPO.getId());

        //根据销售单确定销售退货单的客户（经销商）、业务员
        saleReturnsSheetPO.setSupplier(saleSheetPO.getSupplier());
        saleReturnsSheetPO.setSalesman(saleSheetPO.getSalesman());

        // 以下为处理saleReturnsSheetContent
        /*
            1.退货时支持退一个销售单的部分商品
            2.每个商品实际要退的价格为 (单价 * 折扣 - 消费券作用在该单个商品上的优惠价格)
                单价和折扣应该是销售时的单价和折扣
                消费券作用在该单个商品上的优惠价格”——即按单品在总价中的占比来计算分走了多少优惠额度
            3.优惠券不退回，是否需要体现？
         */

        //这里使用一个multiValueMap，用来根据商品pid找对应的所有content
        MultiValueMap<String, SaleSheetContentPO> map = new LinkedMultiValueMap<>();
        for (SaleSheetContentPO item:saleSheetContentPOList){
            map.add(item.getPid(),item);
        }
        BigDecimal totalReturnsAmount = BigDecimal.ZERO; //要退回的总金额
        List<SaleReturnsSheetContentPO> saleReturnsSheetContentPOList = new ArrayList<>();
        for (SaleReturnsSheetContentVO saleReturnsSheetContentVO : saleReturnsSheetVO.getSaleReturnsSheetContent()){


            // 该如何寻找到对应的单价？
            // 考虑一种情况，销售时售出了100件A产品，其中30件为批次1的，售价60（记为content1）；70件为批次2的，售价80（记为content2）。
            // 对应在销售内容单中，分属于content1和content2，具有相同的pid，但有不同的单价。
            // 假如现在退货80件，则按照content在销售单的顺序退回，退回30件批次1的和50件批次2的.
            // 并记录在退货单的两条content里
            // 也就是尽管前端传来的VO中只有一条content，要求退80件，但记录在PO里为两条，分别是批次1的30件和批次2的50件

            Integer quantity = saleReturnsSheetContentVO.getQuantity(); //要退回的数量
            BigDecimal voucherAmount = saleSheetPO.getVoucherAmount();

            //获取要退货的商品在销售单中的条目content
            List<SaleSheetContentPO> items = map.get(saleReturnsSheetContentVO.getPid());
            if (items.isEmpty()){
                throw new RuntimeException("对应的销售中不存在该商品!无法退货!");
            }

            for (SaleSheetContentPO saleSheetContentPO : items)
                if (saleSheetContentPO.getQuantity() >= quantity){ //该条content的商品已满足退货数量
                    // 单件商品所占消费券优惠额度 unitVoucher = voucherAmount * unitPrice / rawTotalPrice
                    BigDecimal unitVoucher = voucherAmount.multiply(saleSheetContentPO.getUnitPrice()).divide(saleSheetPO.getRawTotalAmount(),5, RoundingMode.FLOOR);
                    // 退回单价 returnsPrice =  unitPrice * discount - unitVoucher
                    BigDecimal returnsPrice = saleSheetContentPO.getUnitPrice().multiply(saleSheetPO.getDiscount()).subtract(unitVoucher);
                    // 该批次商品退回总价 returnsAmount = returnsPrice * quantity;
                    BigDecimal returnsAmount = returnsPrice.subtract(BigDecimal.valueOf(quantity));
                    totalReturnsAmount = totalReturnsAmount.add(returnsAmount);
                    quantity = 0;

                    //填写contentPO
                    SaleReturnsSheetContentPO saleReturnsSheetContentPO = new SaleReturnsSheetContentPO();
                    BeanUtils.copyProperties(saleReturnsSheetContentVO,saleReturnsSheetContentPO);
                    saleReturnsSheetContentPO.setSaleReturnsSheetId(id);
                    saleReturnsSheetContentPO.setUnitPrice(saleSheetContentPO.getUnitPrice());
                    saleReturnsSheetContentPO.setReturnsPrice(returnsPrice);
                    saleReturnsSheetContentPO.setReturnsAmount(returnsAmount);
                    //here 由于批次查询存在问题，暂时不填写批次
                    //saleReturnsSheetContentPO.setBatchId(saleSheetDao.findBatchId(saleSheetPO.getId(),saleSheetContentPO.getId()));

                    saleReturnsSheetContentPOList.add(saleReturnsSheetContentPO);
                }
              else{  //该条content的商品数量不足以满足，能退多少退多少
                    // 单件商品所占消费券优惠额度 unitVoucher = voucherAmount * unitPrice / rawTotalPrice
                    BigDecimal unitVoucher = voucherAmount.multiply(saleSheetContentPO.getUnitPrice()).divide(saleSheetPO.getRawTotalAmount(),5, RoundingMode.FLOOR);
                    // 退回单价 returnsPrice =  unitPrice * discount - unitVoucher
                    BigDecimal returnsPrice = saleSheetContentPO.getUnitPrice().multiply(saleSheetPO.getDiscount()).subtract(unitVoucher);
                    // 该批次商品退回总价 returnsAmount = returnsPrice * getQuantity;
                    BigDecimal returnsAmount = returnsPrice.subtract(BigDecimal.valueOf(saleSheetContentPO.getQuantity()));
                    totalReturnsAmount = totalReturnsAmount.add(returnsAmount);
                    quantity -= saleSheetContentPO.getQuantity();

                    //填写contentPO
                    SaleReturnsSheetContentPO saleReturnsSheetContentPO = new SaleReturnsSheetContentPO();
                    BeanUtils.copyProperties(saleReturnsSheetContentVO,saleReturnsSheetContentPO);
                    saleReturnsSheetContentPO.setSaleReturnsSheetId(id);
                    saleReturnsSheetContentPO.setUnitPrice(saleSheetContentPO.getUnitPrice());
                    saleReturnsSheetContentPO.setReturnsPrice(returnsPrice);
                    saleReturnsSheetContentPO.setReturnsAmount(returnsAmount);
                    //here 由于批次查询存在问题，暂时不填写批次
                    //saleReturnsSheetContentPO.setBatchId(saleSheetDao.findBatchId(saleSheetPO.getId(),saleSheetContentPO.getId()));

                    saleReturnsSheetContentPOList.add(saleReturnsSheetContentPO);
                }
        }
        saleReturnsSheetDao.saveBatchSheetContent(saleReturnsSheetContentPOList);
        saleReturnsSheetPO.setReturnsAmount(totalReturnsAmount);
        saleReturnsSheetDao.saveSheet(saleReturnsSheetPO);
    }

    /**
     * 根据单据状态获取销售退货单
     * @param state
     * @return
     */
    @Override
    @Transactional
    public List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state) {
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> all;
        if(state == null) {
            all = saleReturnsSheetDao.findAllSheet();
        } else {
            all = saleReturnsSheetDao.findAllByState(state);
        }
        for(SaleReturnsSheetPO po: all) {
            SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleReturnsSheetContentPO> alll = saleReturnsSheetDao.findContentBySheetId(po.getId());
            List<SaleReturnsSheetContentVO> vos = new ArrayList<>();
            for (SaleReturnsSheetContentPO p : alll) {
                SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleReturnsSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    @Override
    @Transactional
    public void approval(String saleReturnsSheetId, SaleReturnsSheetState state) {
        SaleReturnsSheetPO saleReturnsSheetPO = saleReturnsSheetDao.findSheetById(saleReturnsSheetId);
        if(state.equals(SaleReturnsSheetState.FAILURE)) {
            if(saleReturnsSheetPO.getState() == SaleReturnsSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = saleReturnsSheetDao.updateSheetState(saleReturnsSheetId,state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleReturnsSheetState prevState;
            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_2;
            } else if (state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleReturnsSheetDao.updateSheetStateOnPrev(saleReturnsSheetId,prevState,state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
            //审批完成
            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                // 将退回的商品重新入库，但不需要填写入库单
                // 重新入库的方式是：找回原来的库存信息，然后增加库存
                List<SaleReturnsSheetContentPO> saleReturnsSheetContentPOList = saleReturnsSheetDao.findContentBySheetId(saleReturnsSheetId);
                List<WarehousePO> warehousePOList = new ArrayList<>();
                // 那么所做的应该是增加库存，更新商品信息，更新客户信息

                for (SaleReturnsSheetContentPO srSheetContent : saleReturnsSheetContentPOList){
                    String pid = srSheetContent.getPid();
                    Integer quantity = srSheetContent.getQuantity();
                    //更新库存
                    //找到对应的库存条目
                    WarehousePO warehousePO = warehouseDao.findOneByPidAndBatchId(pid,quantity);
                    if (warehousePO == null) throw new RuntimeException("单据错误！请联系管理员！");
                    warehousePO.setQuantity(quantity);
                    warehouseDao.addQuantity(warehousePO);
                    //更新商品信息
                    ProductInfoVO productInfoVO = productService.getOneProductByPid(pid);
                    productInfoVO.setQuantity(productInfoVO.getQuantity()+quantity);
                    productService.updateProduct(productInfoVO);
                }

                //更新客户信息
                SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleReturnsSheetPO.getSaleSheetId());
                Integer supplier = saleSheetPO.getSupplier();
                CustomerPO customer = customerService.findCustomerById(supplier);
                BigDecimal receivableToDeduct = saleReturnsSheetPO.getReturnsAmount();
                customer.setReceivable(customer.getReceivable().subtract(receivableToDeduct));
                customerService.updateCustomer(customer);
            }
        }
    }


    /**
     * 根据销售单Id搜索销售退货单信息
     * @param saleReturnsSheetId 销售退货单Id
     * @return 销售退货单
     */
    @Override
    @Transactional
    public SaleReturnsSheetVO getSaleReturnsSheetById(String saleReturnsSheetId) {
        SaleReturnsSheetPO saleReturnsSheetPO = saleReturnsSheetDao.findSheetById(saleReturnsSheetId);
        if(saleReturnsSheetPO == null) return null;
        List<SaleReturnsSheetContentPO> contentPO = saleReturnsSheetDao.findContentBySheetId(saleReturnsSheetId);
        SaleReturnsSheetVO srVO = new SaleReturnsSheetVO();
        BeanUtils.copyProperties(saleReturnsSheetPO, srVO);
        List<SaleReturnsSheetContentVO> saleReturnsSheetContentVOList = new ArrayList<>();
        for (SaleReturnsSheetContentPO content: contentPO) {
            SaleReturnsSheetContentVO srContentVO = new SaleReturnsSheetContentVO();
            BeanUtils.copyProperties(content, srContentVO);
            saleReturnsSheetContentVOList.add(srContentVO);
        }
        srVO.setSaleReturnsSheetContent(saleReturnsSheetContentVOList);
        return srVO;
    }
}
