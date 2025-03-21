package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.api.feign.AuthServiceFeignClient;
import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceFeignClientServiceImpl implements AuthServiceFeignClientService {
    private final AuthServiceFeignClient authServiceFeignClient;

    @Override
    public ResponseEntity<ChallengeResponseDto> startLogin(LoginRequestDto loginRequestDto) {
        return authServiceFeignClient.startLogin(loginRequestDto);
    }

    @Override
    public ResponseEntity<AuthTokenDto> verifyOTP(OTPVerificationRequestDto otpVerificationRequestDto) {
        return authServiceFeignClient.verifyOTP(otpVerificationRequestDto);
    }

    @Override
    public ResponseEntity<Boolean> validateToken(TokenValidationRequestDto tokenValidationRequestDto) {
        return authServiceFeignClient.validateToken(tokenValidationRequestDto);
    }

    @Override
    public ResponseEntity<AuthTokenDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        return authServiceFeignClient.refreshToken(refreshTokenRequestDto);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        return authServiceFeignClient.getUsers();
    }
}