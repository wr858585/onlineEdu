package com.atguigu.guli.service.edu.controller.api;


import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.edu.entity.Teacher;
import com.atguigu.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/api/edu/teacher")
@CrossOrigin
@Api("讲师")
public class ApiTeacherController {

    @Autowired
    TeacherService teacherService;

    //6. 根据id查询讲师
    @ApiOperation(value = "查询指定讲师")
    @GetMapping("get/{id}")
    public R getTeacherById(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item",teacher);
    }

    //1. 查询所有的讲师
    @GetMapping("/list")
    public R listAll(){
        List<Teacher> teachers = teacherService.list();
        return R.ok().data("items",teachers).message("获取讲师列表成功");
    }
}

