package com.atguigu.guli.service.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.oss.config.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    OssProperties ossProperties;

    String scheme;
    String endpoint;
    String keyId;
    String keySecret;
    String bucketName;

    //jdk提供的初始化方法，当前javabean初始化后立即执行此方法！
    @PostConstruct
    public void init(){
        scheme=ossProperties.getScheme();
        endpoint=ossProperties.getEndpoint();
        keyId=ossProperties.getAccessKeyId();
        keySecret=ossProperties.getAccessKeySecret();
        bucketName=ossProperties.getBucketName();
    }

    @Override
    public String uploadFile(MultipartFile file, String module) {
        //1. 创建OssClient对象
        OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, keyId, keySecret);
        //2. 上传文件流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //一般需要自己生成文件上传到oss的文件名称，以保证文件名是唯一的
            String fileName = file.getOriginalFilename();
            //截取文件名的后缀
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID().toString().replace("-","") + suffix;
            String dirs = new DateTime().toString("/yyyy/MM/dd");  //生成年月日的字符串，用来保存图片的路径
            fileName = module + "/" + fileName; //将文件保存到指定的模块中
            //用ossClient上传文件
            ossClient.putObject(bucketName,fileName,inputStream);
            ossClient.shutdown();
            //最后，上传文件成功后，应该获取图片的地址，存放在数据库中
            //通过分析aliyun oss中的图片url，发现是：协议+桶名+.+endpoint+/+文件路径+文件名
            String path = scheme+bucketName+"."+endpoint +dirs+fileName;
//            return R.ok().data("path",path);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        }
    }
}
