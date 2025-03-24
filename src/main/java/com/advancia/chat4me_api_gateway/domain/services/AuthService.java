package com.advancia.chat4me_api_gateway.domain.services;

import com.advancia.chat4me_api_gateway.domain.model.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ChallengeResponse> resStartLogin(ResponseEntity<ChallengeResponse> resChallengeResponse);
    ResponseEntity<AuthToken> resVerifyOTP(ResponseEntity<AuthToken> resAuthToken);
    ResponseEntity<Void> resValidateToken(ResponseEntity<Void> res);
    ResponseEntity<AuthToken> resRefreshToken(ResponseEntity<AuthToken> resRefreshAuthToken);
}