package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.Course;
import com.atguigu.guli.service.edu.entity.CourseDescription;
import com.atguigu.guli.service.edu.entity.form.CourseInfoForm;
import com.atguigu.guli.service.edu.entity.vo.CourseApiVo;
import com.atguigu.guli.service.edu.entity.vo.CourseInfoApiVo;
import com.atguigu.guli.service.edu.entity.vo.CourseInfoVo;
import com.atguigu.guli.service.edu.entity.vo.CoursePublishVo;
import com.atguigu.guli.service.edu.mapper.CourseDescriptionMapper;
import com.atguigu.guli.service.edu.mapper.CourseMapper;
import com.atguigu.guli.service.edu.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@Service
@Transactional
//指定当前类的所有方法支持事务，但是必须启用声明式事务（可以在service-base中config类中添加@EnableTransactionManagement)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    CourseDescriptionMapper courseDescriptionMapper;

    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //将form对象转为course表和courseDescription表的对象存入
        //保存course表时雪花算法生成的id，也可以作为courseDescription表的id，因为两个表格是一对一的关系
        //所以需要在courseDescription的javabean中显示声明主键策略为none
        //否则会默认也是雪花算法生成主键id，因为这些mp生成的javabean都继承了baseEntity
        Course course = new Course();
        //通过BeanUtils工具类可以将数据源对象的数据（courseInfoForm）拷贝给目标对象（course）
        //使用前提：它们的属性名+数据类型必须一样
        BeanUtils.copyProperties(courseInfoForm,course);
        //course对象的默认值的初始化
        course.setBuyCount(0L);
        course.setViewCount(0L);
        course.setStatus("draft");
        baseMapper.insert(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        //这里类声明时就已经把BaseMapper的泛型写成了CourseMapper，所以这里要另外注入一个CourseDescriptionMapper
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseDescription courseDescription = courseDescriptionMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(String courseId, CourseInfoForm courseInfoForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm,course);
        course.setId(courseId);

        baseMapper.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseDescription.getDescription());
        courseDescription.setId(courseId);

        courseDescriptionMapper.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCourseVoById(String courseId) {
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c.id",courseId);
        return baseMapper.selectCourseVoById(queryWrapper);
    }

    @Override
    public List<CourseApiVo> getCourses() {
        //查询指定列的值，每一行对应一个map
//        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("id","title","cover","price","view_count","buy_count");
//        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
//        List<CourseApiVo> courseApiVos = new ArrayList<>(maps.size());
        List<CourseApiVo> courseApiVos = baseMapper.getCourseApiVos();
//        for (Map<String, Object> map : maps) {
//            CourseApiVo courseApiVo = new CourseApiVo();
//            BeanUtils.copyProperties(map,courseApiVo);
//            (map, courseApiVo);
//            System.out.println("courseApiVo = " + courseApiVo);
//            courseApiVos.add(courseApiVo);
//        }
        return courseApiVos;
    }

    @Override
    public CourseInfoApiVo getCourseInfoApiVo(String id) {
        return baseMapper.getCourseInfoApiVoByCourseId(id);
    }
}
