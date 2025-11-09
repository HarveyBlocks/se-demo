package com.harvey.se.util;

/**
 * Redis有关的常量
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 15:17
 */
public class RedisConstants {
    public static final Long ENTITY_CACHE_TTL = 30 * 60L;
    public static final Long CACHE_NULL_TTL = 2L;
    public static final String LOCK_KEY_PRE = "se:lock:";
    public static final String CACHE_KEY_PRE = "se:cache:";
    public static final String SEARCH_HISTORY = "se:cache:search:history:";

    public static class Point {
        public static final String FEEDBACK = CACHE_KEY_PRE + "point:feedback:";

    }

    public static class User {
        public static final String LOCK_KEY = LOCK_KEY_PRE + "user:";
        public static final String USER_CACHE_KEY = CACHE_KEY_PRE + "user:token:";
        public static final String REQUEST_TIME_FIELD = "request_times";
        public static final String ID_FIELD = "id";
        public static final String NICKNAME_FIELD = "nickname";
        public static final String ROLE_FIELD = "role";
        public static final String LOGIN_CODE_KEY = CACHE_KEY_PRE + "login:code:";
        public static final Long LOGIN_CODE_TTL = 3 * 60L;
        public static final String POINTS_FIELD = "points";
    }

    public static class Chat {
        public static final String LOCK_KEY = LOCK_KEY_PRE + "chat:";
        public static final String PIECE_QUEUE_KEY = CACHE_KEY_PRE + "chat:pieces:";
        public static final String ID_GENERATOR = CACHE_KEY_PRE + "chat:id:";
        public static final String MULTIPLY_LEFT_POP_LUA = "mul_left_pop.lua";
    }
}
