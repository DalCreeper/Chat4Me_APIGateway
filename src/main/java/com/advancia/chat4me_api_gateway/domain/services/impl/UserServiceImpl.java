package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.User;
import com.advancia.chat4me_api_gateway.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthServiceFeignClientService authFeignService;

    @Override
    public List<User> getUsers(String accessToken) {
        return authFeignService.getUsers(accessToken);
    }
}