package com.harvey.se.pojo.dto;

import com.harvey.se.exception.ResourceNotFountException;
import com.harvey.se.pojo.entity.UserActionLog;
import com.harvey.se.util.ServerConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户行为日志
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户行为日志")
public class UserActionLogDto {

    public static final String REQUEST_START_TIME = "request-start-time";
    public static final String RESPONSE_CODE_IN_RESULT = "response-code-in-result";
    @ApiModelProperty("主键, 系统自动生成")
    private Long id;

    @ApiModelProperty("用户键, nullable")
    private Long userId;

    @ApiModelProperty("用户来的IP地址")
    private String ipAddress;

    @ApiModelProperty("用户请求的目标URL")
    private String requestUrl;

    @ApiModelProperty("请求的发送方法, 也就是Put, Post, Get, Delete")
    private String requestMethod;

    @ApiModelProperty(value = "发起请求的时间", example = ServerConstants.DATE_TIME_FORMAT_STRING)
    private Date requestTime;

    @ApiModelProperty(value = "响应时间损耗, 单位ms")
    private Integer requestTimeCost;

    @ApiModelProperty(value = "Http响应状态码")
    private Integer responseStatusCode;
    public static UserActionLogDto adapte(UserActionLog entity) {
        if (entity == null) {
            throw new ResourceNotFountException("请求不存在的资源");
        }
        return new UserActionLogDto(
                entity.getId(),
                entity.getUserId(),
                entity.getIpAddress(),
                entity.getRequestUrl(),
                entity.getRequestMethod(),
                entity.getRequestTime(),
                entity.getRequestTimeCost(),
                entity.getResponseStatusCode()
        );
    }

}
