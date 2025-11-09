package com.harvey.se.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.harvey.se.properties.ChatProperties;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.util.JacksonUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 对上下文的设置
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-01-21 21:13
 */
@Configuration
@EnableConfigurationProperties({ConstantsProperties.class, ChatProperties.class})
public class ApplicationConfig {
    @Resource
    private ConstantsProperties constantsProperties;

    @Bean
    public RedissonClient redissonClient() {
        // 配置类
        Config config = new Config();
        // 添加Redis地址, 这里添加了单点的地址
        config.useSingleServer()
                .setAddress("redis://" + constantsProperties.getRedisHost() + ":6379")
                .setPassword(constantsProperties.getRedisPassword());
        // 也可以使用config.useClusterServers()添加集群地址
        return Redisson.create(config);
    }

    @Bean
    public JacksonUtil jacksonUtil() {
        return new JacksonUtil(new ObjectMapper());
    }

}
