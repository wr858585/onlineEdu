package com.atguigu.guli.service.base.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;

/**
 *  * 我们自定义一个 RedisTemplate，设置序列化器，这样我们可以很方便的操作实例对象。
 *  * 否则redis自动使用对象的jdk序列化
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * 配置了cacheManager后，就可以在方法上加@Cacheable注解了
     * @Cacheable(value = "xxx", key = "'xxx'")：
     * 标注在方法上，对方法返回结果进行缓存。下次请求时，如果缓存存在，则直接读取缓存数据返回；
     * 如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
     * 如：@Cacheable(value = "index", key = "'selectByAdTypeId'")
     *       @Override
     *       public List<Ad> selectByAdTypeId(String adTypeId) {
     * @param connectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory) {

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //过期时间600秒
                .entryTtl(Duration.ofSeconds(600))
                // 配置序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory){
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化方式
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());//value序列化
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

}
