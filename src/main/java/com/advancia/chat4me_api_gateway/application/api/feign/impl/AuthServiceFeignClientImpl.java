package com.advancia.chat4me_api_gateway.application.api.feign.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.api.feign.AuthServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.services.AuthServiceFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceFeignClientImpl implements AuthServiceFeignClient {
    private final AuthServiceFeign authServiceFeign;

    @Override
    public ResponseEntity<ChallengeResponseDto> startLogin(LoginRequestDto loginRequestDto) {
        return authServiceFeign.startLogin(loginRequestDto);
    }

    @Override
    public ResponseEntity<AuthTokenDto> verifyOTP(OTPVerificationRequestDto otpVerificationRequestDto) {
        return authServiceFeign.verifyOTP(otpVerificationRequestDto);
    }

    @Override
    public ResponseEntity<Void> validateToken(TokenValidationRequestDto tokenValidationRequestDto) {
        return authServiceFeign.validateToken(tokenValidationRequestDto);
    }

    @Override
    public ResponseEntity<AuthTokenDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        return authServiceFeign.refreshToken(refreshTokenRequestDto);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        return authServiceFeign.getUsers();
    }
}