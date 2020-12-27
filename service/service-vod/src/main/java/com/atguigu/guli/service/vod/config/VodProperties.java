package com.atguigu.guli.service.vod.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.vod")
public class VodProperties {

    private String accessKeyId;//LTAI4G9QdS4zyA8HAjAPQ8DM
    private String accessKeySecret;//jrttTMAPrQhwdoMpA8yAmI6FPBLf46
//    workFlowId: ####TODO

}
