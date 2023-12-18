package com.nju.edu.erp.model.vo.saleReturns;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
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
public class SaleReturnsSheetVO extends SheetVO {
    /**
     * 销售退货单单据编号（格式为：XSTHD-yyyyMMdd-xxxxx), 新建单据时前端传null
     * 在父类SheetVO中
     */
    //private String id;
    /**
     * 关联的销售单id
     */
    private String saleSheetId;
    /**
     * 客户/销售商id，前端传null
     */
    private Integer supplier;
    /**
     * 业务员，前端传null
     */
    private String salesman;
    /**
     * 操作员，前端传null
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private SaleReturnsSheetState state;
    /**
     * 退回总额,在退回全部商品时，与销售单中的折后总额对应，新建单据时前端传null
     */
    private BigDecimal returnsAmount;
    /**
     * 创建时间，前端传null
     * 在父类SheetVO中
     */
    //private Date createTime;
    /**
     * 销售退货单具体内容
     */
    List<SaleReturnsSheetContentVO> saleReturnsSheetContent;
}
