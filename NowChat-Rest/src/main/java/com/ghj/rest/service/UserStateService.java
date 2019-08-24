package com.ghj.rest.service;

import com.ghj.common.base.Result;
import com.ghj.common.dto.response.UserStateResponse;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

public interface UserStateService {
    UserStateResponse queryUserStateById(Integer id);
}
