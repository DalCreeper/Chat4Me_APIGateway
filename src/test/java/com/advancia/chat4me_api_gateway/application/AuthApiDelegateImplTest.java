package com.advancia.chat4me_api_gateway.application;

import com.advancia.chat4me_api_gateway.application.mappers.AuthMappers;
import com.advancia.chat4me_api_gateway.application.mappers.UserMappers;
import com.advancia.chat4me_api_gateway.domain.model.*;
import com.advancia.chat4me_api_gateway.domain.services.AuthService;
import com.advancia.chat4me_api_gateway.generated.application.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthApiDelegateImplTest {
    @Mock
    private AuthService authService;
    @Mock
    private AuthMappers authMappers;
    @Mock
    private UserMappers userMappers;
    @InjectMocks
    private AuthApiDelegateImpl authApiDelegateImpl;

    @Test
    void shouldReturnChallengeResponseDtoAndPrintOTP_whenIsAllOk() {
        LoginRequestDto loginRequestDto = new LoginRequestDto()
            .username("testUser")
            .password("testPassword");
        LoginRequest loginRequest = LoginRequest.builder()
            .username(loginRequestDto.getUsername())
            .password(loginRequestDto.getPassword())
            .build();
        ChallengeResponse challengeResponse = ChallengeResponse.builder()
            .challengeId(UUID.randomUUID())
            .message("test")
            .userId(UUID.randomUUID())
            .build();
        ChallengeResponseDto challengeResponseDto = new ChallengeResponseDto()
            .challengeId(challengeResponse.getChallengeId())
            .message(challengeResponse.getMessage())
            .userId(challengeResponse.getUserId());

        doReturn(loginRequest).when(authMappers).convertToDomain(loginRequestDto);
        doReturn(challengeResponse).when(authService).startLogin(loginRequest);
        doReturn(challengeResponseDto).when(authMappers).convertFromDomain(challengeResponse);

        ResponseEntity<ChallengeResponseDto> response = authApiDelegateImpl.startLogin(loginRequestDto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(challengeResponseDto, response.getBody());

        verify(authMappers).convertToDomain(loginRequestDto);
        verify(authService).startLogin(loginRequest);
        verify(authMappers).convertFromDomain(challengeResponse);
    }

    @Test
    void shouldPropagateException_whenLoginAuthServiceFails() {
        LoginRequestDto loginRequestDto = new LoginRequestDto()
            .username("testUser")
            .password("testPassword");
        LoginRequest loginRequest = LoginRequest.builder()
            .username(loginRequestDto.getUsername())
            .password(loginRequestDto.getPassword())
            .build();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(loginRequest).when(authMappers).convertToDomain(loginRequestDto);
        doThrow(runtimeException).when(authService).startLogin(loginRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authApiDelegateImpl.startLogin(loginRequestDto));
        assertSame(runtimeException, ex);

        verify(authMappers).convertToDomain(loginRequestDto);
        verify(authService).startLogin(loginRequest);
        verify(authMappers, never()).convertFromDomain(any(ChallengeResponse.class));
    }

    @Test
    void shouldReturnAuthTokenDtoAndPrintToken_whenIsAllOk() {
        OTPVerificationRequestDto otpVerificationRequestDto = new OTPVerificationRequestDto()
            .challengeId(UUID.randomUUID())
            .otp("123456")
            .expiresAt(1740478333L)
            .userId(UUID.randomUUID());
        OTPVerificationRequest otpVerificationRequest = OTPVerificationRequest.builder()
            .challengeId(otpVerificationRequestDto.getChallengeId())
            .otp(otpVerificationRequestDto.getOtp())
            .expiresAt(otpVerificationRequestDto.getExpiresAt())
            .userId(otpVerificationRequestDto.getUserId())
            .build();
        AuthToken authToken = AuthToken.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .expiresIn(86400000L)
            .message("Auth token generated")
            .userId(otpVerificationRequest.getUserId())
            .build();
        AuthTokenDto authTokenDto = new AuthTokenDto()
            .tokenId(authToken.getTokenId())
            .accessToken(authToken.getAccessToken())
            .expiresIn(authToken.getExpiresIn())
            .message(authToken.getMessage())
            .userId(otpVerificationRequest.getUserId());

        doReturn(otpVerificationRequest).when(authMappers).convertToDomain(otpVerificationRequestDto);
        doReturn(authToken).when(authService).verifyOTP(otpVerificationRequest);
        doReturn(authTokenDto).when(authMappers).convertFromDomain(authToken);

        ResponseEntity<AuthTokenDto> response = authApiDelegateImpl.verifyOTP(otpVerificationRequestDto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(authTokenDto, response.getBody());

        verify(authMappers).convertToDomain(otpVerificationRequestDto);
        verify(authService).verifyOTP(otpVerificationRequest);
        verify(authMappers).convertFromDomain(authToken);
    }

    @Test
    void shouldPropagateException_whenOTPAuthServiceFails() {
        OTPVerificationRequestDto otpVerificationRequestDto = new OTPVerificationRequestDto()
            .challengeId(UUID.randomUUID())
            .otp("123456")
            .expiresAt(1740478333L)
            .userId(UUID.randomUUID());
        OTPVerificationRequest otpVerificationRequest = OTPVerificationRequest.builder()
            .challengeId(otpVerificationRequestDto.getChallengeId())
            .otp(otpVerificationRequestDto.getOtp())
            .expiresAt(otpVerificationRequestDto.getExpiresAt())
            .userId(otpVerificationRequestDto.getUserId())
            .build();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(otpVerificationRequest).when(authMappers).convertToDomain(otpVerificationRequestDto);
        doThrow(runtimeException).when(authService).verifyOTP(otpVerificationRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authApiDelegateImpl.verifyOTP(otpVerificationRequestDto));
        assertSame(runtimeException, ex);

        verify(authMappers).convertToDomain(otpVerificationRequestDto);
        verify(authService).verifyOTP(otpVerificationRequest);
        verify(authMappers, never()).convertFromDomain(any(AuthToken.class));
    }

    @Test
    void shouldValidateAndReturnAnEmptyBody_whenIsAllOk() {
        TokenValidationRequestDto tokenValidationRequestDto = new TokenValidationRequestDto()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID());
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .tokenId(tokenValidationRequestDto.getTokenId())
            .accessToken(tokenValidationRequestDto.getAccessToken())
            .userId(tokenValidationRequestDto.getUserId())
            .build();

        doReturn(tokenValidationRequest).when(authMappers).convertToDomain(tokenValidationRequestDto);
        doNothing().when(authService).validateToken(tokenValidationRequest);

        ResponseEntity<Void> response = authApiDelegateImpl.validateToken(tokenValidationRequestDto);
        assertEquals(200, response.getStatusCode().value());

        verify(authMappers).convertToDomain(tokenValidationRequestDto);
        verify(authService).validateToken(tokenValidationRequest);
    }

    @Test
    void shouldPropagateException_whenTokenValidationAuthServiceFails() {
        TokenValidationRequestDto tokenValidationRequestDto = new TokenValidationRequestDto()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID());
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .tokenId(tokenValidationRequestDto.getTokenId())
            .accessToken(tokenValidationRequestDto.getAccessToken())
            .userId(tokenValidationRequestDto.getUserId())
            .build();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(tokenValidationRequest).when(authMappers).convertToDomain(tokenValidationRequestDto);
        doThrow(runtimeException).when(authService).validateToken(tokenValidationRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authApiDelegateImpl.validateToken(tokenValidationRequestDto));
        assertSame(runtimeException, ex);

        verify(authMappers).convertToDomain(tokenValidationRequestDto);
        verify(authService).validateToken(tokenValidationRequest);
    }

    @Test
    void shouldReturnARefreshAuthTokenDtoAndPrintToken_whenIsAllOk() {
        RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto()
            .refreshTokenId(UUID.randomUUID())
            .userId(UUID.randomUUID());
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
            .refreshTokenId(refreshTokenRequestDto.getRefreshTokenId())
            .userId(refreshTokenRequestDto.getUserId())
            .build();
        AuthToken authToken = AuthToken.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .expiresIn(86400000L) // 1 day
            .message("Auth token re-generated")
            .userId(refreshTokenRequest.getUserId())
            .build();
        AuthTokenDto authTokenDto = new AuthTokenDto()
            .tokenId(authToken.getTokenId())
            .accessToken(authToken.getAccessToken())
            .userId(authToken.getUserId())
            .expiresIn(authToken.getExpiresIn())
            .message(authToken.getMessage())
            .userId(authToken.getUserId());

        doReturn(refreshTokenRequest).when(authMappers).convertToDomain(refreshTokenRequestDto);
        doReturn(authToken).when(authService).refreshToken(refreshTokenRequest);
        doReturn(authTokenDto).when(authMappers).convertFromDomain(authToken);

        ResponseEntity<AuthTokenDto> response = authApiDelegateImpl.refreshToken(refreshTokenRequestDto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(authTokenDto, response.getBody());

        verify(authMappers).convertToDomain(refreshTokenRequestDto);
        verify(authService).refreshToken(refreshTokenRequest);
        verify(authMappers).convertFromDomain(authToken);
    }

    @Test
    void shouldPropagateException_whenRefreshAuthTokenFails() {
        RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto()
            .refreshTokenId(UUID.randomUUID())
            .userId(UUID.randomUUID());
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
            .refreshTokenId(refreshTokenRequestDto.getRefreshTokenId())
            .userId(refreshTokenRequestDto.getUserId())
            .build();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(refreshTokenRequest).when(authMappers).convertToDomain(refreshTokenRequestDto);
        doThrow(runtimeException).when(authService).refreshToken(refreshTokenRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authApiDelegateImpl.refreshToken(refreshTokenRequestDto));
        assertSame(runtimeException, ex);

        verify(authMappers).convertToDomain(refreshTokenRequestDto);
        verify(authService).refreshToken(refreshTokenRequest);
        verify(authMappers, never()).convertFromDomain(any(AuthToken.class));
    }

    @Test
    void shouldReturnAUserDto_whenIsAllOk() {
        UserIdRequestDto userIdRequestDto = new UserIdRequestDto()
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik");
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken(userIdRequestDto.getAccessToken())
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
        UserDto userDto = new UserDto()
            .id(user.getId())
            .name(user.getName())
            .surname(user.getSurname())
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .tokenId(user.getTokenId());

        doReturn(userIdRequest).when(authMappers).convertToDomain(userIdRequestDto);
        doReturn(user).when(authService).extractUUID(userIdRequest);
        doReturn(userDto).when(userMappers).convertFromDomain(user);

        ResponseEntity<UserDto> response = authApiDelegateImpl.extractUUID(userIdRequestDto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDto, response.getBody());

        verify(authMappers).convertToDomain(userIdRequestDto);
        verify(authService).extractUUID(userIdRequest);
        verify(userMappers).convertFromDomain(user);
    }

    @Test
    void shouldPropagateException_whenUserIDRequestFails() {
        UserIdRequestDto userIdRequestDto = new UserIdRequestDto()
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik");
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken(userIdRequestDto.getAccessToken())
            .build();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(userIdRequest).when(authMappers).convertToDomain(userIdRequestDto);
        doThrow(runtimeException).when(authService).extractUUID(userIdRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> authApiDelegateImpl.extractUUID(userIdRequestDto));
        assertSame(runtimeException, ex);

        verify(authMappers).convertToDomain(userIdRequestDto);
        verify(authService).extractUUID(userIdRequest);
        verify(userMappers, never()).convertFromDomain(any(User.class));
    }
}