package com.harvey.se.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`tb_user_action_log`")
@AllArgsConstructor
@NoArgsConstructor
public class UserActionLog {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("ip_address")
    private String ipAddress;

    @TableField("request_url")
    private String requestUrl;

    @TableField("request_method")
    private String requestMethod;

    @TableField("request_time")
    private Date requestTime;

    @TableField("request_time_cost")
    private Integer requestTimeCost;
}
