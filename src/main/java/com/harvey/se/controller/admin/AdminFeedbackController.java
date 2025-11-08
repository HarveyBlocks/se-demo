package com.harvey.se.controller.admin;

import com.harvey.se.exception.BadRequestException;
import com.harvey.se.pojo.dto.FeedbackDto;
import com.harvey.se.pojo.vo.DateRange;
import com.harvey.se.pojo.vo.Null;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.FeedbackService;
import com.harvey.se.util.ConstantsInitializer;
import com.harvey.se.util.ServerConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 管理键盘
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 01:21
 */
@Slf4j
@RestController
@Api(tags = "管理员管理反馈信息")
@RequestMapping("/admin/feedback")
@EnableConfigurationProperties(ConstantsProperties.class)
public class AdminFeedbackController {

    @Resource
    private ConstantsInitializer constantsInitializer;
    @Resource
    private FeedbackService feedbackService;

    @GetMapping(value = {"/not-read/{time-from}/{time-to}/{limit}/{page}",
            "/not-read/{time-from}/{time-to}/{limit}", "/not-read/{time-from}/{time-to}",
            "/not-read/{time-from}", "/not-read",})
    @ApiOperation("查询一定时间内的未读的用户反馈")
    @ApiResponse(code = 200, message = "按照时间排序, 返回的时间顺序和参数的from-to一致")
    public Result<List<FeedbackDto>> getFeedbackDtoByTimeRange(
            @PathVariable(value = "time-from", required = false)
            @ApiParam(value = "日期查询的起点(包含)", example = ServerConstants.DATE_TIME_FORMAT_STRING)
            String timeFrom,
            @PathVariable(value = "time-to", required = false)
            @ApiParam(value = "日期查询的终点(包含)", example = ServerConstants.DATE_TIME_FORMAT_STRING) String timeTo,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        // 依据请求发送的时间查询
        DateRange dateRange;
        try {
            dateRange = ConstantsInitializer.initDateRange(timeFrom, timeTo);
        } catch (ParseException e) {
            throw new BadRequestException("错误的日期格式", e);
        }
        return new Result<>(feedbackService.queryFeedback(
                dateRange,
                constantsInitializer.initPage(page, limit),
                false
        ));
    }

    @GetMapping(value = {"/read/{time-from}/{time-to}/{limit}/{page}",
            "/read/{time-from}/{time-to}/{limit}", "/read/{time-from}/{time-to}",
            "/read/{time-from}", "/read",})
    @ApiOperation("查询一定时间内的已读的用户反馈")
    @ApiResponse(code = 200, message = "按照时间排序, 返回的时间顺序和参数的from-to一致")
    public Result<List<FeedbackDto>> getReadFeedbackDtoByTimeRange(
            @PathVariable(value = "time-from", required = false)
            @ApiParam(value = "日期查询的起点(包含)", example = ServerConstants.DATE_TIME_FORMAT_STRING)
            String timeFrom,
            @PathVariable(value = "time-to", required = false)
            @ApiParam(value = "日期查询的终点(包含)", example = ServerConstants.DATE_TIME_FORMAT_STRING) String timeTo,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        // 已读的
        DateRange dateRange;
        try {
            dateRange = ConstantsInitializer.initDateRange(timeFrom, timeTo);
        } catch (ParseException e) {
            throw new BadRequestException("错误的日期格式", e);
        }
        return new Result<>(feedbackService.queryFeedback(dateRange, constantsInitializer.initPage(page, limit), true));
    }

    @GetMapping(value = {"/user/{user-id}/{read}/{limit}/{page}", "/user/{user-id}/{read}/{limit}",
            "/user/{user-id}/{read}", "/user/{user-id}"})
    @ApiOperation("查询某一个特定用户的反馈")
    public Result<List<FeedbackDto>> getByUserId(
            @PathVariable(value = "user-id") @ApiParam(value = "用户id", required = true) Long userId,
            @PathVariable(value = "read", required = false) @ApiParam(value = "默认null, 表示两个都查") Boolean read,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        return new Result<>(feedbackService.queryFeedback(userId, constantsInitializer.initPage(page, limit), read));
    }


    @PutMapping(value = "/read")
    @ApiOperation("将某一条反馈标记为已读")
    public Result<Null> read(
            @RequestBody @ApiParam(value = "在body里面简单的就是一个id, 不需要键值对", required = true) Long id) {
        // 对某条反馈已读
        feedbackService.read(id);
        return Result.ok();
    }
}
