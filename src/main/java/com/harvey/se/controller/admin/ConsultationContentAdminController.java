package com.harvey.se.controller.admin;

import com.harvey.se.pojo.dto.ConsultationContentDto;
import com.harvey.se.pojo.dto.ConsultationContentWithUserEntityDto;
import com.harvey.se.pojo.dto.HotWordDto;
import com.harvey.se.pojo.dto.UserInfoDto;
import com.harvey.se.pojo.entity.User;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.ConsultationContentService;
import com.harvey.se.service.HotWordService;
import com.harvey.se.service.UserService;
import com.harvey.se.util.ConstantsInitializer;
import com.harvey.se.util.ServerConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员管理用户咨询内容的信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:46
 */
@Slf4j
@RestController
@Api(tags = "管理员管理用户咨询内容的信息")
@RequestMapping("/admin/consultation/")
@EnableConfigurationProperties(ConstantsProperties.class)
public class ConsultationContentAdminController {
    @Resource
    private ConsultationContentService consultationContentService;
    @Resource
    private HotWordService hotWordService;

    @Resource
    private UserService userService;
    @Resource
    private ConstantsInitializer constantsInitializer;


    @GetMapping(value = "/hot-word/{limit}")
    @ApiOperation(value = "查询排名前limit位的热词")
    public Result<List<HotWordDto>> topHotWord(
            @PathVariable(value = "limit") @ApiParam(value = "排名", required = true) Integer limit) {
        return new Result<>(hotWordService.top(limit));
    }

    @GetMapping(value = "/user/{user-id}")
    @ApiOperation(value = "依据UserId查询某位User的购车咨询")
    public Result<ConsultationContentDto> consultationByUser(
            @PathVariable("user-id") @ApiParam(value = "UserId", required = true) Long userId) {
        return new Result<>(consultationContentService.queryByUser(userId));
    }

    @GetMapping({"/all/{limit}/{page}", "/all/{limit}", "/all"})
    @ApiOperation(value = "分页查询用户咨询")
    public Result<List<ConsultationContentDto>> consultation(
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        // 不提供就使用默认值
        return new Result<>(consultationContentService.queryByPage(constantsInitializer.initPage(page, limit)));
    }


    @GetMapping(value = "/combine/{id}")
    @ApiOperation(value = "依据某一用户的ID查询该用户的用户信息和购车咨询信息")
    public Result<ConsultationContentWithUserEntityDto> combineById(
            @PathVariable(value = "id") @ApiParam("用户id") Long userId) {
        // 不提供就使用默认值
        ConsultationContentDto consultationContentDto = consultationContentService.queryByUser(userId);
        UserInfoDto userInfoDto = userService.queryUserEntityById(userId);
        return new Result<>(ConsultationContentWithUserEntityDto.combine(consultationContentDto, userInfoDto));
    }


    @GetMapping({"/combine/all/{limit}/{page}", "/combine/all/{limit}", "/combine/all"})
    @ApiOperation(value = "分页查询用户信息和咨询的联合信息")
    @ApiResponse(message = "包含用户信息和咨询信息", code = 200)
    public Result<List<ConsultationContentWithUserEntityDto>> combineByPage(
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        // 不提供就使用默认值
        List<User> userList = userService.queryUserByPage(constantsInitializer.initPage(page, limit));
        return new Result<>(consultationContentService.combineWithUsers(userList));
    }
}
