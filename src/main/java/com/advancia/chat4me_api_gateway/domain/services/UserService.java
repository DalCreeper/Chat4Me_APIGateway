package com.advancia.chat4me_api_gateway.domain.services;

import com.advancia.chat4me_api_gateway.domain.model.User;

import java.util.List;

public interface UserService {
    List<User> resGetUsers();
}