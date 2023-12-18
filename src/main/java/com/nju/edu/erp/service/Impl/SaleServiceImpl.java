package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.po.sale.*;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.promotion.PromotionOfferInfo;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.model.vo.sale.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormVO;
import com.nju.edu.erp.service.*;
import com.nju.edu.erp.strategy.promotion.offer.PromotionOfferInfoGenerator;
import com.nju.edu.erp.strategy.promotion.offer.PromotionOfferStrategy;
import com.nju.edu.erp.strategy.promotion.offer.PromotionOfferStrategyContext;
import com.nju.edu.erp.strategy.promotion.require.PromotionRequireStrategy;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleSheetDao saleSheetDao;

    private final ProductDao productDao;

    private final CustomerDao customerDao;

    private final ProductService productService;

    private final CustomerService customerService;

    private final WarehouseService warehouseService;

    private final PromotionService promotionService;

    private final PromotionRequireStrategy promotionRequireStrategy;

    private final PromotionOfferStrategyContext promotionOfferStrategyContext;

    @Autowired
    public SaleServiceImpl(SaleSheetDao saleSheetDao, ProductDao productDao, CustomerDao customerDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService, PromotionService promotionService, PromotionRequireStrategy promotionRequireStrategy, PromotionOfferStrategyContext promotionOfferStrategyContext) {
        this.saleSheetDao = saleSheetDao;
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.promotionService = promotionService;
        this.promotionRequireStrategy = promotionRequireStrategy;
        this.promotionOfferStrategyContext = promotionOfferStrategyContext;
    }


    /**
     * 制定销售单
     *
     * @param userVO 操作员
     * @param saleSheetVO 销售单
     */
    @Override
    @Transactional
    public void makeSaleSheet(UserVO userVO, SaleSheetVO saleSheetVO) {
        // TODO
        ////  DONE
        // 需要持久化销售单（SaleSheet）和销售单content（SaleSheetContent），其中总价或者折后价格的计算需要在后端进行
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        SaleSheetPO saleSheetPO = new SaleSheetPO();
        BeanUtils.copyProperties(saleSheetVO,saleSheetPO);
        // 此处根据指定单据人员确定操作员
        saleSheetPO.setOperator(userVO.getName());
        saleSheetPO.setCreateTime(new Date());
        // 根据最近的一条销售单据编号确定此次单据编号
        SaleSheetPO latest = saleSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSD");
        saleSheetPO.setId(id);
        saleSheetPO.setState(SaleSheetState.PENDING_LEVEL_1);
        // 以下为处理saleSheetContent
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleSheetContentPO> saleSheetContentPOList = new ArrayList<>();
        for (SaleSheetContentVO content: saleSheetVO.getSaleSheetContent()){
            SaleSheetContentPO saleSheetContentPO = new SaleSheetContentPO();
            BeanUtils.copyProperties(content,saleSheetContentPO);
            saleSheetContentPO.setSaleSheetId(id);
            BigDecimal unitPrice = saleSheetContentPO.getUnitPrice();
            //若销售单上未表明单价，直接查询数据库中商品零售价
            if(unitPrice == null) {
                ProductPO product = productDao.findById(content.getPid());
                unitPrice = product.getRetailPrice();
                saleSheetContentPO.setUnitPrice(unitPrice);
            }
            saleSheetContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(saleSheetContentPO.getQuantity())));
            saleSheetContentPOList.add(saleSheetContentPO);
            totalAmount = totalAmount.add(saleSheetContentPO.getTotalPrice());
        }
        saleSheetDao.saveBatchSheetContent(saleSheetContentPOList);
        saleSheetPO.setRawTotalAmount(totalAmount);

        if (saleSheetVO.getDiscount() == null) {
            //收集促销信息，合并优惠信息
            saleSheetVO.setRawTotalAmount(totalAmount);
            List<PromotionVO> validPromotionList = promotionService.getValidPromotion(); //获取目前有效的促销活动
            PromotionOfferInfo promotionOfferInfo = new PromotionOfferInfo();
            for (PromotionVO promotion : validPromotionList) {
                //必须满足促销条件
                if (promotionRequireStrategy.satisfied(promotion, saleSheetVO)) {
                    if (promotion.getDiscount() != null) {
                        PromotionOfferStrategy promotionOfferStrategy = promotionOfferStrategyContext.getResource("折扣优惠");
                        promotionOfferInfo = PromotionOfferInfoGenerator.generate(promotionOfferInfo, promotionOfferStrategy.offer(promotion));
                    }
                    if (promotion.getVoucher() != null) {
                        PromotionOfferStrategy promotionOfferStrategy = promotionOfferStrategyContext.getResource("代金券优惠");
                        promotionOfferInfo = PromotionOfferInfoGenerator.generate(promotionOfferInfo, promotionOfferStrategy.offer(promotion));
                    }
                    if (promotion.getGifts() != null) {
                        PromotionOfferStrategy promotionOfferStrategy = promotionOfferStrategyContext.getResource("赠品优惠");
                        promotionOfferInfo = PromotionOfferInfoGenerator.generate(promotionOfferInfo, promotionOfferStrategy.offer(promotion));
                    }
                }
            }

            saleSheetPO.setDiscount(promotionOfferInfo.getDiscount() == null ? BigDecimal.ONE : promotionOfferInfo.getDiscount());
            saleSheetPO.setVoucherAmount(promotionOfferInfo.getVoucher() == null ? BigDecimal.ZERO : promotionOfferInfo.getVoucher());
            //todo 生成库存赠品单
        }
        // 折让后总额 = 折让前总额 * 折让 - 代金券总额
        BigDecimal finalAmount = saleSheetPO.getRawTotalAmount().multiply(saleSheetPO.getDiscount()).subtract(saleSheetPO.getVoucherAmount());
        saleSheetPO.setFinalAmount(finalAmount);
        saleSheetDao.saveSheet(saleSheetPO);
    }

    /**
     * 根据状态获取销售单[不包括content信息](state == null 则获取所有进货单)
     *
     * @param state 进货单状态
     * @return 销售单列表
     */
    @Override
    @Transactional
    public List<SaleSheetVO> getSaleSheetByState(SaleSheetState state) {
        // TODO
        //// DONE
        // 根据单据状态获取销售单（注意：VO包含SaleSheetContent）
        // 依赖的dao层部分方法未提供，需要自己实现（已添加）
        List<SaleSheetVO> res = new ArrayList<>();
        List<SaleSheetPO> all;
        if(state == null) {
            all = saleSheetDao.findAllSheet();
        } else {
            all = saleSheetDao.findAllByState(state);
        }
        for(SaleSheetPO po: all) {
            SaleSheetVO vo = new SaleSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleSheetContentPO> alll = saleSheetDao.findContentBySheetId(po.getId());
            List<SaleSheetContentVO> vos = new ArrayList<>();
            for (SaleSheetContentPO p : alll) {
                SaleSheetContentVO v = new SaleSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据销售单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleSheetId 销售单id
     * @param state       销售单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String saleSheetId, SaleSheetState state) {
        // TODO
        //// DONE
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        /* 一些注意点：
            1. 二级审批成功之后需要进行
                 1. 修改单据状态
                 2. 更新商品表
                 3. 更新客户表
                 4. 新建出库草稿
            2. 一级审批状态不能直接到审批完成状态； 二级审批状态不能回到一级审批状态
         */

        // 这个关于状态的条件判断比较复杂，具体的解释可以参考PurchaseServiceImp.approval相似代码中的注释
        if(state.equals(SaleSheetState.FAILURE)) {
            SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
            if(saleSheet.getState() == SaleSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = saleSheetDao.updateSheetState(saleSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleSheetState prevState;
            if(state.equals(SaleSheetState.SUCCESS)) {
                prevState = SaleSheetState.PENDING_LEVEL_2;
            } else if(state.equals(SaleSheetState.PENDING_LEVEL_2)) {
                prevState = SaleSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            // 修改状态
            int effectLines = saleSheetDao.updateSheetStateOnPrev(saleSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(SaleSheetState.SUCCESS)) {

                // 更新商品表的最新售价
                    // 根据saleSheetId查到对应的content -> 得到商品id和单价
                    // 根据商品id和单价更新商品最近进价recentRp
                // 同时填写出库单内容
                List<SaleSheetContentPO> saleSheetContent =  saleSheetDao.findContentBySheetId(saleSheetId);
                List<WarehouseOutputFormContentVO> warehouseOutputFormContentVOS = new ArrayList<>();

                for(SaleSheetContentPO content : saleSheetContent) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    productInfoVO.setId(content.getPid());
                    productInfoVO.setRecentRp(content.getUnitPrice());
                    productService.updateProduct(productInfoVO);

//                    here! 要考虑库存不够的情况吗？？ 就像进货退货里那样
                    WarehouseOutputFormContentVO woContentVO = new WarehouseOutputFormContentVO();
                    woContentVO.setSalePrice(content.getUnitPrice());
                    woContentVO.setQuantity(content.getQuantity());
                    woContentVO.setRemark(content.getRemark());
                    woContentVO.setPid(content.getPid());
                    warehouseOutputFormContentVOS.add(woContentVO);
                }
                // 更新客户表(更新应收字段)
                // 更新应收 receivable
                SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
                CustomerPO customerPO = customerService.findCustomerById(saleSheet.getSupplier());
                customerPO.setReceivable(customerPO.getReceivable().add(saleSheet.getFinalAmount()));
                customerService.updateCustomer(customerPO);

                // 制定出库单草稿(在这里关联销售单)
                // 调用创建出库单的方法
                WarehouseOutputFormVO warehouseOutputFormVO = new WarehouseOutputFormVO();
                warehouseOutputFormVO.setOperator(null); // 暂时不填操作人(确认草稿单的时候填写)
                warehouseOutputFormVO.setSaleSheetId(saleSheetId);
                warehouseOutputFormVO.setList(warehouseOutputFormContentVOS);
                warehouseService.productOutOfWarehouse(warehouseOutputFormVO);
            }
        }
    }

    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     * @param salesman 销售人员的名字
     * @param beginDateStr 开始时间字符串
     * @param endDateStr 结束时间字符串
     * @return
     */
    @Override
    public CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman,String beginDateStr,String endDateStr){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date beginTime =dateFormat.parse(beginDateStr);
            Date endTime=dateFormat.parse(endDateStr);
            if(beginTime.compareTo(endTime)>0){
                return null;
            }else{
                return saleSheetDao.getMaxAmountCustomerOfSalesmanByTime(salesman,beginTime,endTime);
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据销售单Id搜索销售单信息
     * @param saleSheetId 销售单Id
     * @return 销售单全部信息
     */
    @Override
    public SaleSheetVO getSaleSheetById(String saleSheetId) {
        SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleSheetId);
        if(saleSheetPO == null) return null;
        List<SaleSheetContentPO> contentPO = saleSheetDao.findContentBySheetId(saleSheetId);
        SaleSheetVO sVO = new SaleSheetVO();
        BeanUtils.copyProperties(saleSheetPO, sVO);
        List<SaleSheetContentVO> saleSheetContentVOList = new ArrayList<>();
        for (SaleSheetContentPO content:
                contentPO) {
            SaleSheetContentVO sContentVO = new SaleSheetContentVO();
            BeanUtils.copyProperties(content, sContentVO);
            saleSheetContentVOList.add(sContentVO);
        }
        sVO.setSaleSheetContent(saleSheetContentVOList);
        return sVO;
    }

}
