package com.advancia.chat4me_api_gateway.application.services;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.api.feign.AuthServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.AuthMappers;
import com.advancia.chat4me_api_gateway.application.mappers.UserMappers;
import com.advancia.chat4me_api_gateway.application.services.impl.AuthServiceFeignClientServiceImpl;
import com.advancia.chat4me_api_gateway.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceFeignClientServiceImplTest {
    @Mock
    private AuthServiceFeignClient authServiceFeignClient;
    @Mock
    private AuthMappers authMappers;
    @Mock
    private UserMappers userMappers;
    @InjectMocks
    private AuthServiceFeignClientServiceImpl authFeignServiceImpl;

    @Test
    void shouldReturnChallengeResponse_whenLoginIsSuccessful() {
        LoginRequest loginRequest = LoginRequest.builder()
            .username("testUser")
            .password("testPassword")
            .build();
        LoginRequestDto loginRequestDto = new LoginRequestDto()
            .username("testUser")
            .password("testPassword");
        ChallengeResponseDto challengeResponseDto = new ChallengeResponseDto()
            .challengeId(UUID.randomUUID())
            .message("testMessage")
            .userId(UUID.randomUUID());
        ChallengeResponse challengeResponse = ChallengeResponse.builder()
            .challengeId(challengeResponseDto.getChallengeId())
            .message(challengeResponseDto.getMessage())
            .userId(challengeResponseDto.getUserId())
            .build();

        doReturn(loginRequestDto).when(authMappers).convertFromDomain(loginRequest);
        doReturn(challengeResponseDto).when(authServiceFeignClient).startLogin(loginRequestDto);
        doReturn(challengeResponse).when(authMappers).convertToDomain(challengeResponseDto);

        ChallengeResponse response = authFeignServiceImpl.startLogin(loginRequest);
        assertEquals(challengeResponse, response);

        verify(authMappers).convertFromDomain(loginRequest);
        verify(authServiceFeignClient).startLogin(loginRequestDto);
        verify(authMappers).convertToDomain(challengeResponseDto);
    }

    @Test
    void shouldPropagateException_whenLoginAuthServiceFails() {
        LoginRequest loginRequest = LoginRequest.builder()
            .username("testUser")
            .password("testPassword")
            .build();
        LoginRequestDto loginRequestDto = new LoginRequestDto()
            .username("testUser")
            .password("testPassword");
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(loginRequestDto).when(authMappers).convertFromDomain(loginRequest);
        doThrow(runtimeException).when(authServiceFeignClient).startLogin(loginRequestDto);

        Exception ex = assertThrows(RuntimeException.class, () -> authFeignServiceImpl.startLogin(loginRequest));
        assertSame(runtimeException, ex);

        verify(authMappers).convertFromDomain(loginRequest);
        verify(authServiceFeignClient).startLogin(loginRequestDto);
        verify(authMappers, never()).convertToDomain(any(ChallengeResponseDto.class));
    }

    @Test
    void shouldReturnAuthToken_whenOTPVerificationIsSuccessful() {
        OTPVerificationRequest otpRequest = OTPVerificationRequest.builder()
            .challengeId(UUID.randomUUID())
            .otp("123456")
            .expiresAt(1740478333L)
            .userId(UUID.randomUUID())
            .build();
        OTPVerificationRequestDto otpRequestDto = new OTPVerificationRequestDto()
            .challengeId(otpRequest.getChallengeId())
            .otp(otpRequest.getOtp())
            .expiresAt(otpRequest.getExpiresAt())
            .userId(otpRequest.getUserId());
        AuthTokenDto authTokenDto = new AuthTokenDto()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .expiresIn(86400000L) // 1 day
            .message("Auth token generated")
            .userId(otpRequest.getUserId());
        AuthToken authToken = AuthToken.builder()
            .tokenId(authTokenDto.getTokenId())
            .accessToken(authTokenDto.getAccessToken())
            .expiresIn(authTokenDto.getExpiresIn())
            .message(authTokenDto.getMessage())
            .userId(authTokenDto.getUserId())
            .build();

        doReturn(otpRequestDto).when(authMappers).convertFromDomain(otpRequest);
        doReturn(authTokenDto).when(authServiceFeignClient).verifyOTP(otpRequestDto);
        doReturn(authToken).when(authMappers).convertToDomain(authTokenDto);

        AuthToken response = authFeignServiceImpl.verifyOTP(otpRequest);
        assertEquals(authToken, response);

        verify(authMappers).convertFromDomain(otpRequest);
        verify(authServiceFeignClient).verifyOTP(otpRequestDto);
        verify(authMappers).convertToDomain(authTokenDto);
    }

    @Test
    void shouldPropagateException_whenOTPAuthServiceFails() {
        OTPVerificationRequest otpRequest = OTPVerificationRequest.builder()
            .challengeId(UUID.randomUUID())
            .otp("123456")
            .expiresAt(1740478333L)
            .userId(UUID.randomUUID())
            .build();
        OTPVerificationRequestDto otpRequestDto = new OTPVerificationRequestDto()
            .challengeId(otpRequest.getChallengeId())
            .otp(otpRequest.getOtp())
            .expiresAt(otpRequest.getExpiresAt())
            .userId(otpRequest.getUserId());
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(otpRequestDto).when(authMappers).convertFromDomain(otpRequest);
        doThrow(runtimeException).when(authServiceFeignClient).verifyOTP(otpRequestDto);

        Exception ex = assertThrows(RuntimeException.class, () -> authFeignServiceImpl.verifyOTP(otpRequest));
        assertSame(runtimeException, ex);

        verify(authMappers).convertFromDomain(otpRequest);
        verify(authServiceFeignClient).verifyOTP(otpRequestDto);
        verify(authMappers, never()).convertToDomain(any(AuthTokenDto.class));
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID())
            .build();
        TokenValidationRequestDto tokenValidationRequestDto = new TokenValidationRequestDto()
            .tokenId(tokenValidationRequest.getTokenId())
            .accessToken(tokenValidationRequest.getAccessToken())
            .userId(tokenValidationRequest.getUserId());

        doReturn(tokenValidationRequestDto).when(authMappers).convertFromDomain(tokenValidationRequest);
        doNothing().when(authServiceFeignClient).validateToken(tokenValidationRequestDto);

        assertDoesNotThrow(() -> authFeignServiceImpl.validateToken(tokenValidationRequest));

        verify(authMappers).convertFromDomain(tokenValidationRequest);
        verify(authServiceFeignClient).validateToken(tokenValidationRequestDto);
    }

    @Test
    void shouldPropagateException_whenTokenValidationAuthServiceFails() {
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID())
            .build();
        TokenValidationRequestDto tokenValidationRequestDto = new TokenValidationRequestDto()
            .tokenId(tokenValidationRequest.getTokenId())
            .accessToken(tokenValidationRequest.getAccessToken())
            .userId(tokenValidationRequest.getUserId());
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(tokenValidationRequestDto).when(authMappers).convertFromDomain(tokenValidationRequest);
        doThrow(runtimeException).when(authServiceFeignClient).validateToken(tokenValidationRequestDto);

        Exception ex = assertThrows(RuntimeException.class, () -> authFeignServiceImpl.validateToken(tokenValidationRequest));
        assertSame(runtimeException, ex);

        verify(authMappers).convertFromDomain(tokenValidationRequest);
        verify(authServiceFeignClient).validateToken(tokenValidationRequestDto);
    }

    @Test
    void shouldReturnUser_whenExtractUUIDIsSuccessful() {
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .build();
        UserIdRequestDto userIdRequestDto = new UserIdRequestDto()
            .accessToken(userIdRequest.getAccessToken());
        UserDto userDto = new UserDto()
            .id(UUID.randomUUID())
            .name("TestName")
            .surname("TestSurname")
            .username("TestUsername")
            .email("test@example.com")
            .password("testPassword")
            .tokenId(UUID.randomUUID());
        User user = User.builder()
            .id(userDto.getId())
            .name(userDto.getName())
            .surname(userDto.getSurname())
            .username(userDto.getUsername())
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .tokenId(userDto.getTokenId())
            .build();

        doReturn(userIdRequestDto).when(authMappers).convertFromDomain(userIdRequest);
        doReturn(userDto).when(authServiceFeignClient).extractUUID(userIdRequestDto);
        doReturn(user).when(userMappers).convertToDomain(userDto);

        User response = authFeignServiceImpl.extractUUID(userIdRequest);
        assertEquals(user, response);

        verify(authMappers).convertFromDomain(userIdRequest);
        verify(authServiceFeignClient).extractUUID(userIdRequestDto);
        verify(userMappers).convertToDomain(userDto);
    }

    @Test
    void shouldPropagateException_whenExtractUUIDAuthServiceFails() {
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .build();
        UserIdRequestDto userIdRequestDto = new UserIdRequestDto()
            .accessToken(userIdRequest.getAccessToken());
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(userIdRequestDto).when(authMappers).convertFromDomain(userIdRequest);
        doThrow(runtimeException).when(authServiceFeignClient).extractUUID(userIdRequestDto);

        Exception ex = assertThrows(RuntimeException.class, () -> authFeignServiceImpl.extractUUID(userIdRequest));
        assertSame(runtimeException, ex);

        verify(authMappers).convertFromDomain(userIdRequest);
        verify(authServiceFeignClient).extractUUID(userIdRequestDto);
    }

    @Test
    void shouldReturnAuthToken_whenRefreshTokenIsValid() {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
            .refreshTokenId(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .build();
        RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto()
            .refreshTokenId(refreshTokenRequest.getRefreshTokenId())
            .userId(refreshTokenRequest.getUserId());
        AuthTokenDto authTokenDto = new AuthTokenDto()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .expiresIn(86400000L) // 1 day
            .message("Auth token re-generated")
            .userId(refreshTokenRequest.getUserId());
        AuthToken authToken = AuthToken.builder()
            .tokenId(authTokenDto.getTokenId())
            .accessToken(authTokenDto.getAccessToken())
            .expiresIn(authTokenDto.getExpiresIn())
            .message(authTokenDto.getMessage())
            .userId(authTokenDto.getUserId())
            .build();

        doReturn(refreshTokenRequestDto).when(authMappers).convertFromDomain(refreshTokenRequest);
        doReturn(authTokenDto).when(authServiceFeignClient).refreshToken(refreshTokenRequestDto);
        doReturn(authToken).when(authMappers).convertToDomain(authTokenDto);

        AuthToken result = authFeignServiceImpl.refreshToken(refreshTokenRequest);
        assertEquals(authToken, result);

        verify(authMappers).convertFromDomain(refreshTokenRequest);
        verify(authServiceFeignClient).refreshToken(refreshTokenRequestDto);
        verify(authMappers).convertToDomain(authTokenDto);
    }

    @Test
    void shouldPropagateException_whenRefreshAuthTokenFails() {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
            .refreshTokenId(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .build();
        RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto()
            .refreshTokenId(refreshTokenRequest.getRefreshTokenId())
            .userId(refreshTokenRequest.getUserId());
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(refreshTokenRequestDto).when(authMappers).convertFromDomain(refreshTokenRequest);
        doThrow(runtimeException).when(authServiceFeignClient).refreshToken(refreshTokenRequestDto);

        Exception ex = assertThrows(RuntimeException.class, () -> authFeignServiceImpl.refreshToken(refreshTokenRequest));
        assertSame(runtimeException, ex);

        verify(authMappers).convertFromDomain(refreshTokenRequest);
        verify(authServiceFeignClient).refreshToken(refreshTokenRequestDto);
        verify(authMappers, never()).convertToDomain(any(AuthTokenDto.class));
    }

    @Test
    void shouldReturnUserList_whenGetUsersIsCalled() {
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik";
        UserDto userDto = new UserDto()
            .id(UUID.randomUUID())
            .name("TestName")
            .surname("TestSurname")
            .username("TestUsername")
            .email("test@example.com")
            .password("testPassword")
            .tokenId(UUID.randomUUID());
        User user = User.builder()
            .id(userDto.getId())
            .name(userDto.getName())
            .surname(userDto.getSurname())
            .username(userDto.getUsername())
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .tokenId(userDto.getTokenId())
            .build();

        List<UserDto> usersDto = List.of(userDto);
        List<User> users = List.of(user);

        doReturn(usersDto).when(authServiceFeignClient).getUsers(accessToken);
        doReturn(users).when(userMappers).convertToDomain(usersDto);

        List<User> result = authFeignServiceImpl.getUsers(accessToken);
        assertEquals(users, result);

        verify(authServiceFeignClient).getUsers(accessToken);
        verify(userMappers).convertToDomain(usersDto);
    }

    @Test
    void shouldPropagateException_whenUserServiceFails() {
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDE4NjUxMDksImV4cCI6MTc0MTk1MTUwOX0.das6YB90HEXhxzSOh8ukhHXmCjwPBmzHUx4yjIvaWJI";
        RuntimeException runtimeException = new RuntimeException("Service error");

        doThrow(runtimeException).when(authServiceFeignClient).getUsers(accessToken);

        Exception ex = assertThrows(RuntimeException.class, () -> authFeignServiceImpl.getUsers(accessToken));
        assertSame(runtimeException, ex);

        verify(authServiceFeignClient).getUsers(accessToken);
        verify(userMappers, never()).convertToDomain(anyList());
    }
}