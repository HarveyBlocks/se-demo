package com.harvey.se.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 12:53
 */
public class ExecutorServiceUtil {
    public static ExecutorService newFixedThreadPool(int nThread, String eachNamePre) {
        AtomicInteger threadNameCounter = new AtomicInteger(0);
        return Executors.newFixedThreadPool(
                nThread,
                r -> new Thread(r, eachNamePre + threadNameCounter.getAndIncrement())
        );
    }
}
