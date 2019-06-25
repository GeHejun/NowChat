package com.ghj.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NowChatEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NowChatEurekaApplication.class, args);
    }
}
