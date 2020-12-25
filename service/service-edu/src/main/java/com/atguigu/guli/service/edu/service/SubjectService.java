package com.atguigu.guli.service.edu.service;

import com.atguigu.guli.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
public interface SubjectService extends IService<Subject> {

    public void batchImportSubjects(InputStream inputStream);

}
