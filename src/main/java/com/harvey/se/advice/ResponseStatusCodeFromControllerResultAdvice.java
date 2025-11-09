package com.harvey.se.advice;

import com.harvey.se.pojo.dto.UserActionLogDto;
import com.harvey.se.pojo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 面向切面增强controller解决swagger对已经封装的Result不会扫描到Model的问题.但是对status code的设置很难.所以没用
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 17:31
 */
@SuppressWarnings("NullableProblems")
@Slf4j
@RestControllerAdvice
public class ResponseStatusCodeFromControllerResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 过滤Controller
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        if (!(body instanceof Result)) {
            return body;
        }
        Result<?> resultBody = (Result<?>) body;
        ((ServletServerHttpResponse) response).getServletResponse().setHeader(
                UserActionLogDto.RESPONSE_CODE_IN_RESULT,
                String.valueOf(resultBody.getCode())
        );
        return body;
    }

}
