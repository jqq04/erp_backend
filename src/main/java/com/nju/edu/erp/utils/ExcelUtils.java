package com.nju.edu.erp.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.nju.edu.erp.model.vo.business.SaleDetailExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {
    /**
     *  导出Excel
     * @param response 返回对象
     * @param list  Excel表中的记录
     */
    public static void writeExcel(HttpServletResponse response, List<SaleDetailExcel> list) throws IOException {

        ExcelWriter excelWriter= EasyExcel.write(response.getOutputStream()).build();
//        定义工作表对象
        WriteSheet sheet=EasyExcel.writerSheet(0,"sheet").head(SaleDetailExcel.class).build();
//        向Excel文件中写入数据
        excelWriter.write(list,sheet);
//        关闭输出流
        excelWriter.finish();
    }


}