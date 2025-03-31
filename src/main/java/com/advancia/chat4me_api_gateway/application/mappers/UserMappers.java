package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.UserDto;
import com.advancia.chat4me_api_gateway.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMappers {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "tokenId", target = "tokenId")
    UserDto convertFromDomain(User user);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "tokenId", target = "tokenId")
    User convertToDomain(UserDto userDto);

    List<UserDto> convertFromDomain(List<User> users);
    List<User> convertToDomain(List<UserDto> usersDto);
}