package com.harvey.se.service.impl;

import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.service.PointService;
import com.harvey.se.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 对积分的一些操作
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:56
 */
@Service
public class PointServiceImpl implements PointService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserService userService;

    @Override
    public void add(String keyPre, UserDto user, int point, int timeout, TimeUnit unit) {
        // 1. 检查缓存, 是否已经加过分
        String flagKey = keyPre + user.getId();
        boolean hasKeys = Boolean.TRUE.equals(stringRedisTemplate.hasKey(flagKey));
        if (hasKeys) {
            return;
        }
        // 2. 增加缓存标记
        stringRedisTemplate.opsForValue().set(flagKey, flagKey, timeout);
        // 3. 增加积分
        userService.increasePoint(user.getId(), user.getPoints(), point);
    }
}
