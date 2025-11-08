package com.harvey.se.controller.normal;

import com.harvey.se.exception.BadRequestException;
import com.harvey.se.exception.ResourceNotFountException;
import com.harvey.se.pojo.dto.LoginFormDto;
import com.harvey.se.pojo.dto.RegisterFormDto;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.pojo.entity.User;
import com.harvey.se.pojo.vo.Null;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.UserService;
import com.harvey.se.util.RedisConstants;
import com.harvey.se.util.ServerConstants;
import com.harvey.se.util.UserHolder;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 14:09
 */
@Slf4j
@RestController
@Api(tags = "用户登录校验")
@RequestMapping("/user")
@EnableConfigurationProperties(ConstantsProperties.class)
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ConstantsProperties constantsProperties;

    /**
     * 发送手机验证码
     */
    @PostMapping("/code")
    @ApiOperation("发送验证码, 不得使用, 因为没有购买相关服务")
    public Result<Null> sendCode(
            @RequestParam("phone") @ApiParam(required = true) String phone) {
        // 发送短信验证码并保存验证码
        String code = userService.sendCode(phone);
        if (code == null) {
            throw new BadRequestException("手机号不合法");
        }

        //session.setAttribute(CODE_SESSION_KEY,code);
        //session.setAttribute(PHONE_SESSION_KEY,phone);
        // 记得设置有效期
        stringRedisTemplate.opsForValue()
                .set(
                        RedisConstants.User.LOGIN_CODE_KEY + phone,
                        code,
                        RedisConstants.User.LOGIN_CODE_TTL,
                        TimeUnit.MINUTES
                );

        return Result.ok();
    }

    /**
     * 登录功能
     *
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    @ApiResponse(code = 200, responseHeaders = @ResponseHeader(name = ServerConstants.AUTHORIZATION_HEADER,
            description = "JWT token"),
            message = "在响应头里有用户校验token, 发送请求时在请求头里带上这个token, 表示这个用户已经登录")
    public Result<Null> login(
            @RequestBody @ApiParam(value = "需要用户登录的Json,密码和验证码二选一", readOnly = true, required = true)
            LoginFormDto loginForm, @ApiParam(hidden = true) HttpServletResponse response) {
        //实现登录功能
        // System.out.println(result);
        if (UserHolder.existUser()) {
            return new Result<>(400, "请不要重复登录");
        }
        String token = userService.chooseLoginWay(loginForm);
        response.setHeader(constantsProperties.getAuthorizationHeader(), token);
        return Result.ok();
    }

    /**
     * 注册功能
     *
     * @param registerForm 注册参数，包含手机号、密码
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    @ApiResponse(code = 200, responseHeaders = @ResponseHeader(name = ServerConstants.AUTHORIZATION_HEADER,
            description = "JWT token"),
            message = "在响应头里有用户校验token, 发送请求时在请求头里带上这个token, 表示这个用户已经登录")
    public Result<Null> register(
            @RequestBody @ApiParam(value = "需要用户注册的Json,使用密码", required = true) RegisterFormDto registerForm,
            @ApiParam(hidden = true) HttpServletResponse response) {
//        System.err.println("hi");
        //实现注册功能
        User user = userService.selectByPhone(registerForm.getPhone());
        if (user != null) {
            return new Result<>(400, "用户已存在");
        }
        String token = userService.register(registerForm);
        if (token == null) {
            return new Result<>(500, "保存失败");
        }
        response.setHeader(constantsProperties.getAuthorizationHeader(), token);
        return Result.ok();
    }


    /**
     * 登出功能
     *
     * @return 无
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<Null> logout(
            @ApiParam(hidden = true) HttpServletResponse response) {
        String tokenKey = RedisConstants.User.USER_CACHE_KEY + UserHolder.currentUserId();
        stringRedisTemplate.delete(tokenKey);
        response.setStatus(401);
        return Result.ok();
    }

    @ApiOperation("获取当前登录的用户并返回")
    @GetMapping("/me")
    public Result<UserDto> me() {
        // 获取当前登录的用户并返回
        return new Result<>(UserHolder.getUser());
    }

    /**
     * UserController 根据id查询用户
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户")
    public Result<UserDto> queryUserById(@PathVariable("id") @ApiParam(required = true) Long userId) {
        UserDto userDTO;
        try {
            userDTO = userService.queryUserByIdWithRedisson(userId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (userDTO == null) {
            log.warn("request null");
            throw new ResourceNotFountException("用户" + userId + "不存在");
        }
        return new Result<>(userDTO);
    }

    @ApiOperation(value = "更新本用户信息", notes = "不用传ID")
    @PutMapping("/update")
    public Result<Null> update(
            @RequestBody @ApiParam(value = "为了安全, 这里用户事实上只能更新自己的昵称", required = true)
            UserDto userDTO, @ApiParam(hidden = true) HttpServletRequest request) {
        userService.updateUser(userDTO, request.getHeader(constantsProperties.getAuthorizationHeader()));
        return Result.ok();
    }

}