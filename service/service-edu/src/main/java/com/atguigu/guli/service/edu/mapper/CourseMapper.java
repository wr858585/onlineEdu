package com.atguigu.guli.service.edu.mapper;

import com.atguigu.guli.service.edu.entity.Course;
import com.atguigu.guli.service.edu.entity.vo.CourseApiVo;
import com.atguigu.guli.service.edu.entity.vo.CourseInfoApiVo;
import com.atguigu.guli.service.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCourseVoById(@Param("ew") QueryWrapper<Object> queryWrapper);

    List<CourseApiVo> getCourseApiVos();

    CourseInfoApiVo getCourseInfoApiVoByCourseId(String id);
}
