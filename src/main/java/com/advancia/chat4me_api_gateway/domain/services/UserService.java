package com.advancia.chat4me_api_gateway.domain.services;

import com.advancia.chat4me_api_gateway.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<List<User>> resGetUsers(ResponseEntity<List<User>> resUsers);
}