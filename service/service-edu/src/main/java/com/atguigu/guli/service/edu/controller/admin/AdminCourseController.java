package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.edu.entity.Course;
import com.atguigu.guli.service.edu.entity.form.CourseInfoForm;
import com.atguigu.guli.service.edu.entity.vo.CourseInfoVo;
import com.atguigu.guli.service.edu.entity.vo.CoursePublishVo;
import com.atguigu.guli.service.edu.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/edu/course")
public class AdminCourseController {

    @Autowired
    CourseService courseService;

    //5. 发布课程
    @PutMapping("/publish-course/{courseId}")
    public R publishCourse(@PathVariable String courseId){
//        Course course = courseService.getById(courseId);
//        course.setStatus("Published");
//        courseService.updateById(course);
        /**
         * 也可以使用updateWrapper
         * updateWrapper=queryWrapper（设置where字段）+设置set字段
         * update table set k=v, k=v ... where k=v
         */
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("status","published");
        updateWrapper.set("publish_time", new Date());
        updateWrapper.eq("id",courseId);
        boolean b = courseService.update(updateWrapper);
        if(b){
            return R.ok().message("课程发布成功");
        }else{
            return R.error().message("数据不存在，课程发布失败");
        }
    }

    //4. 查询课程发布信息
    @GetMapping("/get-coursevo/{courseId}")
    public R getCourseVo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.getCourseVoById(courseId);
        return R.ok().data("item",coursePublishVo);
    }

    //3. 更新课程基本信息
    @PutMapping("/update-course-info/{courseId}")
    public R updateCourseInfo(@PathVariable String courseId ,@RequestBody CourseInfoForm courseInfoForm){
        courseService.updateCourseInfo(courseId,courseInfoForm);
        return R.ok();
    }

    //2. 查询课程基本信息
    @GetMapping("/get-course-info/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("item", courseInfoVo);
    }

    //1. 课程发布第一步：保存课程的基本信息
    @PostMapping("/save-course-info")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        //这里要把service层的saveCourseInfo方法设计成返回courseId，方便第二步和第三步使用这个courseId来获取需要的数据
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }

}

