package com.harvey.se.controller.normal;

import com.harvey.se.pojo.dto.ChatTextPiece;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.ChatService;
import com.harvey.se.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:14
 */
@Slf4j
@RestController
@Api(tags = "和LLM聊天的Controller")
@RequestMapping("/robot")
@EnableConfigurationProperties(ConstantsProperties.class)
public class RobotChatController {
    @Resource
    private ChatService chatService;

    @PostMapping(value = "/chat")
    @ApiOperation(
            "用户问问题, LLM进行回答, 回答使用流式回答. 在回答期间, 用户再问问题, 问题将被忽略. 请客户端阻止用户问问题. ")
    public Result<Long> streamChat(@RequestBody String message) throws InterruptedException {
        // 生成一个ID, 表示这一次回答
        return new Result<>(chatService.chat(UserHolder.currentUserId(), message, 0/*TODO更多...*/));
    }

    @ApiOperation("获取文本片段")
    @DeleteMapping(value = {"/pieces/{chat-id}/{limit}", "/pieces/{chat-id}"})
    public Result<List<ChatTextPiece>> pullPieces(
            @PathVariable("chat-id") @ApiParam("聊天ID") Long chatId,
            @PathVariable(value = "limit", required = false) Integer limit) {
        return new Result<>(chatService.pullPieces(chatId, limit));
    }

}
