package com.advancia.chat4me_api_gateway.domain.services;

import com.advancia.chat4me_api_gateway.domain.model.*;

public interface AuthService {
    ChallengeResponse startLogin(LoginRequest loginRequest);
    AuthToken verifyOTP(OTPVerificationRequest otpVerificationRequest);
    void validateToken(TokenValidationRequest tokenValidationRequest);
    User extractUUID(UserIdRequest userIdRequest);
    AuthToken refreshToken(RefreshTokenRequest refreshTokenRequest);
}