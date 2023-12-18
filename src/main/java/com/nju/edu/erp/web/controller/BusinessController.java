package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.salary.SalarySheetPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.business.SaleDetailExcel;
import com.nju.edu.erp.model.vo.business.SaleDetailVO;
import com.nju.edu.erp.model.vo.payment.PaymentSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptSheetVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.BusinessService;
import com.nju.edu.erp.utils.ExcelUtils;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/business")
public class BusinessController {
    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    /**
     * 查看销售明细表
     * @param beginDateStr 开始时间 yyyy-MM-dd HH:mm:ss
     * @param endDateStr 结束时间 yyyy-MM-dd HH:mm:ss
     * 如果时间传null，则返回所有
     * @return List<SaleDetailVO>
     */
    @GetMapping("/sale-details/show")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response showSaleDetails(@RequestParam(required = false) String beginDateStr,@RequestParam(required = false) String endDateStr){
        return Response.buildSuccess(businessService.showSaleDetails(beginDateStr,endDateStr));
    }

    /**
     * 返回一个销售明细表中，所有商品名的集合
     * @param beginDateStr 开始时间 yyyy-MM-dd HH:mm:ss
     * @param endDateStr 结束时间 yyyy-MM-dd HH:mm:ss
     * 如果时间传null，则返回所有
     * @return Set<String productName>
     */
    @GetMapping("/sale-details/product-set")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getProductList(@RequestParam(required = false) String beginDateStr,@RequestParam(required = false) String endDateStr){
        return Response.buildSuccess(businessService.getProductSet(beginDateStr,endDateStr));
    }

    /**
     * 返回一个销售明细表中，所有客户（id）的集合
     * @param beginDateStr 开始时间 yyyy-MM-dd HH:mm:ss
     * @param endDateStr 结束时间 yyyy-MM-dd HH:mm:ss
     * 如果时间传null，则返回所有
     * @return Set<Integer supplier>
     */
    @GetMapping("/sale-details/supplier-set")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getSupplierList(@RequestParam(required = false) String beginDateStr,@RequestParam(required = false) String endDateStr){
        return Response.buildSuccess(businessService.getSupplierSet(beginDateStr,endDateStr));
    }

    /**
     * 返回一个销售明细表中，所有销售员（salesman）的集合
     * @param beginDateStr 开始时间 yyyy-MM-dd HH:mm:ss
     * @param endDateStr 结束时间 yyyy-MM-dd HH:mm:ss
     * 如果时间传null，则返回所有
     * @return Set<String salesman>
     */
    @GetMapping("/sale-details/salesman-set")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response getSalesmanList(@RequestParam(required = false) String beginDateStr,@RequestParam(required = false) String endDateStr){
        return Response.buildSuccess(businessService.getSalesmanSet(beginDateStr,endDateStr));
    }

    /**
     * 查看经营历程表，即是该时间段内的所有单据的列表
     * @param beginDateStr 开始时间 yyyy-MM-dd HH:mm:ss
     * @param endDateStr 结束时间 yyyy-MM-dd HH:mm:ss
     * 如果时间传null，则返回所有
     * @return List<MyFilter>  其中MyFilter.value = sheetVO,  MyFilter.text = 单据类型
     * SheetVO是各种单据VO的父类，因此前端要根据不同的VO展示不同的内容
     */
    @GetMapping("/business-process/show")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response showBusinessProcess(@RequestParam(required = false) String beginDateStr,@RequestParam(required = false) String endDateStr){
        return Response.buildSuccess(businessService.showBusinessProcess(beginDateStr,endDateStr));
    }

//    /*
//    该方法存在bug，即前端传来的数据会自动转为基类SheetVO，丢失了子类的信息，故废弃
//     * 处理一个红冲单据
//     * @param userVO 用户信息，前端不用传
//     * @param sheetVO 红冲单据基类
//     */
//    @PostMapping("/business-process/write-back")
//    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
//    public Response writeBack(UserVO userVO, @RequestBody SheetVO sheetVO){
//        businessService.writeBack(userVO,sheetVO);
//        return Response.buildSuccess();
//    }

    @PostMapping("/business-process/write-back/purchase-sheet")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response writeBackPurchaseSheet(UserVO userVO, @RequestBody PurchaseSheetVO purchaseSheetVO){
        businessService.writeBack(userVO,purchaseSheetVO);
        return Response.buildSuccess();
    }

    @PostMapping("/business-process/write-back/purchase-returns-sheet")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response writeBackPurchaseReturnsSheet(UserVO userVO, @RequestBody PurchaseReturnsSheetVO purchaseReturnsSheetVO){
        businessService.writeBack(userVO,purchaseReturnsSheetVO);
        return Response.buildSuccess();
    }

    @PostMapping("/business-process/write-back/sale-sheet")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response writeBackSaleSheet(UserVO userVO, @RequestBody SaleSheetVO saleSheetVO){
        businessService.writeBack(userVO,saleSheetVO);
        return Response.buildSuccess();
    }

    @PostMapping("/business-process/write-back/sale-returns-sheet")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response writeBackSaleReturnsSheet(UserVO userVO, @RequestBody SaleReturnsSheetVO saleReturnsSheetVO){
        businessService.writeBack(userVO,saleReturnsSheetVO);
        return Response.buildSuccess();
    }

    @PostMapping("/business-process/write-back/payment-sheet")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response writeBackPaymentSheet(UserVO userVO, @RequestBody PaymentSheetVO paymentSheetVO){
        businessService.writeBack(userVO,paymentSheetVO);
        return Response.buildSuccess();
    }

    @PostMapping("/business-process/write-back/receipt-sheet")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response writeBackReceiptSheet(UserVO userVO, @RequestBody ReceiptSheetVO receiptSheetVO){
        businessService.writeBack(userVO,receiptSheetVO);
        return Response.buildSuccess();
    }

    @PostMapping("/business-process/write-back/salary-sheet")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response writeBackSalarySheet(UserVO userVO, @RequestBody SalarySheetPO salarySheetPO){
        businessService.writeBack(userVO,salarySheetPO);
        return Response.buildSuccess();
    }

    /**
     * 查看经营情况表
     * @param beginDateStr 开始时间 yyyy-MM-dd HH:mm:ss
     * @param endDateStr 结束时间 yyyy-MM-dd HH:mm:ss
     * 如果时间传null，则返回所有
     * @return BusinessSituationTable 这是一个封装好了所有有关信息的对象
     */
    @GetMapping("/business-situation/show")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response showBusinessSituation(@RequestParam(required = false) String beginDateStr,@RequestParam(required = false) String endDateStr){
        return Response.buildSuccess(businessService.showBusinessSituation(beginDateStr,endDateStr));
    }

//    /**
//     * 导出销售明细表
//     */
//    @PostMapping(value = "/sale-details/excel")
//    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
//    public Response exportSaleDetails(HttpServletResponse response) {
//        //businessService.exportSaleDetails(response);
//        try {
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setCharacterEncoding("utf-8");
//            String fileName = URLEncoder.encode("销售明细表", "UTF-8").replaceAll("\\+", "%20");
//            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//            EasyExcel.write(response.getOutputStream(), SaleDetailVO.class)
//                    .sheet("销售明细")
//                    .doWrite(businessService.showSaleDetails(null,null));
//
//            return Response.buildSuccess();
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("出错了");
//            return Response.buildFailed("P0001","WRONG");
//        }
//
//    }

    /**
     * 导出销售明细表为excel
     * @throws IOException
     * 目前存在bug，导出并下载后，excel显示文件被损坏
     */
    @GetMapping("/sale-details/excel")
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.FINANCIAL_STAFF})
    public Response exportSaleDetails(HttpServletResponse httpServletResponse) throws IOException {
        List<SaleDetailVO> saleDetailVOList = businessService.showSaleDetails(null,null);
        List<SaleDetailExcel> saleDetailExcelList = new ArrayList<>();
        for (SaleDetailVO vo:saleDetailVOList){
            SaleDetailExcel excelItem = new SaleDetailExcel();
            BeanUtils.copyProperties(vo,excelItem);
            saleDetailExcelList.add(excelItem);
        }
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + "销售明细表" + ".xlsx" );
        ExcelUtils.writeExcel(httpServletResponse, saleDetailExcelList);

        return Response.buildSuccess();
    }

    /**
     * 创建一个新的套账，时间点为当前时间
     */
    @PostMapping("/book/create")
    @Authorized(roles = {Role.ADMIN,Role.FINANCIAL_STAFF})
    public Response createBook(){
        businessService.createBook();
        return Response.buildSuccess();
    }

    /**
     * 传入一个套账编号，获取该套账
     * @param bookId 套账编号
     * @return bookVO
     */
    @GetMapping("/book/get")
    @Authorized(roles = {Role.ADMIN,Role.FINANCIAL_STAFF})
    public Response getBook(@RequestParam(value = "id") String bookId){
        return Response.buildSuccess(businessService.getBookById(bookId));
    }

    /**
     * 获取所有套账的编号
     * List<String id>
     * if code = "P0002" then msg = '不存在对应编号的套账'
     */
    @GetMapping("/book/ids")
    @Authorized(roles = {Role.ADMIN,Role.FINANCIAL_STAFF})
    public Response getAllBookId(){
        return Response.buildSuccess(businessService.getAllBookId());
    }
}
