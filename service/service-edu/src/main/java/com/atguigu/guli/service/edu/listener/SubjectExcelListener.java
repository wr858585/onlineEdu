package com.atguigu.guli.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.guli.service.edu.entity.Subject;
import com.atguigu.guli.service.edu.entity.excel.SubjectExcelData;
import com.atguigu.guli.service.edu.mapper.SubjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;

import java.util.List;
import java.util.Map;

@Slf4j
public class SubjectExcelListener extends AnalysisEventListener<SubjectExcelData> {

    //自动装配@Autowired是需要从容器中获取，再DI注入的，这个类我们没有放入容器
    //可以考虑用一个构造器，用构造器来初始化
    private SubjectMapper subjectMapper;
    public SubjectExcelListener(SubjectMapper subjectMapper){
        this.subjectMapper = subjectMapper;
    }

    //上传的课程分类需要转为数据库表对应的数据，存入数据库
    @Override
    public void invoke(SubjectExcelData subjectExcelData, AnalysisContext analysisContext) {
        String levelOneTitle = subjectExcelData.getLevelOneTitle();
        String levelTwoTitle = subjectExcelData.getLevelTwoTitle();
        //验证一级分类是否第一次存入数据库（一级分类的pid=0）
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title" , levelOneTitle);
        queryWrapper.eq("parent_id" , 0);
        Subject levelOneSubject = subjectMapper.selectOne(queryWrapper);
        if(levelOneSubject == null){
            //将一级分类标题转为一级课程分类对象存入数据库
            //不存在，需要保存
            levelOneSubject = new Subject();
            levelOneSubject.setParentId(0+"");
            levelOneSubject.setTitle(levelOneTitle);
            subjectMapper.insert(levelOneSubject);//默认会返回新增数据的id设置给当前javabean
        }
        String levelOneSubjectId = levelOneSubject.getId();
        //验证2级分类是否为第一次存入数据库
        queryWrapper.clear();
        queryWrapper.eq("title" , levelTwoTitle);
        queryWrapper.eq("parent_id" , levelOneSubjectId);
        Subject levelTwoSubject = subjectMapper.selectOne(queryWrapper);
        if(levelTwoSubject==null){
            //将2级分类标题转为2级课程分类对象存入数据库
            levelTwoSubject = new Subject();
            levelTwoSubject.setTitle(levelTwoTitle);
            levelTwoSubject.setParentId(levelOneSubjectId);
            subjectMapper.insert(levelTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
