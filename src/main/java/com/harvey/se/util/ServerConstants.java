package com.harvey.se.util;

import java.text.SimpleDateFormat;
import java.util.Set;

/**
 * 常量类
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 07:24
 */
public class ServerConstants {
    public static final String AUTHORIZATION_HEADER = "authorization";
    public static final String RESTRICT_REQUEST_TIMES = "7";
    public static final Set<String> ROOT_AUTH_URI = Set.of("/user/create");
    public static final String MAX_PAGE_SIZE = "30";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final long CLEAR_CLICK_HISTORY_WAIT_MILLIONS = 10 * 60 * 1000;
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_STRING);
    public static final int WORKERS_ON_INSERT_USER_ACTION = 5;
}
