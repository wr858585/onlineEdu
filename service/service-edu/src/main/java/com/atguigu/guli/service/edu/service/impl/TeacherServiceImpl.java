package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.Teacher;
import com.atguigu.guli.service.edu.entity.query.TeacherQuery;
import com.atguigu.guli.service.edu.mapper.TeacherMapper;
import com.atguigu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Page<Teacher> selectTeacherPageByCondition(Integer pageNum, Integer pageSize, TeacherQuery teacherQuery) {
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        if(teacherQuery == null){
            //没有查询条件，则将所有的查询结果用分页返回
            //baseMapper代表当前service对应的Mapper，而非baseMapper
            return baseMapper.selectPage(page,null);
        }
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();
        String joinDateBegin = teacherQuery.getJoinDateBegin();
        String joinDateEnd = teacherQuery.getJoinDateEnd();
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            //name条件不为空，则TODO
            wrapper.likeRight("name",name);
        }
        //sql中如何判断时间日期
        //如果TODO
        if(!StringUtils.isEmpty(joinDateBegin)){
            wrapper.ge("join_date",joinDateBegin);
        }
        if(!StringUtils.isEmpty(joinDateEnd)){
            wrapper.le("join_date", joinDateEnd);
        }
        return baseMapper.selectPage(page, wrapper);
    }
}
