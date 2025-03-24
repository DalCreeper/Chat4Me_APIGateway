package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.model.User;
import com.advancia.chat4me_api_gateway.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<List<User>> resGetUsers(ResponseEntity<List<User>> resUsers) {
        return null;
    }
}