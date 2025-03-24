package com.advancia.chat4me_api_gateway.application.services;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthServiceFeign {
    ResponseEntity<ChallengeResponseDto> startLogin(LoginRequestDto loginRequestDto);
    ResponseEntity<AuthTokenDto> verifyOTP(OTPVerificationRequestDto otpVerificationRequestDto);
    ResponseEntity<Void> validateToken(TokenValidationRequestDto tokenValidationRequestDto);
    ResponseEntity<AuthTokenDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
    ResponseEntity<List<UserDto>> getUsers();
}