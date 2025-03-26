package com.advancia.chat4me_api_gateway.domain.services;

import com.advancia.chat4me_api_gateway.domain.model.*;

public interface AuthService {
    ChallengeResponse resStartLogin(LoginRequest loginRequest);
    AuthToken resVerifyOTP(OTPVerificationRequest otpVerificationRequest);
    void resValidateToken(TokenValidationRequest tokenValidationRequest);
    AuthToken resRefreshToken(RefreshTokenRequest refreshTokenRequest);
}