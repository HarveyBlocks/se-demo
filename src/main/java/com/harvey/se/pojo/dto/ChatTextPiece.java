package com.harvey.se.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天文本碎片
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-09 02:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "聊天的文本碎片")
public class ChatTextPiece {
    public static final Integer SUCCESSFULLY_FINISHED_ID = -1;
    public static final Integer QUESTION_WHILE_GENERATING_ID = -2;
    public static final Integer SERVICE_ERROR_ID = -3;
    @ApiModelProperty("自增的文本, 用于指示文档顺序." +
                      "但id是负数时, 表示特殊含义(-1: 结束;-2: 重复提问;-1: 服务器异常)")
    private Integer id;
    @ApiModelProperty("")
    private String text;

    public static ChatTextPiece ofSuccessfullyFinished(String message) {
        return new ChatTextPiece(SUCCESSFULLY_FINISHED_ID, message);
    }

    public static ChatTextPiece ofQuestionWhileGenerating(String message) {
        return new ChatTextPiece(QUESTION_WHILE_GENERATING_ID, message);
    }

    public static ChatTextPiece ofServiceError(String message) {
        return new ChatTextPiece(SERVICE_ERROR_ID, message);
    }

    public boolean endSign() {
        return id < 0;
    }
}
