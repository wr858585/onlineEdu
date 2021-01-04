package com.atguigu.guli.service.edu.mapper;

import com.atguigu.guli.service.edu.entity.Chapter;
import com.atguigu.guli.service.edu.entity.vo.ChapterVo;
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
public interface ChapterMapper extends BaseMapper<Chapter> {

    List<ChapterVo> selectChapterNestedList(String courseId);

    //QueryWrapper不要加泛型，mp源码中用getCustomSqlSegment中有注释要求的
    List<ChapterVo> getChapterNestedList(@Param("ew") QueryWrapper queryWrapper);

//    ChapterVo getChapterVoById(String chapterId);
}
