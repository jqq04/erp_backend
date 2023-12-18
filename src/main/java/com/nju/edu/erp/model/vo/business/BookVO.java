package com.nju.edu.erp.model.vo.business;

import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * 套账单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookVO {
    /**
     * 套账编号，"TZ-yyyyMMdd-xxxxx'
     */
    private String id;
    /**
     * 套账创建时间，同时也表示这套账所记录的时间点 yyyy-MM-dd HH:mm:ss
     */
    private Date createTime;
    /**
     * 套账的客户列表
     */
    private List<CustomerVO> customerList;
    /**
     * 套账的账户列表
     */
    private List<AccountVO> accountList;
    /**
     * 套账的商品列表
     */
    private List<ProductInfoVO> productList;
}
