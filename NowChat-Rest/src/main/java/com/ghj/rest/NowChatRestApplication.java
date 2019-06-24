package com.ghj.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gehj
 * @date 2019/6/24 19:05
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.ghj.rest.mapper"})
public class NowChatRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(NowChatRestApplication.class, args);
    }
}
