package com.atguigu.guli.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.atguigu.guli.service.edu.entity.Subject;
import com.atguigu.guli.service.edu.entity.excel.SubjectExcelData;
import com.atguigu.guli.service.edu.entity.vo.SubjectVo;
import com.atguigu.guli.service.edu.listener.SubjectExcelListener;
import com.atguigu.guli.service.edu.mapper.SubjectMapper;
import com.atguigu.guli.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@Service
@Transactional
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void batchImportSubjects(InputStream inputStream) {
        EasyExcel.read(inputStream, SubjectExcelData.class, new SubjectExcelListener(baseMapper)
        ).excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }

    @Override
    public List<SubjectVo> nestedList() {
        return baseMapper.selectNestedList();
    }
}
