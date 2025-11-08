package com.harvey.se.controller.normal;

import com.harvey.se.pojo.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回声服务器
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-04 20:05
 */
@Slf4j
@RestController
@Api(tags = "测试与回声")
@RequestMapping("hello")
public class HelloController {

    @ApiOperation("回声")
    @GetMapping("/echo/{message}")
    public Result<String> hello(@PathVariable("message") @ApiParam("信息, 会被回声") String message) {
        return new Result<>(message);
    }
}
