package com.nju.edu.erp.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 单聚类的共同父类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SheetVO {
    /**
     * 单据编号，为所有单据VO所共有的属性，格式：XXD-yyyyMMdd-xxxxx
     */
    protected String id;
    /**
     * 创建时间，为所有单据VO所共有的属性
     */
    protected Date createTime;
}
