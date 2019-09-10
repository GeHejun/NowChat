package com.ghj.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/9/10 15:10
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
