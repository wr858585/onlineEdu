package com.atguigu.guli.service.oss.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
import com.atguigu.guli.service.oss.config.OssProperties;
import com.atguigu.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "文件管理模块")
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    OssProperties ossProperties;

    @Autowired
    FileService fileService;

    //3. 测试edu远程调用oss服务
    @GetMapping("test")
    @ApiOperation("测试feign远程调用")
    public R testFeignToOss(String path){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("edu服务远程调用了oss服务");
        return R.ok().message("成功调用了oss");
    }

    //1. 上传文件
    //参数1：上传文件表单对象
    //参数2：文件保存的模块说明
    @PostMapping("upload")
    @ApiOperation("文件上传功能")
    //注意：module参数是对oss管理有帮助的，会将上传的图片通过service层的逻辑放到oss中指定的层级目录，所以规定需要传
    public R uploadFile(MultipartFile file, String module){
        String path = fileService.uploadFile(file,module);
        return R.ok().data("path",path);
    }
    //2. 根据文件的上传地址删除文件
    //intuition：删除讲师时，把其头像也删掉
    @ApiOperation("文件删除功能")
    @DeleteMapping("/delete")
    public R deleteFile(@RequestParam String filePath, @RequestParam String module){
        fileService.deleteFile(filePath,module);
        return R.ok();
    }
}
