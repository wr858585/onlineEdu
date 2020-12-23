package com.atguigu.guli.service.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 这个配置类，可以把oss参数从yml中封装到一个bean中，之后可以用@Autowired注入即可使用，不用@Value一个一个来了
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    private String scheme;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     *     scheme: http://
     *     endpoint: oss-cn-heyuan.aliyuncs.com
     *     accessKeyId: LTAI4G9QdS4zyA8HAjAPQ8DM
     *     acceessKeySecret: jrttTMAPrQhwdoMpA8yAmI6FPBLf46
     *     bucketName: sh0821-onlineedu
     */


}
