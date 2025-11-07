package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.FeedbackMapper;
import com.harvey.se.pojo.entity.Feedback;
import com.harvey.se.service.FeedbackService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:55
 * @see Feedback
 * @see FeedbackMapper
 * @see FeedbackService
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

}