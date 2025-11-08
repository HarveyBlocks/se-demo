package com.harvey.se.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redisson锁
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-01-21 21:20
 */
@Component
@Slf4j
public class RedissonLock<T> {
    @Resource
    private RedissonClient redissonClient;

    @Getter
    private T errorResult = null;

    public void setErrorResult(T errorResult) {
        this.errorResult = errorResult;
    }

    /**
     * 分布式锁
     *
     * @param lockKey lock的键
     * @param supply  获取值的方法
     * @return id
     */
    public T asynchronousLock(String lockKey, Supplier<T> supply) throws InterruptedException {
        log.debug("will lock : {}", lockKey);
        // 获取锁(可重入)
        RLock lock = redissonClient.getLock(lockKey);
        // 尝试获取锁, 参数分别为: 获取锁的最大等待时间(期间会重试),锁自动释放时间, 时间单位
        long waitTime = -1L;// 等待时间, 默认-1不等待
        long releaseTime = 30L;// 自动释放时间,默认30s
        boolean isLock = lock.tryLock(waitTime, releaseTime, TimeUnit.SECONDS);
        if (isLock) {
            try {
                return supply.get();
            } finally {
                lock.unlock();
            }
        }
        return errorResult;
    }

}
