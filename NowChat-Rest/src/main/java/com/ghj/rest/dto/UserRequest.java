package com.ghj.rest.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {
    /**
     * 登陆账号
     */
    @NotNull
    private String loginName;

    /**
     * 密码
     */
    @NotNull
    private String passWord;
}
