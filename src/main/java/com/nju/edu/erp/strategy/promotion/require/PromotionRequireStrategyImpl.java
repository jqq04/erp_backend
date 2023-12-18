package com.nju.edu.erp.strategy.promotion.require;

import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.promotion.PromotionPackageVO;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PromotionRequireStrategyImpl implements PromotionRequireStrategy {

    private final CustomerService customerService;

    @Autowired
    public PromotionRequireStrategyImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean satisfied(PromotionVO promotionVO, SaleSheetVO saleSheetVO){
        boolean timeSatisfied = timeSatisfied(promotionVO.getBeginTime(), promotionVO.getEndTime(), saleSheetVO.getCreateTime());
        boolean levelSatisfied = levelSatisfied(promotionVO.getCustomerLevel(), saleSheetVO.getSupplier());
        boolean amountSatisfied = amountSatisfied(promotionVO.getTotalAmount(), saleSheetVO.getRawTotalAmount());
        boolean packageSatisfied = packageSatisfied(promotionVO.getPackages(), saleSheetVO.getSaleSheetContent());

        return timeSatisfied && levelSatisfied && amountSatisfied && packageSatisfied;
    }

    private boolean timeSatisfied(String beginTimeStr, String endTimeStr, Date createTime){
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTimeStr = simpleDateFormat.format(createTime);
        return beginTimeStr.compareTo(createTimeStr)<=0 && createTimeStr.compareTo(endTimeStr)<=0;
    }

    private boolean levelSatisfied(Integer requireLevel, Integer supplier){
        if (requireLevel == null)
            return true;
        CustomerPO customerPO = customerService.findCustomerById(supplier);
        return requireLevel <= customerPO.getLevel();
    }

    private boolean packageSatisfied(List<PromotionPackageVO> requirePackages, List<SaleSheetContentVO> productItemList){
        //对于要求包里的每一个商品和数量，单据里的商品列表都要满足
        if (requirePackages == null)
            return true;
        for (PromotionPackageVO requirePackage:requirePackages){
            Integer quantity = requirePackage.getQuantity();
            for (SaleSheetContentVO item:productItemList)
                if (item.getPid().equals(requirePackage.getProductId()))
                    quantity -= item.getQuantity();
            //如果要求包里的有一个数量要求没达到，就返回false
            if (quantity > 0)
                return false;
        }
        return true;
    }

    private boolean amountSatisfied(BigDecimal requireAmount, BigDecimal rawTotalAmount){
        if (rawTotalAmount == null)
            return true;
        return requireAmount.compareTo(rawTotalAmount)<=0;
    }

}
