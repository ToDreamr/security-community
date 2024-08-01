package com.pray.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedissonConfig
 *
 * @author 春江花朝秋月夜
 * @since 2024/5/26 20:52
 */
@Configuration
public class RedissonConfig {
    //配置Redisson客户端
    @Bean
    public RedissonClient redissonClient(){
        //实例化配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);//创建RedissonClient对象
    }
}
