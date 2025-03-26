package com.advancia.chat4me_api_gateway.application;

import com.advancia.Chat4Me_API_Gateway.generated.application.api.AuthApiDelegate;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.api.feign.AuthServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.AuthMappers;
import com.advancia.chat4me_api_gateway.domain.model.*;
import com.advancia.chat4me_api_gateway.domain.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {
    private final AuthService authService;
    private final AuthMappers authMappers;

    @Override
    public ResponseEntity<ChallengeResponseDto> startLogin(LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = authMappers.convertToDomain(loginRequestDto);
        ChallengeResponse challengeResponse = authService.resStartLogin(loginRequest);
        return ResponseEntity.ok(authMappers.convertFromDomain(challengeResponse));
    }

    @Override
    public ResponseEntity<AuthTokenDto> verifyOTP(OTPVerificationRequestDto otPVerificationRequestDto) {
        OTPVerificationRequest otpVerificationRequest = authMappers.convertToDomain(otPVerificationRequestDto);
        AuthToken authToken = authService.resVerifyOTP(otpVerificationRequest);
        return ResponseEntity.ok(authMappers.convertFromDomain(authToken));
    }

    @Override
    public ResponseEntity<Void> validateToken(TokenValidationRequestDto tokenValidationRequestDto) {
        TokenValidationRequest tokenValidationRequest = authMappers.convertToDomain(tokenValidationRequestDto);
        authService.resValidateToken(tokenValidationRequest);
        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<AuthTokenDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenRequest refreshTokenRequest = authMappers.convertToDomain(refreshTokenRequestDto);
        AuthToken authToken = authService.resRefreshToken(refreshTokenRequest);
        return ResponseEntity.ok(authMappers.convertFromDomain(authToken));
    }
}