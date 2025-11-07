package com.harvey.se.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.harvey.se.pojo.dto.FeedbackDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * TODO 咨询内容
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`tb_feedback`")
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("text")
    private String text;

    @TableField("create_time")
    private Date createTime;

    @TableField("read")
    private Boolean read;

    public Feedback(FeedbackDto dto) {
        this(
                dto.getId(),
                dto.getUserId(),
                dto.getText(),
                dto.getCreateTime(),
                dto.getRead()
        );
    }
}
