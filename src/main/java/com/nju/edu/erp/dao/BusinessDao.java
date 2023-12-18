package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.ProductPO;
import com.nju.edu.erp.model.po.business.BookPO;
import com.nju.edu.erp.model.vo.business.BusinessSituationItem;
import com.nju.edu.erp.model.vo.business.SaleDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface BusinessDao {

    /**
     * 根据给定时间段获取销售明细
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 销售明细列表
     */
    List<SaleDetailVO> getSaleDetails(Date beginDate, Date endDate);

    /**
     * 获取全部销售明细
     * @return 销售明细列表
     */
    List<SaleDetailVO> getAllSaleDetails();

    /**
     * 根据给定时间段获取经营情况具体内容
     */
    List<BusinessSituationItem> getBusinessSituationItem(Date beginDate, Date endDate);

    /**
     * 获取全部经营情况具体内容
     */
    List<BusinessSituationItem> getAllBusinessSituationItem();

    /**
     * 获取最新套账（主套账）
     */
    BookPO getLatestBook();

    /**
     * 存入套账的主套账（标识套账）
     * 一套账由在数据库中由 一个主套账（标识套账） + 三个分套帐 组成
     * @param bookPO 主套账
     * @return 影响行数
     */
    int saveBook(BookPO bookPO);

    /**
     * 存入一个银行账户信息套账（分套账）
     * @param accountPOList 账户列表
     * @param bookId 对于的主套账编号
     */
    int saveAccountBook(@Param("list") List<AccountPO> accountPOList, String bookId);

    /**
     * 存入一个客户信息套账（分套账）
     */
    int saveCustomerBook(@Param("list") List<CustomerPO> customerPOList, String bookId);

    /**
     * 存入一个商品信息套账（分套账）
     */
    int saveProductBook(@Param("list") List<ProductPO> productPOList, String bookId);

    /**
     * 根据套账编号获取主套账
     * @param bookId 套账编号
     * @return 主套账PO
     */
    BookPO getBookById(String bookId);

    /**
     * 获取银行账户信息分套账，对于一个账户列表
     */
    List<AccountPO> getAccountBookById(String bookId);

    /**
     * 获取客户信息分套账
     */
    List<CustomerPO> getCustomerBookById(String bookId);

    /**
     * 获取商品信息分套账
     */
    List<ProductPO> getProductBookById(String bookId);

    /**
     * 获取所有套账的编号列表
     */
    List<String> getAllBookId();
}
