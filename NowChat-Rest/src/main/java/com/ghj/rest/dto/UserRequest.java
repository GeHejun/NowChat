package com.ghj.rest.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
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
