package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.services.AuthServiceFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthServiceFeignImpl implements AuthServiceFeign {
    private final AuthApiDelegateImpl authApiDelegateImpl;
    private final UsersApiDelegateImpl usersApiDelegateImpl;

    @GetMapping("${app.feign.clients.api.startLogin}")
    @Override
    public ResponseEntity<ChallengeResponseDto> startLogin(LoginRequestDto loginRequestDto) {
        return authApiDelegateImpl.startLogin(loginRequestDto);
    }

    @GetMapping("${app.feign.clients.api.verifyOTP}")
    @Override
    public ResponseEntity<AuthTokenDto> verifyOTP(OTPVerificationRequestDto otpVerificationRequestDto) {
        return authApiDelegateImpl.verifyOTP(otpVerificationRequestDto);
    }

    @GetMapping("${app.feign.clients.api.validateToken}")
    @Override
    public ResponseEntity<Void> validateToken(TokenValidationRequestDto tokenValidationRequestDto) {
        return authApiDelegateImpl.validateToken(tokenValidationRequestDto);
    }

    @GetMapping("${app.feign.clients.api.refreshToken}")
    @Override
    public ResponseEntity<AuthTokenDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        return authApiDelegateImpl.refreshToken(refreshTokenRequestDto);
    }

    @GetMapping("${app.feign.clients.api.getUsers}")
    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        return usersApiDelegateImpl.getUsers();
    }
}