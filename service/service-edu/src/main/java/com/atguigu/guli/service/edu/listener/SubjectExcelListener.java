package com.atguigu.guli.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.guli.service.edu.entity.Subject;
import com.atguigu.guli.service.edu.entity.excel.SubjectExcelData;
import com.atguigu.guli.service.edu.mapper.SubjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
        //先验证一级分类是否已经存入数据库，避免重复写入
        Map map = new HashedMap<>();
        map.put("title",levelOneTitle);
        map.put("parent_id",0);
        List<Subject> list = subjectMapper.selectByMap(map);
        Subject levelOneSubject = null;
        //将一级分类标题转为一级课程分类对象存入数据库
        if(list.size() == 0){
            //不存在，需要保存这个一级分类
            list.add(new Subject().setParentId("0").setTitle(levelOneTitle));
            levelOneSubject = list.get(0);
            subjectMapper.insert(levelOneSubject);  //默认会返回新增数据的id设置给当前的javabean
        }
        levelOneSubject = list.get(0);
        String levelOneSubjectId = levelOneSubject.getId();

        //先验证二级分类是否已经存入数据库，避免重复写入
        map.clear();
        map.put("title",levelTwoTitle);
        list = subjectMapper.selectByMap(map);
        Subject levelTwoSubject = null;
        if(levelTwoSubject == null){
            levelTwoSubject = new Subject().setParentId(levelOneSubjectId).setTitle(levelTwoTitle);
        }
        //将二级分类标题转为二级课程分类对象存入数据库
        subjectMapper.insert(levelTwoSubject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
