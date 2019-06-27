package com.ghj.rest;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * @author gehj
 * @date 2019/6/24 19:05
 */
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.ghj.rest.dao"})
@Import(FdfsClientConfig.class)
public class NowChatRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(NowChatRestApplication.class, args);
    }
}
