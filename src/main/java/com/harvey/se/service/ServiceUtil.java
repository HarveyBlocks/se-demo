package com.harvey.se.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harvey.se.pojo.vo.DateRange;
import com.harvey.se.pojo.vo.IntRange;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * 简化Service的方法, 提高复用率
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 09:03
 */
@Slf4j
public class ServiceUtil {
    private ServiceUtil() {
    }

    public static <T> List<T> queryAndOrderWithDate(
            LambdaQueryChainWrapper<T> wrapper, SFunction<T, Date> rangeColumn, DateRange range, Page<T> page) {
        if (range.isForward()) {
            wrapper.ge(range.getFrom() != null, rangeColumn, range.getFrom())
                    .le(range.getTo() != null, rangeColumn, range.getTo())
                    .orderByAsc(rangeColumn, (SFunction<T, Date>[]) null);
        } else {
            wrapper.ge(range.getTo() != null, rangeColumn, range.getTo())
                    .le(range.getFrom() != null, rangeColumn, range.getFrom())
                    .orderByDesc(rangeColumn, (SFunction<T, Date>[]) null);
        }
        return wrapper.page(page).getRecords();
    }

    public static <T> List<T> queryAndOrderWithInteger(
            LambdaQueryChainWrapper<T> wrapper, SFunction<T, Integer> rangeColumn, IntRange range, Page<T> page) {
        if (range.isForward()) {
            wrapper.ge(range.getLower() != null, rangeColumn, range.getLower())
                    .le(range.getUpper() != null, rangeColumn, range.getUpper())
                    .orderByAsc(rangeColumn, (SFunction<T, Date>[]) null);
        } else {
            wrapper.ge(range.getUpper() != null, rangeColumn, range.getUpper())
                    .le(range.getLower() != null, rangeColumn, range.getLower())
                    .orderByDesc(rangeColumn, (SFunction<T, Date>[]) null);
        }
        return wrapper.page(page).getRecords();
    }
}
