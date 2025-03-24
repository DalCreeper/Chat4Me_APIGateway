package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.api.AuthApiDelegate;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.api.feign.AuthServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.AuthMappers;
import com.advancia.chat4me_api_gateway.domain.model.AuthToken;
import com.advancia.chat4me_api_gateway.domain.model.ChallengeResponse;
import com.advancia.chat4me_api_gateway.domain.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {
    private final AuthServiceFeignClient authServiceFeignClient;
    private final AuthService authService;
    private final AuthMappers authMappers;

    @Override
    public ResponseEntity<ChallengeResponseDto> startLogin(LoginRequestDto loginRequestDto) {
        ResponseEntity<ChallengeResponseDto> challengeResponseDto = authServiceFeignClient.startLogin(loginRequestDto);
        ResponseEntity<ChallengeResponse> challengeResponse = authService.resStartLogin(
            ResponseEntity.status(challengeResponseDto.getStatusCode()).body(authMappers.convertToDomain(challengeResponseDto.getBody()))
        );
        return ResponseEntity.status(challengeResponse.getStatusCode()).body(authMappers.convertFromDomain(challengeResponse.getBody()));
    }

    @Override
    public ResponseEntity<AuthTokenDto> verifyOTP(OTPVerificationRequestDto otPVerificationRequestDto) {
        ResponseEntity<AuthTokenDto> authTokenDto = authServiceFeignClient.verifyOTP(otPVerificationRequestDto);
        ResponseEntity<AuthToken> authToken = authService.resVerifyOTP(
            ResponseEntity.status(authTokenDto.getStatusCode()).body(authMappers.convertToDomain(authTokenDto.getBody()))
        );
        return ResponseEntity.status(authToken.getStatusCode()).body(authMappers.convertFromDomain(authToken.getBody()));
    }

    @Override
    public ResponseEntity<Void> validateToken(TokenValidationRequestDto tokenValidationRequestDto) {
        ResponseEntity<Void> resValDto = authServiceFeignClient.validateToken(tokenValidationRequestDto);
        ResponseEntity<Void> resVal = authService.resValidateToken(
            ResponseEntity.status(resValDto.getStatusCode()).body(resValDto.getBody())
        );
        return ResponseEntity.status(resVal.getStatusCode()).body(resVal.getBody());
    }

    @Override
    public ResponseEntity<AuthTokenDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        ResponseEntity<AuthTokenDto> refreshAuthTokenDto = authServiceFeignClient.refreshToken(refreshTokenRequestDto);
        ResponseEntity<AuthToken> refreshAuthToken = authService.resRefreshToken(
            ResponseEntity.status(refreshAuthTokenDto.getStatusCode()).body(authMappers.convertToDomain(refreshAuthTokenDto.getBody()))
        );
        return ResponseEntity.status(refreshAuthToken.getStatusCode()).body(authMappers.convertFromDomain(refreshAuthToken.getBody()));
    }
}