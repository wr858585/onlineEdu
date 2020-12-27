package com.atguigu.guli.service.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(value = "com.atguigu.guli")
@EnableDiscoveryClient
public class ServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class,args);
    }
}
