package com.ghj.rest.service;

import com.ghj.rest.dto.SessionResponse;

import java.util.List;

/**
 * @author gehj
 * @date 2019/6/2511:31
 */
public interface SessionService {
    List<SessionResponse> listSessionByUserId(Integer userId);
}
