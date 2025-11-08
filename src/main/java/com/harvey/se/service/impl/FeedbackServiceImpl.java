package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.FeedbackMapper;
import com.harvey.se.pojo.dto.FeedbackDto;
import com.harvey.se.pojo.entity.Feedback;
import com.harvey.se.pojo.vo.DateRange;
import com.harvey.se.service.FeedbackService;
import com.harvey.se.service.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public List<FeedbackDto> queryFeedback(DateRange dateRange, Page<Feedback> page, boolean read) {
        return ServiceUtil.queryAndOrderWithDate(new LambdaQueryChainWrapper<>(this.baseMapper).eq(
                        Feedback::getHasRead,
                        read
                ), Feedback::getCreateTime, dateRange, page)
                .stream()
                .map(FeedbackDto::adapte)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> queryFeedback(Long userId, Page<Feedback> page, Boolean read) {
        return new LambdaQueryChainWrapper<>(this.baseMapper).eq(Feedback::getUserId, userId)
                .eq(read != null, Feedback::getHasRead, read)
                .page(page)
                .getRecords()
                .stream()
                .map(FeedbackDto::adapte)
                .collect(Collectors.toList());
    }

    @Override
    public void read(Long id) {
        boolean updated = new LambdaUpdateChainWrapper<>(baseMapper).set(Feedback::getHasRead, true) // 已读
                .eq(Feedback::getId, id).update();
        if (!updated) {
            log.warn("未成功更新read");
        }
    }

    @Override
    public void saveNew(Feedback feedback) {
        feedback.setId(null);
        feedback.setHasRead(false);
        boolean saved = super.save(feedback);
        if (!saved) {
            log.warn("保存反馈信息 {} 失败" + feedback);
        }
    }
}