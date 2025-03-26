package com.advancia.chat4me_api_gateway.application;

import com.advancia.Chat4Me_API_Gateway.generated.application.api.UsersApiDelegate;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.UserDto;
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
    private final UserService userService;
    private final UserMappers userMappers;

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.resGetUsers();
        return ResponseEntity.ok(userMappers.convertFromDomain(users));
    }
}