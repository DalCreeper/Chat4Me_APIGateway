package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.UserDto;
import com.advancia.chat4me_api_gateway.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMappers {
    List<User> convertToDomain(List<UserDto> usersDto);
    List<UserDto> convertFromDomain(List<User> users);
}