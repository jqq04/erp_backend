package com.nju.edu.erp.service;

import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.vo.business.BookVO;
import com.nju.edu.erp.model.vo.MyFilter;
import com.nju.edu.erp.model.vo.SheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.business.BusinessSituationTable;
import com.nju.edu.erp.model.vo.business.SaleDetailVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

public interface BusinessService {
    /**
     * 获取一段时间内的销售明细
     * @param beginDateStr 开始时间 "yyyy-MM-dd HH:mm:ss"
     * @param endDateStr 结束时间 "yyyy-MM-dd HH:mm:ss"
     *                   如果开始时间和结束时间是null，则返回所有的销售明细
     * @return 销售明细列表
     */
    List<SaleDetailVO> showSaleDetails(String beginDateStr, String endDateStr);


    /**
     * 返回一个销售明细表中，所有商品名的集合
     * @return Set<String productName>
     */
    Set<MyFilter> getProductSet(String beginDateStr, String endDateStr);
    /**
     * 返回一个销售明细表中，所有客户（id）的集合
     * @return Set<Integer supplier>
     */
    Set<MyFilter> getSupplierSet(String beginDateStr, String endDateStr);
    /**
     * 返回一个销售明细表中，所有销售员（salesman）的集合
     * @return Set<String salesman>
     */
    Set<MyFilter> getSalesmanSet(String beginDateStr, String endDateStr);


//    /**
//     * 在service层处理导出excel，已废弃
//     * @param response
//     */
//    void exportSaleDetails(HttpServletResponse response);

    /**
     * 获取一段时间内的经营历程单据列表
     * @param beginDateStr 开始时间 "yyyy-MM-dd HH:mm:ss"
     * @param endDateStr 结束时间 "yyyy-MM-dd HH:mm:ss"
     *                   如果开始时间和结束时间是null，则返回所有
     * @return 封装了单据类型和单据实体的对象的列表
     */
    List<MyFilter> showBusinessProcess(String beginDateStr, String endDateStr);

    /**
     * 对一个单据进行红冲操作(实际是写回操作）
     * 当传来的单据与原单据是相反数关系，则是红冲单据；如果是修改关系，则是红冲复制单据
     * @param sheetVO 红冲（复制）单据
     * @throws MyServiceException = "P0001","错误的单据类型或单据编号"
     */
    void writeBack(UserVO userVO, SheetVO sheetVO);


    /**
     * 获取一段时间内的经营情况表
     * @param beginDateStr 开始时间 "yyyy-MM-dd HH:mm:ss"
     * @param endDateStr 结束时间 "yyyy-MM-dd HH:mm:ss"
     * 如果开始时间和结束时间是null，则返回所有
     * 如果时间信息有错误，则返回null（如开始时间大于结束时间）
     * @return 经营情况表
     */
    BusinessSituationTable showBusinessSituation(String beginDateStr, String endDateStr);


    /**
     * 建账，按照当前时间，建立起一套账
     */
    void createBook();

    /**
     * 根据套账编号获取套账
     * @param bookId 套账编号
     * @return 套账
     */
    BookVO getBookById(String bookId);

    /**
     * 获取所有套账的编号
     * @return 套账编号的列表
     * 不存在套账则返回null
     */
    List<String> getAllBookId();

}
