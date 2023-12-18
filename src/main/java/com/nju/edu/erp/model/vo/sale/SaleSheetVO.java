package com.nju.edu.erp.model.vo.sale;

import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.vo.SheetVO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleSheetVO extends SheetVO {

    /**
     * 销售单单据编号（格式为：XSD-yyyyMMdd-xxxxx), 新建单据时前端传null
     * 在父类SheetVO中
     */
    //private String id;
    /**
     * 客户/销售商id
     */
    private Integer supplier;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 折让前总额
     */
    private BigDecimal rawTotalAmount;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private SaleSheetState state;
    /**
     * 折让后总额, 新建单据时前端传null
     */
    private BigDecimal finalAmount;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 创建时间，前端传null
     * 在父类SheetVO中
     */
     //private Date createTime;
    /**
     * 销售单具体内容
     */
    List<SaleSheetContentVO> saleSheetContent;
}
