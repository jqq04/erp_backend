package com.nju.edu.erp.model.po.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPO {
    /**
     * 套账编号，"TZ-yyyyMMdd-xxxxx'
     */
    private String id;
    /**
     * 套账创建时间，同时也表示这套账所记录的时间点 yyyy-MM-dd HH:mm:ss
     */
    private Date createTime;
}
