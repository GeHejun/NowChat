package com.ghj.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;

/**
 * @author gehj
 * @version 1.0
 * @description TODO
 * @date 2019/8/21 18:40
 */
@FeignClient(name = "rest" )
public interface UserService {

    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    void queryUser(@NotNull Integer id);
}
