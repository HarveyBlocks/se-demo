package com.harvey.se.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-09 11:02
 */
@Component
public class RedisIdWorker {
    public static final long BASE_TIMESTAMP = 1762668974L;
    private static final int COUNT_BITS = 32;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * id生成器
     *
     * @param servicePrefix 用于生成Id的分层, 不同的业务使用不同的分层, 需要以:结尾
     * @return id.
     */
    public long nextId(String servicePrefix) {
        // 生成时间戳
        // 当前时间
        LocalDateTime now = LocalDateTimeUtil.now();
        long nowTimestamp = now.toEpochSecond(ZoneOffset.UTC); // s
        // 时间差
        long timestamp = nowTimestamp - BASE_TIMESTAMP;
        // 生成序列号,利用Redis的自增长,由于序列号定义了下2^32,一月一key,还有统计效果
        String icrKey = String.format("%s%d:%02d", servicePrefix, now.getYear() % 100, now.getMonth().getValue());
        // 默认加一, 报黄是因为icr不存在, 但Redis会对不存在的key自动创建并加一(之后为1),所以不必担心
        Long count = stringRedisTemplate.opsForValue().increment(icrKey);
        if (count == null) {
            throw new IllegalStateException("不可能的情况, 没有自动创建key");
        }
        // 拼接
        return timestamp << COUNT_BITS | count; // 使用或运算
    }

}