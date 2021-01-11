package com.atguigu.guli.service.edu.feign;

import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.edu.feign.fallback.OssFeignClientFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//ribbon和feign都是使用网络地址发起的请求：http://localhost:8120/admin/oss/file/deleteFile?filePath=xxx&module=xxx
//绑定接口和要远程访问的微服务
//http://localhost:8120不能写死，用微服务在注册中心的服务名代替
@FeignClient(value = "service-oss", fallback = OssFeignClientFallback.class) //去nacos页面中service-list中去找
@Component
public interface OssFeignClient {

    //远程测试edu调用oss
    @GetMapping("/admin/oss/file/test")
    public R testFeignToOss(@RequestParam String path);

    //OpenFeign使用接口实现远程调用，接口的写法：拷贝要访问的服务（这里是oss）的接口的方法+映射路径
    @DeleteMapping("/admin/oss/file/delete")   //且修改地址为需要访问的地址，拼接好controller的mapping
    public R deleteFile(@RequestParam String filePath, @RequestParam String module);
    //远程调用时，传参一定要加注解@RequestParam或者@RequestBody！

}
