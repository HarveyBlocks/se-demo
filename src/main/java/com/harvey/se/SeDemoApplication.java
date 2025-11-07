package com.harvey.se;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-04 20:05
 */
@MapperScan(basePackages = "com.harvey.se.dao")
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class SeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeDemoApplication.class, args);
    }

}
