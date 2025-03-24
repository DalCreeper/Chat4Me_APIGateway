package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.model.*;
import com.advancia.chat4me_api_gateway.domain.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseEntity<ChallengeResponse> resStartLogin(ResponseEntity<ChallengeResponse> resChallengeResponse) {
        return null;
    }

    @Override
    public ResponseEntity<AuthToken> resVerifyOTP(ResponseEntity<AuthToken> resAuthToken) {
        return null;
    }

    @Override
    public ResponseEntity<Void> resValidateToken(ResponseEntity<Void> res) {
        return null;
    }

    @Override
    public ResponseEntity<AuthToken> resRefreshToken(ResponseEntity<AuthToken> resRefreshAuthToken) {
        return null;
    }
}