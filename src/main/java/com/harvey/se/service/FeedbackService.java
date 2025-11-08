package com.harvey.se.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harvey.se.pojo.dto.FeedbackDto;
import com.harvey.se.pojo.entity.Feedback;
import com.harvey.se.pojo.vo.DateRange;

import java.util.List;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:51
 */
public interface FeedbackService extends IService<Feedback> {
    List<FeedbackDto> queryFeedback(DateRange dateRange, Page<Feedback> page, boolean read);

    List<FeedbackDto> queryFeedback(Long userId, Page<Feedback> page, Boolean read);

    void read(Long id);

    void saveNew(Feedback feedback);
}
