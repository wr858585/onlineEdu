package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.service.base.exception.GuliException;
import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
import com.atguigu.guli.service.edu.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/admin/edu/subject")
@CrossOrigin
@Slf4j
public class AdminSubjectController {

    @Autowired
    SubjectService subjectService;

    //1. 处理课程分类文件上传，保存课程分类到数据库的请求
    @PostMapping("import")
    //注意：MultipartFile作为参数时，参数名必须为file（vue写死），如果写其他的，需要额外去指定name属性，这里演示下
    public R batchImportSubjects(@RequestParam("file") MultipartFile subjectFile){
        //解析文件上传中的课程分类存到数据库
        //1. 引入easyexcel依赖
        //2. 编写文件对应的javaBean
        //3. 编写监听器，每读一行回调它一次（核心业务都写在里面）(EasyExcel读取文件需要这个监听器，作为参数传入）
        //4. 使用监听器，对文件进行解析
        try {
            InputStream inputStream = subjectFile.getInputStream();
            subjectService.batchImportSubjects(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

}

