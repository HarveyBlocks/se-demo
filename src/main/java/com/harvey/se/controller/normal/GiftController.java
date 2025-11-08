package com.harvey.se.controller.normal;

import com.harvey.se.pojo.dto.GiftDto;
import com.harvey.se.pojo.dto.GiftInfoDto;
import com.harvey.se.pojo.vo.Null;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.GiftService;
import com.harvey.se.util.ConstantsInitializer;
import com.harvey.se.util.ServerConstants;
import com.harvey.se.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奖品功能, 展示奖品
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 01:06
 */
@Slf4j
@RestController
@Api(tags = "对礼品的查询和用户消费")
@RequestMapping("/gift")
@EnableConfigurationProperties(ConstantsProperties.class)
public class GiftController {
    @Resource
    private GiftService giftService;

    @Resource
    private ConstantsInitializer constantsInitializer;

    @GetMapping(value = {"/all/{limit}/{page}", "/all/{limit}", "/all"})
    @ApiOperation("分页查询Gift的简略信息")
    @ApiResponse(code = 200, message = "按照id升序")
    public Result<List<GiftDto>> queryAll(
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        // 不提供就使用默认值
        return new Result<>(giftService.queryByPage(constantsInitializer.initPage(page, limit)));
    }

    @GetMapping(value = {"/cost-in-range/{lower}/{upper}/{limit}/{page}", "/cost-in-range/{lower}/{upper}/{limit}",
            "/cost-in-range/{lower}/{upper}", "/cost-in-range/{lower}", "/cost-in-range",})
    @ApiOperation("用预算的上下限来计算, 分页查询Gift的简略信息")
    @ApiResponse(code = 200, message = "使用升序排序")
    public Result<List<GiftDto>> queryCostInRange(
            @PathVariable(value = "lower", required = false)
            @ApiParam(value = "商品花费积分下限(包含)", defaultValue = "0") Integer lower,
            @PathVariable(value = "upper", required = false) @ApiParam(value = "商品花费积分上限(包含), null表示无限大")
            Integer upper,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        // 按照花销排序, 使用升序排序
        return new Result<>(giftService.queryByCost(
                ConstantsInitializer.initIntRange(lower, upper),
                constantsInitializer.initPage(page, limit)
        ));
    }

    @GetMapping(value = "/detail/{id}")
    @ApiOperation("获取某一商品的详情信息")
    public Result<GiftInfoDto> giftDetail(
            @PathVariable(value = "id")
            @ApiParam(value = "目标礼品id, 依据用户选择的商品简略信息来获取", required = true) Long id) {
        // 查礼品询详细信息
        return new Result<>(giftService.queryDetail(id));
    }

    @PutMapping(value = "/consume/")
    @ApiOperation("用用户自己的积分进行消费")
    public Result<Null> consume(
            @RequestBody @ApiParam(value = "目标礼品id", required = true) Long id) {
        // 进行消费
        giftService.consume(UserHolder.getUser(), id);
        return Result.ok();
    }
}
