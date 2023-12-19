package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.PromotionDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.promotion.PromotionPO;
import com.nju.edu.erp.model.po.promotion.PromotionPackagePO;
import com.nju.edu.erp.model.vo.promotion.PromotionPackageVO;
import com.nju.edu.erp.model.vo.promotion.PromotionVO;
import com.nju.edu.erp.service.PromotionService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionDao promotionDao;

    @Autowired
    public PromotionServiceImpl(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    @Transactional
    public void createPromotion(PromotionVO promotionVO) {

        if (promotionVO.getBeginTime() == null || promotionVO.getEndTime() == null)
            throw new MyServiceException("Z0001","时间不能为空");

        PromotionPO promotionPO = new PromotionPO();
        BeanUtils.copyProperties(promotionVO, promotionPO);

        try {
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date beginTime = simpleDateFormat.parse(promotionVO.getBeginTime());
            Date endTime = simpleDateFormat.parse(promotionVO.getEndTime());
            promotionPO.setBeginTime(beginTime);
            promotionPO.setEndTime(endTime);
        }catch (ParseException e){
            throw new MyServiceException("Z0002","时间格式错误");
        }

        PromotionPO latest = promotionDao.getLatest();
        String id = IdGenerator.generateSheetId(latest==null?null:latest.getId(), "PRO");
        promotionPO.setId(id);

        boolean hasPromotion = false;//用来判断是否有优惠，一个促销必须至少有一个优惠方式
        hasPromotion = promotionVO.getDiscount() != null || promotionVO.getVoucher() != null;

        //存入策略
        promotionDao.savePromotion(promotionPO);

        //存入要求包
        List<PromotionPackagePO> promotionPackagePOList = new ArrayList<>();
        List<PromotionPackageVO> promotionPackageVOList = promotionVO.getPackages();
        if (promotionPackageVOList != null) {
            if (!(promotionPackageVOList.size()==1 && promotionPackageVOList.get(0).getProductId().equals("") && promotionPackageVOList.get(0).getQuantity() == null)) { //不是默认情况，就要处理
                for (PromotionPackageVO vo : promotionPackageVOList) {
                    if (vo.getProductId().equals("") || vo.getQuantity() == null)
                        throw new MyServiceException("Z0003", "商品名和数量不能为空");

                    PromotionPackagePO po = new PromotionPackagePO();
                    BeanUtils.copyProperties(vo, po);
                    po.setPromotionId(id);

                    promotionPackagePOList.add(po);
                }
                promotionDao.savePackages(promotionPackagePOList);
            }
        }

        //存入赠品包
        List<PromotionPackagePO> promotionGiftPOList = new ArrayList<>();
        List<PromotionPackageVO> promotionGiftVOList = promotionVO.getGifts();
        if (promotionGiftVOList != null) {
            if (!(promotionGiftVOList.size() == 1 && promotionGiftVOList.get(0).getProductId().equals("") && promotionGiftVOList.get(0).getQuantity() == null)) { //不是默认情况，就要处理
                for (PromotionPackageVO vo : promotionGiftVOList) {
                    if (vo.getProductId().equals("") || vo.getProductId() == null || vo.getQuantity() == null)
                        throw new MyServiceException("Z0003", "商品名和数量不能为空");

                    PromotionPackagePO po = new PromotionPackagePO();
                    BeanUtils.copyProperties(vo, po);
                    po.setPromotionId(id);

                    promotionGiftPOList.add(po);
                }
                hasPromotion = true;
                promotionDao.saveGifts(promotionGiftPOList);
            }
        }
        if (!hasPromotion)
            throw new MyServiceException("Z0004","未选择促销优惠");
    }

    @Override
    public PromotionVO getPromotionById(String promotionId){
        PromotionPO promotionPO = promotionDao.getPromotionById(promotionId);
        List<PromotionPackagePO> promotionPackagePOList = promotionDao.getPackagesByPromotionId(promotionId);
        List<PromotionPackagePO> promotionGiftPOList = promotionDao.getGiftsByPromotionId(promotionId);

        PromotionVO promotionVO = new PromotionVO();
        BeanUtils.copyProperties(promotionPO, promotionVO);

        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        promotionVO.setBeginTime(simpleDateFormat.format(promotionPO.getBeginTime()));
        promotionVO.setEndTime(simpleDateFormat.format(promotionPO.getEndTime()));

        List<PromotionPackageVO> promotionPackageVOList = new ArrayList<>();
        for (PromotionPackagePO po: promotionPackagePOList){
            PromotionPackageVO vo = new PromotionPackageVO();
            BeanUtils.copyProperties(po,vo);
            promotionPackageVOList.add(vo);
        }
        if (promotionPackagePOList.isEmpty())
            promotionVO.setPackages(null);
        else
            promotionVO.setPackages(promotionPackageVOList);

        List<PromotionPackageVO> promotionGiftVOList = new ArrayList<>();
        for (PromotionPackagePO po: promotionGiftPOList){
            PromotionPackageVO vo = new PromotionPackageVO();
            BeanUtils.copyProperties(po,vo);
            promotionGiftVOList.add(vo);
        }
        if (promotionGiftPOList.isEmpty())
            promotionVO.setGifts(null);
        else
            promotionVO.setGifts(promotionGiftVOList);


        return promotionVO;
    }

    @Override
    public List<PromotionVO> getAllPromotion(){
        List<PromotionPO> promotionPOList = promotionDao.getAllPromotion();
        List<PromotionVO> promotionVOList = new ArrayList<>();
        for (PromotionPO promotionPO: promotionPOList){
            List<PromotionPackagePO> promotionPackagePOList = promotionDao.getPackagesByPromotionId(promotionPO.getId());
            List<PromotionPackagePO> promotionGiftPOList = promotionDao.getGiftsByPromotionId(promotionPO.getId());

            PromotionVO promotionVO = new PromotionVO();
            BeanUtils.copyProperties(promotionPO, promotionVO);

            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            promotionVO.setBeginTime(simpleDateFormat.format(promotionPO.getBeginTime()));
            promotionVO.setEndTime(simpleDateFormat.format(promotionPO.getEndTime()));

            List<PromotionPackageVO> promotionPackageVOList = new ArrayList<>();
            for (PromotionPackagePO po: promotionPackagePOList){
                PromotionPackageVO vo = new PromotionPackageVO();
                BeanUtils.copyProperties(po,vo);
                promotionPackageVOList.add(vo);
            }
            if (promotionPackagePOList.isEmpty())
                promotionVO.setPackages(null);
            else
                promotionVO.setPackages(promotionPackageVOList);

            List<PromotionPackageVO> promotionGiftVOList = new ArrayList<>();
            for (PromotionPackagePO po: promotionGiftPOList){
                PromotionPackageVO vo = new PromotionPackageVO();
                BeanUtils.copyProperties(po,vo);
                promotionGiftVOList.add(vo);
            }
            if (promotionGiftPOList.isEmpty())
                promotionVO.setGifts(null);
            else
                promotionVO.setGifts(promotionGiftVOList);

            promotionVOList.add(promotionVO);
        }
        return promotionVOList;
    }

    @Override
    public List<PromotionVO> getValidPromotion(){
        List<PromotionVO> res = new ArrayList<>();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowStr = simpleDateFormat.format(new Date());

        for (PromotionVO promotionVO : getAllPromotion())
            if (promotionVO.getBeginTime().compareTo(nowStr)<=0 && nowStr.compareTo(promotionVO.getEndTime())<=0)
                res.add(promotionVO);
        return res;
    }

    @Override
    @Transactional
    public void deletePromotion(String promotionId){
        PromotionPO promotionPO = promotionDao.getPromotionById(promotionId);
        if (promotionPO == null)
            throw new MyServiceException("Z0005","促销不存在");

        int ans = promotionDao.deletePromotion(promotionId);
        if (ans == 0)
            throw new MyServiceException("Z0006","删除失败");
    }
}
