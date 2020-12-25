package com.atguigu.guli.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.guli.service.edu.entity.Subject;
import com.atguigu.guli.service.edu.entity.excel.SubjectExcelData;
import com.atguigu.guli.service.edu.listener.SubjectExcelListener;
import com.atguigu.guli.service.edu.mapper.SubjectMapper;
import com.atguigu.guli.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void batchImportSubjects(InputStream inputStream) {
        EasyExcel.read(inputStream, SubjectExcelData.class, new SubjectExcelListener(baseMapper)
        ).sheet().doRead();
    }
}
