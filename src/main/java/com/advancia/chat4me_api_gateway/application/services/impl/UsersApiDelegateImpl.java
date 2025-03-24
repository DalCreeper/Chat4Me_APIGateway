package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.api.UsersApiDelegate;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.UserDto;
import com.advancia.chat4me_api_gateway.application.api.feign.AuthServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.UserMappers;
import com.advancia.chat4me_api_gateway.domain.model.User;
import com.advancia.chat4me_api_gateway.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {
    private final AuthServiceFeignClient authServiceFeignClient;
    private final UserService userService;
    private final UserMappers userMappers;

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        ResponseEntity<List<UserDto>> usersDto = authServiceFeignClient.getUsers();
        ResponseEntity<List<User>> users = userService.resGetUsers(
            ResponseEntity.status(usersDto.getStatusCode()).body(userMappers.convertToDomain(usersDto.getBody()))
        );
        return ResponseEntity.status(users.getStatusCode()).body(userMappers.convertFromDomain(users.getBody()));
    }
}