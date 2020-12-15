package com.atguigu.guli.service.base.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring中将javabean注入到容器中的方式：
 * 1. 在spring.xml中通过bean标签配置
 * 2. 在类名上使用组件注解
 * 3. 在组件类的方法上标注@Bean注解，可以将方法的返回值对象注入到容器中
 */

@Configuration
//指定扫描mapper接口的基本包
@MapperScan(basePackages = {"com.atguigu.guli.service.*.mapper"})
public class MybatisPlusConfig {

    //乐观锁拦截器
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    //分页拦截器
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}
