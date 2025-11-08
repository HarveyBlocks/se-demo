package com.harvey.se.pojo.dto;

import com.harvey.se.exception.ResourceNotFountException;
import com.harvey.se.pojo.entity.Feedback;
import com.harvey.se.util.ServerConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户回馈信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 01:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户的反馈信息")
public class FeedbackDto {
    @ApiModelProperty(value = "主键, 插入时系统自动分配")
    private Long id;

    @ApiModelProperty(value = "持有这个咨询信息的用户, 系统自动分配")
    private Long userId;

    @ApiModelProperty(value = "文本", required = true)
    private String text;

    @ApiModelProperty(value = "请求日期, 系统自己决定", example = ServerConstants.DATE_TIME_FORMAT_STRING)
    private Date createTime;

    @ApiModelProperty(value = "是否已读")
    private Boolean read;

    public static FeedbackDto adapte(Feedback entity) {
        if (entity == null) {
            throw new ResourceNotFountException("请求不存在的资源");
        }
        return new FeedbackDto(
                entity.getId(),
                entity.getUserId(),
                entity.getText(),
                entity.getCreateTime(),
                entity.getHasRead()
        );
    }
}
