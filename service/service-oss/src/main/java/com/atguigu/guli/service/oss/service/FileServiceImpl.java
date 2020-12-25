package com.atguigu.guli.service.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.guli.service.base.exception.GuliException;
import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
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
            String path = scheme+bucketName+"."+endpoint +"/"+fileName;
//            return R.ok().data("path",path);
            return path;
        } catch (Exception e) {
            //编译时异常：必须处理否则编译不通过；
            // 注意，异常改大一点，如果IOException可能捕获不到其他上传文件可能出现的异常
            // 如OssException，则不进入catch没被捕获，最后仍被base中的GlobalExceptionHandler处理了
            e.printStackTrace();
            //将编译时异常转为运行时异常，不用强制处理
//            throw new RuntimeException("文件上传失败");
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public void deleteFile(String filePath, String module) {
        int index = filePath.indexOf("/"+module);
        //文件名=文件路径+文件原名
        String objectName = filePath.substring(index+1);
        //创建ossClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
        //删除文件，如需要删除文件夹，将ObjectName设置为对应的文件夹名称
        //如果文件夹名称不是空，则需要将文件夹下的;
        ossClient.deleteObject(bucketName,objectName);
        //关闭ossClient
        ossClient.shutdown();
    }
}
