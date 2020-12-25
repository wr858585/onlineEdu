package com.atguigu.guli.service.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectExcelData {

    //这个@ExcelProperty中指定的名称一定要和上传的excel中的列明一模一样才能对上
    @ExcelProperty(value = "一级分类")
    private String levelOneTitle;
    @ExcelProperty(value = "二级分类")
    private String levelTwoTitle;

}
