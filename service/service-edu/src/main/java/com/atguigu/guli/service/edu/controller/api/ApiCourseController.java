package com.atguigu.guli.service.edu.controller.api;


import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.edu.entity.vo.CourseApiVo;
import com.atguigu.guli.service.edu.entity.vo.CourseInfoApiVo;
import com.atguigu.guli.service.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@CrossOrigin
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {

    @Autowired
    CourseService courseService;

    //2. 根据课程id查询课程详情页
    @GetMapping("getCourse/{id}")
    public R getCourseInfoApiVo(@PathVariable String id){
        CourseInfoApiVo courseInfoApiVo = courseService.getCourseInfoApiVo(id);
        return R.ok().data("item", courseInfoApiVo);
    }

    //1. 查询所有课程
    @GetMapping("list")
    public R getAllCourses(){
        List<CourseApiVo> courseApiVos = courseService.getCourses();
        return R.ok().data("items", courseApiVos);
    }

}

