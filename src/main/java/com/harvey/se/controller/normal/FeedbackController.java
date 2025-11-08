package com.harvey.se.controller.normal;

import com.harvey.se.pojo.entity.Feedback;
import com.harvey.se.pojo.vo.Null;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.FeedbackService;
import com.harvey.se.service.PointService;
import com.harvey.se.util.ConstantsInitializer;
import com.harvey.se.util.RedisConstants;
import com.harvey.se.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *  反馈功能
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 01:07
 */
@Slf4j
@RestController
@Api(tags = "用户反馈")
@RequestMapping("/feedback")
@EnableConfigurationProperties(ConstantsProperties.class)
public class FeedbackController {
    @Resource
    private PointService pointService;
    @Resource
    private FeedbackService feedbackService;

    @PostMapping(value = "/feedback")
    @ApiOperation("提交一份反馈,反馈有加积分,加多少再议, 每天只能加一次. 每小时只能反馈一次")
    public Result<Null> feedback(
            @RequestBody @ApiParam(value = "反馈的文本, 最多250字", required = true) String text) {
        // 进行反馈, 反馈有加积分, 加多少再议
        //  一天在这个项目上只能加一次积分
        pointService.add(RedisConstants.Point.FEEDBACK, UserHolder.getUser(), 5, 1, TimeUnit.DAYS);
        feedbackService.saveNew(new Feedback(
                null,
                UserHolder.currentUserId(),
                text,
                ConstantsInitializer.nowDateTime(),
                false
        ));
        return Result.ok();
    }
}
