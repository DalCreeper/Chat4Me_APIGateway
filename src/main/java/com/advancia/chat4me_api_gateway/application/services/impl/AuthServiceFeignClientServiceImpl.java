package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.chat4me_api_gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.api.feign.AuthServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.AuthMappers;
import com.advancia.chat4me_api_gateway.application.mappers.UserMappers;
import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceFeignClientServiceImpl implements AuthServiceFeignClientService {
    private final AuthServiceFeignClient authServiceFeignClient;
    private final AuthMappers authMappers;
    private final UserMappers userMappers;

    @Override
    public ChallengeResponse startLogin(LoginRequest loginRequest) {
        LoginRequestDto loginRequestDto = authMappers.convertFromDomain(loginRequest);
        ChallengeResponseDto challengeResponseDto = authServiceFeignClient.startLogin(loginRequestDto);
        return authMappers.convertToDomain(challengeResponseDto);
    }

    @Override
    public AuthToken verifyOTP(OTPVerificationRequest otpVerificationRequest) {
        OTPVerificationRequestDto otpVerificationRequestDto = authMappers.convertFromDomain(otpVerificationRequest);
        AuthTokenDto authTokenDto = authServiceFeignClient.verifyOTP(otpVerificationRequestDto);
        return authMappers.convertToDomain(authTokenDto);
    }

    @Override
    public void validateToken(TokenValidationRequest tokenValidationRequest) {
        TokenValidationRequestDto tokenValidationRequestDto = authMappers.convertFromDomain(tokenValidationRequest);
        authServiceFeignClient.validateToken(tokenValidationRequestDto);
    }

    @Override
    public User extractUUID(UserIdRequest userIdRequest) {
        UserIdRequestDto userIdRequestDto = authMappers.convertFromDomain(userIdRequest);
        UserDto userDto = authServiceFeignClient.extractUUID(userIdRequestDto);
        return userMappers.convertToDomain(userDto);
    }

    @Override
    public AuthToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenRequestDto refreshTokenRequestDto = authMappers.convertFromDomain(refreshTokenRequest);
        AuthTokenDto refreshAuthTokenDto = authServiceFeignClient.refreshToken(refreshTokenRequestDto);
        return authMappers.convertToDomain(refreshAuthTokenDto);
    }

    @Override
    public List<User> getUsers(String accessToken) {
        List<UserDto> usersDto = authServiceFeignClient.getUsers(accessToken);
        return userMappers.convertToDomain(usersDto);
    }
}