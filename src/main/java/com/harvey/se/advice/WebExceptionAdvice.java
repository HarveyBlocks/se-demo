package com.harvey.se.advice;


import com.harvey.se.exception.BadRequestException;
import com.harvey.se.exception.ResourceNotFountException;
import com.harvey.se.exception.UnauthorizedException;
import com.harvey.se.exception.UncompletedException;
import com.harvey.se.pojo.vo.Null;
import com.harvey.se.pojo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;

/**
 * 异常处理增强
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 17:31
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public Result<Null> handleRuntimeException(RuntimeException e) {
        log.error(e.toString(), e);
        return new Result<>(500, "服务器异常,请稍后再试! 错误信息: " + e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public Result<Null> handleBadRequestException(BadRequestException bre) {
        return new Result<>(bre.getCode(), bre.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Null> handleBadRequestException(MethodArgumentTypeMismatchException e) {
        return new Result<>(403, "请求方式错误或URL参数格式不符合要求");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result<Null> handleUnauthorizedException(UnauthorizedException e) {
        return new Result<>(401, e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public Result<Null> handleSqlException(SQLException e) {
        log.error("exception on sql", e);
        return new Result<>(401, e.getMessage());
    }

    @ExceptionHandler(UncompletedException.class)
    public Result<Null> handleUncompletedException(UncompletedException e) {
        log.error("uncompleted:", e);
        return new Result<>(500, "未完成的功能: " + e.getMessage());
    }

    @ExceptionHandler(ResourceNotFountException.class)
    public Result<Null> handleResourceNotFountException(ResourceNotFountException e) {
        log.warn("not found: {} ", e.getMessage());
        return new Result<>(404, "未找到资源: " + e.getMessage());
    }
}
