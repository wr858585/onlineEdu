package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.service.edu.entity.Course;
import com.atguigu.guli.service.edu.entity.form.CourseInfoForm;
import com.atguigu.guli.service.edu.entity.vo.CourseApiVo;
import com.atguigu.guli.service.edu.entity.vo.CourseInfoApiVo;
import com.atguigu.guli.service.edu.entity.vo.CourseInfoVo;
import com.atguigu.guli.service.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(String courseId, CourseInfoForm courseInfoForm);

    CoursePublishVo getCourseVoById(String courseId);

    List<CourseApiVo> getCourses();

    CourseInfoApiVo getCourseInfoApiVo(String id);
}
