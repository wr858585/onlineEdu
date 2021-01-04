package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.Chapter;
import com.atguigu.guli.service.edu.entity.vo.ChapterVo;
import com.atguigu.guli.service.edu.mapper.ChapterMapper;
import com.atguigu.guli.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Override
    public List<ChapterVo> chapterNestedList(String courseId) {
        //为了方法传入条件的扩展性，也可以使用mp提供的queryWrapper生成查询语句的条件
        //QueryWrapper不要加泛型，mp源码中用getCustomSqlSegment中有注释要求的
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c1.course_id",courseId);
        //添加排序规则，显示查询结果
        queryWrapper.orderByAsc("c1.sort","c1.id");     //先按照c1.sort排序，一样时按照c1.id排序
        //发现：想要什么条件，只用动态的在这里添加即可了，不用去改sql，非常便利
        return baseMapper.getChapterNestedList(queryWrapper);
        //intuition：把传入的形参弄成queryWrapper enabling users to customise their query needs
        //而下面就只能传courseId，写死了能用的查询条件
//        return baseMapper.selectChapterNestedList(courseId);
    }

//    @Override
//    public ChapterVo getChapterVoById(String chapterId) {
//        return baseMapper.getChapterVoById(chapterId);
//    }
}
