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
    public ChallengeResponse resStartLogin(LoginRequest loginRequest) {
        return authFeignService.startLogin(loginRequest);
    }

    @Override
    public AuthToken resVerifyOTP(OTPVerificationRequest otpVerificationRequest) {
        return authFeignService.verifyOTP(otpVerificationRequest);
    }

    @Override
    public void resValidateToken(TokenValidationRequest tokenValidationRequest) {
        authFeignService.validateToken(tokenValidationRequest);
    }

    @Override
    public AuthToken resRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        return authFeignService.refreshToken(refreshTokenRequest);
    }
}