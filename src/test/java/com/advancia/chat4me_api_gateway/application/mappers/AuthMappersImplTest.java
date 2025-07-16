package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.chat4me_api_gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthMappersImplTest {
    @InjectMocks
    private AuthMappersImpl authMappersImpl;

    @Test
    void shouldConvertLoginRequestFromDomain_whenIsAllOk() {
        LoginRequest loginRequest = LoginRequest.builder()
            .username("username")
            .password("password")
            .build();

        LoginRequestDto loginRequestDto = authMappersImpl.convertFromDomain(loginRequest);
        assertNotNull(loginRequestDto);
        assertEquals(loginRequest.getUsername(), loginRequestDto.getUsername());
        assertEquals(loginRequest.getPassword(), loginRequestDto.getPassword());
    }

    @Test
    void shouldReturnNull_whenLoginRequestIsNull() {
        assertNull(authMappersImpl.convertFromDomain((LoginRequest) null));
    }

    @Test
    void shouldConvertLoginRequestToDomain_whenIsAllOk() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(
            "username",
            "password"
        );

        LoginRequest loginRequest = authMappersImpl.convertToDomain(loginRequestDto);
        assertNotNull(loginRequest);
        assertEquals(loginRequestDto.getUsername(), loginRequest.getUsername());
        assertEquals(loginRequestDto.getPassword(), loginRequest.getPassword());
    }

    @Test
    void shouldReturnNull_whenLoginRequestDtoIsNull() {
        assertNull(authMappersImpl.convertToDomain((LoginRequestDto) null));
    }

    @Test
    void shouldConvertChallengeResponseFromDomain_whenIsAllOk() {
        ChallengeResponse challengeResponse = ChallengeResponse.builder()
            .challengeId(UUID.randomUUID())
            .message("test")
            .userId(UUID.randomUUID())
            .build();

        ChallengeResponseDto challengeResponseDto = authMappersImpl.convertFromDomain(challengeResponse);
        assertNotNull(challengeResponseDto);
        assertEquals(challengeResponse.getChallengeId(), challengeResponseDto.getChallengeId());
        assertEquals(challengeResponse.getMessage(), challengeResponseDto.getMessage());
        assertEquals(challengeResponse.getUserId(), challengeResponseDto.getUserId());
    }

    @Test
    void shouldReturnNull_whenChallengeResponseIsNull() {
        assertNull(authMappersImpl.convertFromDomain((ChallengeResponse) null));
    }

    @Test
    void shouldConvertChallengeResponseToDomain_whenIsAllOk() {
        ChallengeResponseDto challengeResponseDto = new ChallengeResponseDto();
        challengeResponseDto.setChallengeId(UUID.randomUUID());
        challengeResponseDto.setMessage("test");
        challengeResponseDto.setUserId(UUID.randomUUID());

        ChallengeResponse challengeResponse = authMappersImpl.convertToDomain(challengeResponseDto);
        assertNotNull(challengeResponse);
        assertEquals(challengeResponseDto.getChallengeId(), challengeResponse.getChallengeId());
        assertEquals(challengeResponseDto.getMessage(), challengeResponse.getMessage());
        assertEquals(challengeResponseDto.getUserId(), challengeResponse.getUserId());
    }

    @Test
    void shouldReturnNull_whenChallengeResponseDtoIsNull() {
        assertNull(authMappersImpl.convertToDomain((ChallengeResponseDto) null));
    }

    @Test
    void shouldConvertAuthTokenFromDomain_whenIsAllOk() {
        AuthToken authToken = AuthToken.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .expiresIn(86400000L)   // 1 day
            .message("Token generated")
            .userId(UUID.randomUUID())
            .build();

        AuthTokenDto authTokenDto = authMappersImpl.convertFromDomain(authToken);
        assertNotNull(authTokenDto);
        assertEquals(authToken.getTokenId(), authTokenDto.getTokenId());
        assertEquals(authToken.getAccessToken(), authTokenDto.getAccessToken());
        assertEquals(authToken.getExpiresIn(), authTokenDto.getExpiresIn());
        assertEquals(authToken.getUserId(), authTokenDto.getUserId());
        assertEquals(authToken.getMessage(), authTokenDto.getMessage());
    }

    @Test
    void shouldReturnNull_whenAuthTokenIsNull() {
        assertNull(authMappersImpl.convertFromDomain((AuthToken) null));
    }

    @Test
    void shouldConvertAuthTokenToDomain_whenIsAllOk() {
        AuthTokenDto authTokenDto = new AuthTokenDto();
        authTokenDto.setTokenId(UUID.randomUUID());
        authTokenDto.setAccessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik");
        authTokenDto.setExpiresIn(86400000L);   // 1 day
        authTokenDto.setMessage("Auth token generated");
        authTokenDto.setUserId(UUID.randomUUID());

        AuthToken authToken = authMappersImpl.convertToDomain(authTokenDto);
        assertNotNull(authToken);
        assertEquals(authTokenDto.getTokenId(), authToken.getTokenId());
        assertEquals(authTokenDto.getAccessToken(), authToken.getAccessToken());
        assertEquals(authTokenDto.getExpiresIn(), authToken.getExpiresIn());
        assertEquals(authTokenDto.getUserId(), authToken.getUserId());
        assertEquals(authTokenDto.getMessage(), authToken.getMessage());
    }

    @Test
    void shouldReturnNull_whenAuthTokenDtoIsNull() {
        assertNull(authMappersImpl.convertToDomain((AuthTokenDto) null));
    }

    @Test
    void shouldConvertOTPVerificationRequestFromDomain_whenIsAllOk() {
        OTPVerificationRequest otpVerificationRequest = OTPVerificationRequest.builder()
            .userId(UUID.randomUUID())
            .otp("123456")
            .build();

        OTPVerificationRequestDto otpVerificationRequestDto = authMappersImpl.convertFromDomain(otpVerificationRequest);
        assertNotNull(otpVerificationRequestDto);
        assertEquals(otpVerificationRequest.getUserId(), otpVerificationRequestDto.getUserId());
        assertEquals(otpVerificationRequest.getOtp(), otpVerificationRequestDto.getOtp());
    }

    @Test
    void shouldReturnNull_whenOTPVerificationRequestIsNull() {
        assertNull(authMappersImpl.convertFromDomain((OTPVerificationRequest) null));
    }

    @Test
    void shouldConvertOTPVerificationRequestToDomain_whenIsAllOk() {
        OTPVerificationRequestDto otpVerificationRequestDto = new OTPVerificationRequestDto()
            .challengeId(UUID.randomUUID())
            .otp("123456")
            .expiresAt(1740478333L)
            .userId(UUID.randomUUID());

        OTPVerificationRequest otpVerificationRequest = authMappersImpl.convertToDomain(otpVerificationRequestDto);
        assertNotNull(otpVerificationRequest);
        assertEquals(otpVerificationRequestDto.getChallengeId(), otpVerificationRequest.getChallengeId());
        assertEquals(otpVerificationRequestDto.getOtp(), otpVerificationRequest.getOtp());
        assertEquals(otpVerificationRequestDto.getExpiresAt(), otpVerificationRequest.getExpiresAt());
        assertEquals(otpVerificationRequestDto.getUserId(), otpVerificationRequest.getUserId());
    }

    @Test
    void shouldReturnNull_whenOTPVerificationRequestDtoIsNull() {
        assertNull(authMappersImpl.convertToDomain((OTPVerificationRequestDto) null));
    }

    @Test
    void shouldConvertTokenValidationRequestFromDomain_whenIsAllOk() {
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID())
            .build();

        TokenValidationRequestDto tokenValidationRequestDto = authMappersImpl.convertFromDomain(tokenValidationRequest);
        assertNotNull(tokenValidationRequestDto);
        assertEquals(tokenValidationRequest.getAccessToken(), tokenValidationRequestDto.getAccessToken());
    }

    @Test
    void shouldReturnNull_whenTokenValidationRequestIsNull() {
        assertNull(authMappersImpl.convertFromDomain((TokenValidationRequest) null));
    }

    @Test
    void shouldConvertTokenValidationRequestToDomain_whenIsAllOk() {
        TokenValidationRequestDto tokenValidationRequestDto = new TokenValidationRequestDto()
            .tokenId(UUID.randomUUID())
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .userId(UUID.randomUUID());

        TokenValidationRequest tokenValidationRequest = authMappersImpl.convertToDomain(tokenValidationRequestDto);
        assertNotNull(tokenValidationRequest);
        assertEquals(tokenValidationRequestDto.getTokenId(), tokenValidationRequest.getTokenId());
        assertEquals(tokenValidationRequestDto.getAccessToken(), tokenValidationRequest.getAccessToken());
        assertEquals(tokenValidationRequestDto.getUserId(), tokenValidationRequest.getUserId());
    }

    @Test
    void shouldReturnNull_whenTokenValidationRequestDtoIsNull() {
        assertNull(authMappersImpl.convertToDomain((TokenValidationRequestDto) null));
    }

    @Test
    void shouldConvertUserIdRequestFromDomain_whenIsAllOk() {
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .build();

        UserIdRequestDto userIdRequestDto = authMappersImpl.convertFromDomain(userIdRequest);
        assertNotNull(userIdRequestDto);
        assertEquals(userIdRequest.getAccessToken(), userIdRequestDto.getAccessToken());
    }

    @Test
    void shouldReturnNull_whenUserIdRequestIsNull() {
        assertNull(authMappersImpl.convertFromDomain((UserIdRequest) null));
    }

    @Test
    void shouldConvertUserIdRequestToDomain_whenIsAllOk() {
        UserIdRequestDto userIdRequestDto = new UserIdRequestDto();
        userIdRequestDto.setAccessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik");

        UserIdRequest userIdRequest = authMappersImpl.convertToDomain(userIdRequestDto);
        assertNotNull(userIdRequest);
        assertEquals(userIdRequestDto.getAccessToken(), userIdRequest.getAccessToken());
    }

    @Test
    void shouldReturnNull_whenUserIdRequestDtoIsNull() {
        assertNull(authMappersImpl.convertToDomain((UserIdRequestDto) null));
    }

    @Test
    void shouldConvertRefreshTokenRequestFromDomain_whenIsAllOk() {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
            .refreshTokenId(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .build();

        RefreshTokenRequestDto refreshTokenRequestDto = authMappersImpl.convertFromDomain(refreshTokenRequest);
        assertNotNull(refreshTokenRequestDto);
        assertEquals(refreshTokenRequest.getRefreshTokenId(), refreshTokenRequestDto.getRefreshTokenId());
        assertEquals(refreshTokenRequest.getUserId(), refreshTokenRequestDto.getUserId());
    }

    @Test
    void shouldReturnNull_whenRefreshTokenRequestIsNull() {
        assertNull(authMappersImpl.convertFromDomain((RefreshTokenRequest) null));
    }

    @Test
    void shouldConvertRefreshTokenRequestToDomain_whenIsAllOk() {
        RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto();
        refreshTokenRequestDto.refreshTokenId(UUID.randomUUID());
        refreshTokenRequestDto.userId(UUID.randomUUID());

        RefreshTokenRequest refreshTokenRequest = authMappersImpl.convertToDomain(refreshTokenRequestDto);
        assertNotNull(refreshTokenRequest);
        assertEquals(refreshTokenRequestDto.getRefreshTokenId(), refreshTokenRequest.getRefreshTokenId());
        assertEquals(refreshTokenRequestDto.getUserId(), refreshTokenRequest.getUserId());
    }

    @Test
    void shouldReturnNull_whenRefreshTokenRequestDtoIsNull() {
        assertNull(authMappersImpl.convertToDomain((RefreshTokenRequestDto) null));
    }
}