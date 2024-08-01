package com.pray.utils.cache;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RedisPreProcess
 *
 * @author 春江花朝秋月夜
 * @since 2024/7/12 15:21
 */
@Component
@Slf4j
public class RedisPreProcess implements InitializingBean {
    //缓存预热处理，通过预先调查用户热数据，先自查询一次，写入缓存中，避免冷启动每次查询都走DB给数据库带来巨大压力
    @Resource
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("使用缓存工具"+redisTemplate.toString()+"执行缓存预热");
    }
}
