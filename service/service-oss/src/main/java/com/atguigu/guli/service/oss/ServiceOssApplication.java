package com.atguigu.guli.service.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//因为service-oss依赖了service，service依赖了service-base，所以oss相当于import了base中所有的依赖
//注意：dependency就相当于import，是直接导入依赖；dependencyManagement不是直接依赖，是父模块给出的版本管理
//      只有当子模块显示的声明依赖dependencyManagement中的依赖且没有指明版本号时，才会import其中的依赖
//所以，右侧maven中可以查看到，oss实际依赖了base中的mysql-connector
//而springboot的一个大好处就是自动配置，因为oss依赖了数据库连接，所以会自动去yml中找配置信息自动配置
//找不到-->报错：
//Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
//
//Reason: Failed to determine a suitable driver class

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.atguigu.guli")
public class ServiceOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class,args);
    }
}
