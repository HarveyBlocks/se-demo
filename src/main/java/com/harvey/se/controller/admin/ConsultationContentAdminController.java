package com.harvey.se.controller.admin;

import com.harvey.se.exception.UncompletedException;
import com.harvey.se.pojo.dto.ConsultationContentDto;
import com.harvey.se.pojo.dto.ConsultationContentWithUserEntityDto;
import com.harvey.se.pojo.dto.HotWordDto;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
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

import java.util.List;

/**
 * TODO
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

    @GetMapping(value = "/hot-word/{limit}")
    @ApiOperation(value = "查询排名前limit位的热词")
    public Result<List<HotWordDto>> topHotWord(
            @PathVariable(value = "limit") @ApiParam(value = "排名", required = true) Integer limit) {
        throw new UncompletedException("查询热词");
    }

    @GetMapping(value = "/user/{user-id}")
    @ApiOperation(value = "依据UserId查询某位User的购车咨询")
    public Result<List<ConsultationContentDto>> consultationByUser(
            @PathVariable("user-id") @ApiParam(value = "UserId", required = true) Long userId) {
        throw new UncompletedException("依据用户查询购车咨询");
    }

    @GetMapping(value = "/all/{limit}/{page}")
    @ApiOperation(value = "分页查询用户咨询")
    public Result<List<ConsultationContentDto>> consultation(
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ConstantsProperties.DEFAULT_PAGE_SIZE) String limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1") String page) {
        // 不提供就使用默认值
        throw new UncompletedException("分页查询购车咨询");
    }


    @GetMapping(value = "/both/{id}")
    @ApiOperation(value = "依据某一用户的ID查询该用户的用户信息和购车咨询信息")
    public Result<ConsultationContentWithUserEntityDto> bothById(
            @PathVariable(value = "id") @ApiParam("用户id") Long userId) {
        // 不提供就使用默认值
        throw new UncompletedException("依据用户id查询购车咨询+用户信息");
    }


    @GetMapping(value = "/both/all/{limit}/{page}")
    @ApiOperation(value = "分页查询用户咨询")
    @ApiResponse(message = "包含用户信息和咨询信息", code = 200)
    public Result<List<ConsultationContentWithUserEntityDto>> bothByPage(
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ConstantsProperties.DEFAULT_PAGE_SIZE) String limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1") String page) {
        // 不提供就使用默认值
        throw new UncompletedException("分页查询购车咨询+用户信息");
    }
}
