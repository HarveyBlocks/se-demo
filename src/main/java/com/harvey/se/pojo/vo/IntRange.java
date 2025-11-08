package com.harvey.se.pojo.vo;

import lombok.Data;

/**
 * 整型区间
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 15:41
 */
@Data
public class IntRange {
    /**
     * null for 不设限制
     */
    private final Integer lower;
    /**
     * null for 不设限制
     */
    private final Integer upper;
    /**
     * true if {@link #upper} is greater
     */
    private final boolean forward;

    public IntRange(Integer lower, Integer upper) {
        if (lower != null && upper != null && lower < upper) {
            this.lower = upper;
            this.upper = lower;
            this.forward = false;
        } else {
            this.lower = lower;
            this.upper = upper;
            this.forward = true;
        }
    }
}
