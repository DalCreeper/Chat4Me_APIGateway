package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.*;
import com.advancia.chat4me_api_gateway.domain.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthServiceFeignClientService authFeignService;

    @Override
    public ChallengeResponse startLogin(LoginRequest loginRequest) {
        return authFeignService.startLogin(loginRequest);
    }

    @Override
    public AuthToken verifyOTP(OTPVerificationRequest otpVerificationRequest) {
        return authFeignService.verifyOTP(otpVerificationRequest);
    }

    @Override
    public void validateToken(TokenValidationRequest tokenValidationRequest) {
        authFeignService.validateToken(tokenValidationRequest);
    }

    @Override
    public User extractUUID(UserIdRequest userIdRequest) {
        return authFeignService.extractUUID(userIdRequest);
    }

    @Override
    public AuthToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return authFeignService.refreshToken(refreshTokenRequest);
    }
}