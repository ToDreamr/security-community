package com.pray.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * IdWorker
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/13 16:43
 */
@Component
public class UniqueIDGenerator {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    private static final long BEGIN_TIMESTAMP = 1694709840L;
    private static final int COUNT_BITS = 32;// 向左移动32位

    public long nextId(String keyPrefix) {
        LocalDateTime now = LocalDateTime.now();
        long nowSec = now.toEpochSecond(ZoneOffset.UTC);
        long timeGap = nowSec - BEGIN_TIMESTAMP;

        String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        long count = redisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + today);// 不同天采用不同的key

        // 通过和自增的数据进行或运算
        return ~(timeGap << COUNT_BITS | count);
    }
}
