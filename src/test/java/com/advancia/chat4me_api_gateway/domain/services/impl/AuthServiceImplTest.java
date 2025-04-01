package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    private AuthServiceFeignClientService authFeignService;
    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Test
    void shouldReturnChallengeResponse_whenIsAllOk() {
        LoginRequest loginRequest = LoginRequest.builder()
            .username("testUser")
            .password("testPassword")
            .build();
        ChallengeResponse challengeResponse = ChallengeResponse.builder()
            .challengeId(UUID.randomUUID())
            .message("test")
            .userId(UUID.randomUUID())
            .build();

        doReturn(challengeResponse).when(authFeignService).startLogin(loginRequest);

        ChallengeResponse result = authServiceImpl.startLogin(loginRequest);
        assertEquals(challengeResponse, result);

        verify(authFeignService).startLogin(loginRequest);
    }

    @Test
    void shouldPropagateException_whenStartLoginFails() {
        LoginRequest loginRequest = LoginRequest.builder()
            .username("testUser")
            .password("testPassword")
            .build();
        RuntimeException exception = new RuntimeException("Service error");

        doThrow(exception).when(authFeignService).startLogin(loginRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authServiceImpl.startLogin(loginRequest));
        assertSame(exception, ex);

        verify(authFeignService).startLogin(loginRequest);
    }

    @Test
    void shouldReturnAuthToken_whenIsAllOk() {
        OTPVerificationRequest otpVerificationRequest = OTPVerificationRequest.builder()
            .challengeId(UUID.randomUUID())
            .otp("123456")
            .expiresAt(1740478333L)
            .userId(UUID.randomUUID())
            .build();
        AuthToken authToken = AuthToken.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .expiresIn(86400000L)
            .message("Auth token generated")
            .userId(otpVerificationRequest.getUserId())
            .build();

        doReturn(authToken).when(authFeignService).verifyOTP(otpVerificationRequest);

        AuthToken result = authServiceImpl.verifyOTP(otpVerificationRequest);
        assertEquals(authToken, result);

        verify(authFeignService).verifyOTP(otpVerificationRequest);
    }

    @Test
    void shouldPropagateException_whenVerifyOTPFails() {
        OTPVerificationRequest otpVerificationRequest = OTPVerificationRequest.builder()
            .challengeId(UUID.randomUUID())
            .otp("123456")
            .expiresAt(1740478333L)
            .userId(UUID.randomUUID())
            .build();
        RuntimeException exception = new RuntimeException("Service error");

        doThrow(exception).when(authFeignService).verifyOTP(otpVerificationRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authServiceImpl.verifyOTP(otpVerificationRequest));
        assertSame(exception, ex);

        verify(authFeignService).verifyOTP(otpVerificationRequest);
    }

    @Test
    void shouldValidateToken_whenIsAllOk() {
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID())
            .build();

        authServiceImpl.validateToken(tokenValidationRequest);

        verify(authFeignService).validateToken(tokenValidationRequest);
    }

    @Test
    void shouldPropagateException_whenValidateTokenFails() {
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID())
            .build();
        RuntimeException exception = new RuntimeException("Service error");

        doThrow(exception).when(authFeignService).validateToken(tokenValidationRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authServiceImpl.validateToken(tokenValidationRequest));
        assertSame(exception, ex);

        verify(authFeignService).validateToken(tokenValidationRequest);
    }

    @Test
    void shouldReturnUser_whenIsAllOk() {
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .build();
        User user = User.builder()
            .id(UUID.randomUUID())
            .name("testName")
            .surname("testSurname")
            .username("testUsername")
            .email("testEmail")
            .password("testPassword")
            .tokenId(UUID.randomUUID())
            .build();

        doReturn(user).when(authFeignService).extractUUID(userIdRequest);

        User result = authServiceImpl.extractUUID(userIdRequest);
        assertEquals(user, result);

        verify(authFeignService).extractUUID(userIdRequest);
    }

    @Test
    void shouldPropagateException_whenExtractUUIDFails() {
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .build();
        RuntimeException exception = new RuntimeException("Service error");

        doThrow(exception).when(authFeignService).extractUUID(userIdRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authServiceImpl.extractUUID(userIdRequest));
        assertSame(exception, ex);

        verify(authFeignService).extractUUID(userIdRequest);
    }

    @Test
    void shouldReturnRefreshedAuthToken_whenIsAllOk() {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
            .refreshTokenId(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .build();
        AuthToken authToken = AuthToken.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .expiresIn(86400000L) // 1 day
            .message("Auth token re-generated")
            .userId(refreshTokenRequest.getUserId())
            .build();

        doReturn(authToken).when(authFeignService).refreshToken(refreshTokenRequest);

        AuthToken result = authServiceImpl.refreshToken(refreshTokenRequest);
        assertEquals(authToken, result);

        verify(authFeignService).refreshToken(refreshTokenRequest);
    }

    @Test
    void shouldPropagateException_whenRefreshTokenFails() {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
            .refreshTokenId(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .build();
        RuntimeException exception = new RuntimeException("Service error");

        doThrow(exception).when(authFeignService).refreshToken(refreshTokenRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authServiceImpl.refreshToken(refreshTokenRequest));
        assertSame(exception, ex);

        verify(authFeignService).refreshToken(refreshTokenRequest);
    }
}