package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.FeedbackMapper;
import com.harvey.se.pojo.dto.FeedbackDto;
import com.harvey.se.pojo.entity.Feedback;
import com.harvey.se.pojo.vo.DateRange;
import com.harvey.se.service.FeedbackService;
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
        LambdaQueryChainWrapper<Feedback> wrapper = new LambdaQueryChainWrapper<>(this.baseMapper).eq(
                Feedback::getRead,
                read
        );
        if (dateRange.isForward()) {
            wrapper.ge(dateRange.getFrom() != null, Feedback::getCreateTime, dateRange.getFrom())
                    .le(dateRange.getTo() != null, Feedback::getCreateTime, dateRange.getTo())
                    .orderByAsc(Feedback::getCreateTime, (SFunction<Feedback, ?>[]) null);
            ;
        } else {
            wrapper.ge(dateRange.getTo() != null, Feedback::getCreateTime, dateRange.getTo())
                    .le(dateRange.getFrom() != null, Feedback::getCreateTime, dateRange.getFrom())
                    .orderByDesc(Feedback::getCreateTime, (SFunction<Feedback, ?>[]) null);
        }
        return wrapper.page(page).getRecords().stream().map(FeedbackDto::new).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> queryFeedback(Long userId, Page<Feedback> page, boolean read) {
        return new LambdaQueryChainWrapper<>(this.baseMapper)
                .eq(Feedback::getUserId, userId)
                .eq(Feedback::getRead, read)
                .page(page)
                .getRecords()
                .stream()
                .map(FeedbackDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void read(Long id) {
        LambdaUpdateWrapper<Feedback> updateWrapper = new LambdaUpdateWrapper<Feedback>().set(
                        Feedback::getRead, true) // 已读
                .eq(Feedback::getId, id);
        boolean updated = super.update(updateWrapper);
        if (!updated) {
            log.warn("未成功更新read");
        }
    }
}