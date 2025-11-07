package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.UserActionLogMapper;
import com.harvey.se.pojo.entity.UserActionLog;
import com.harvey.se.service.UserActionLogService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:56
 * @see UserActionLog
 * @see UserActionLogMapper
 * @see UserActionLogService
 */
@Service
public class UserActionLogServiceImpl extends ServiceImpl<UserActionLogMapper, UserActionLog> implements
        UserActionLogService {

}