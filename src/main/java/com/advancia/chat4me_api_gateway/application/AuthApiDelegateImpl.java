package com.advancia.chat4me_api_gateway.application;

import com.advancia.chat4me_api_gateway.generated.application.api.AuthApiDelegate;
import com.advancia.chat4me_api_gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.mappers.AuthMappers;
import com.advancia.chat4me_api_gateway.application.mappers.UserMappers;
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
    private final UserMappers userMappers;

    @Override
    public ResponseEntity<ChallengeResponseDto> startLogin(LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = authMappers.convertToDomain(loginRequestDto);
        ChallengeResponse challengeResponse = authService.startLogin(loginRequest);
        return ResponseEntity.ok(authMappers.convertFromDomain(challengeResponse));
    }

    @Override
    public ResponseEntity<AuthTokenDto> verifyOTP(OTPVerificationRequestDto otPVerificationRequestDto) {
        OTPVerificationRequest otpVerificationRequest = authMappers.convertToDomain(otPVerificationRequestDto);
        AuthToken authToken = authService.verifyOTP(otpVerificationRequest);
        return ResponseEntity.ok(authMappers.convertFromDomain(authToken));
    }

    @Override
    public ResponseEntity<Void> validateToken(TokenValidationRequestDto tokenValidationRequestDto) {
        TokenValidationRequest tokenValidationRequest = authMappers.convertToDomain(tokenValidationRequestDto);
        authService.validateToken(tokenValidationRequest);
        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<UserDto> extractUUID(UserIdRequestDto userIdRequestDto) {
        UserIdRequest userIdRequest = authMappers.convertToDomain(userIdRequestDto);
        User user = authService.extractUUID(userIdRequest);
        return ResponseEntity.ok(userMappers.convertFromDomain(user));
    }

    @Override
    public ResponseEntity<AuthTokenDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenRequest refreshTokenRequest = authMappers.convertToDomain(refreshTokenRequestDto);
        AuthToken authToken = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(authMappers.convertFromDomain(authToken));
    }
}