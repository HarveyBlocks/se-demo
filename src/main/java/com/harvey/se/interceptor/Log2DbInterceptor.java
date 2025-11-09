package com.harvey.se.interceptor;

import com.harvey.se.pojo.dto.UserActionLogDto;
import com.harvey.se.pojo.entity.UserActionLog;
import com.harvey.se.service.UserActionLogService;
import com.harvey.se.util.ClientIpUtil;
import com.harvey.se.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * 创建用户请求日志, 并将其存入数据库
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-8 12:32
 */
@Slf4j
@Component
public class Log2DbInterceptor implements HandlerInterceptor {
    @Resource
    private UserActionLogService userActionLogService;


    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(UserActionLogDto.REQUEST_START_TIME, System.currentTimeMillis());
        return true;// 总是放过
    }


    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Object attribute = request.getAttribute(UserActionLogDto.REQUEST_START_TIME);
        if (!(attribute instanceof Long)) {
            throw new IllegalStateException("expect timestamp (long) in request attribute " +
                                            UserActionLogDto.REQUEST_START_TIME);
        }
        Date requestDate = new Date((Long) attribute);
        Integer status = statusFromResponse(response);
        UserActionLog userActionLog = new UserActionLog(
                null,
                UserHolder.existUser() ? UserHolder.currentUserId() : null,
                ClientIpUtil.get(request),
                request.getRequestURI(),
                request.getMethod().toUpperCase(),
                requestDate,
                (int) (System.currentTimeMillis() - requestDate.getTime()),
                status
        );
        userActionLogService.syncInsert(userActionLog);
    }

    private int statusFromResponse(HttpServletResponse response) {
        int status = response.getStatus();
        if (status != 200) {
            return status;
        }
        String statusStr = response.getHeader(UserActionLogDto.RESPONSE_CODE_IN_RESULT);
        try {
            return Integer.parseInt(statusStr);
        } catch (Exception e) {
            return 200;
        }
    }
}
