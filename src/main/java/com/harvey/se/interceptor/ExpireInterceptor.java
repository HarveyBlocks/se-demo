package com.harvey.se.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.UserService;
import com.harvey.se.util.ClientIpUtil;
import com.harvey.se.util.JwtTool;
import com.harvey.se.util.RedisConstants;
import com.harvey.se.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.harvey.se.util.RedisConstants.User.REQUEST_TIME_FIELD;

/**
 * 登录拦截器,会从Redis中查出用户的信息, 查到了就存入ThreadLocal
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-01-03 13:32
 */
@Slf4j
@Component
public class ExpireInterceptor implements HandlerInterceptor {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private JwtTool jwtTool;
    @Resource
    private ConstantsProperties constantsProperties;
    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        // 进入controller之前进行登录校验

//        System.err.println("1");
        String token = request.getHeader(constantsProperties.getAuthorizationHeader());//依据前端的信息

        // 获取请求头中的token
        String id;
        if (token == null || token.isEmpty()) {
            id = ClientIpUtil.get(request);
        } else {
            try {
                Long userId = jwtTool.parseToken(token);
                id = userId.toString();
                userService.loadCache(userId); // 更新一下缓存
            } catch (Exception e) {
                log.warn(e.getMessage());
                id = request.getRemoteAddr();
            }
        }
        UserDto userDto = doPreHandle(id);
        if (userDto == null) {
            return false;
        } else {
            if (userDto.getId() != null) {
                // 保存到ThreadLocal
                UserHolder.saveUser(userDto);
            }
            return true;
        }
    }

    public UserDto doPreHandle(String id) {

        // 获取user数据
        String tokenKey = RedisConstants.User.USER_CACHE_KEY + id;
        Map<Object, Object> userFieldMap = stringRedisTemplate.opsForHash().entries(tokenKey);

        if (userFieldMap.isEmpty()) {
            // entries不会返回null
            // 用户不存在Redis,就是游客,也给他限个流
            stringRedisTemplate.opsForHash()
                    .put(tokenKey, REQUEST_TIME_FIELD, constantsProperties.getRestrictRequestTimes());
            userFieldMap.put(REQUEST_TIME_FIELD, constantsProperties.getRestrictRequestTimes());
        }

        // 更新时间
        if (RedisConstants.ENTITY_CACHE_TTL != -1L) {
            stringRedisTemplate
                    .expire(tokenKey, RedisConstants.ENTITY_CACHE_TTL, TimeUnit.MINUTES);
        }

        String time = (String) userFieldMap.get(REQUEST_TIME_FIELD);
        if ("0".equals(time) || time.startsWith("-")) {
            log.error("id = " + id + " 访问次数太多了");
            //  经测试, 这个需要频率数在200QPS能触发28次左右(限制是同时7个)
            // 这种肯定是开挂了, 要不要加黑名单到里去?
            // TODO throw new BadRequestException("request too much");
        } else {
            stringRedisTemplate.opsForHash()
                    .increment(tokenKey, REQUEST_TIME_FIELD, -1);
        }
        userFieldMap.remove(REQUEST_TIME_FIELD);
        if (userFieldMap.isEmpty()) {
            // 现在是游客的可以走了
            return new UserDto();
        }
        // 第三个参数: 是否忽略转换过程中产生的异常
        return BeanUtil.fillBeanWithMap(userFieldMap, new UserDto(), false);
    }


    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex) {
        doAfter(ClientIpUtil.get(request));
    }

    public void doAfter(String ip) {
        String id;
        try {
            id = UserHolder.currentUserId().toString();
        } catch (Exception e) {
            id = ip;
        }
        String tokenKey = RedisConstants.User.USER_CACHE_KEY + id;
        Object timeFiled = stringRedisTemplate.opsForHash().get(tokenKey, REQUEST_TIME_FIELD);
        if (timeFiled != null) {
            stringRedisTemplate.opsForHash().increment(tokenKey, REQUEST_TIME_FIELD, 1);
        }

        // 完成Controller之后移除UserHolder, 以防下一次用这条线程的请求获取到不属于它的用户信息
        UserHolder.removeUser();
    }

}
