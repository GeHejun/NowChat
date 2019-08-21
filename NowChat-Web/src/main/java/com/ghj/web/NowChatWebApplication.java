package com.ghj.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author gehj
 * @date 2019/6/24 19:05
 */
@EnableEurekaClient
@SpringBootApplication
public class NowChatWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(NowChatWebApplication.class, args);
    }
}
