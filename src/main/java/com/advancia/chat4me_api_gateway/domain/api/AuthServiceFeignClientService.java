package com.advancia.chat4me_api_gateway.domain.api;


import com.advancia.chat4me_api_gateway.domain.model.*;

import java.util.List;

public interface AuthServiceFeignClientService {
    ChallengeResponse startLogin(LoginRequest loginRequest);
    AuthToken verifyOTP(OTPVerificationRequest otpVerificationRequest);
    void validateToken(TokenValidationRequest tokenValidationRequest);
    AuthToken refreshToken(RefreshTokenRequest refreshTokenRequest);
    List<User> getUsers();
}