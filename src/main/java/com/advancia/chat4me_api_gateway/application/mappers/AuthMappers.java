package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.chat4me_api_gateway.generated.application.model.ChallengeResponseDto;
import com.advancia.chat4me_api_gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthMappers {
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    LoginRequestDto convertFromDomain(LoginRequest loginRequest);
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    LoginRequest convertToDomain(LoginRequestDto loginRequestDto);

    @Mapping(source = "challengeId", target = "challengeId")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "userId", target = "userId")
    ChallengeResponseDto convertFromDomain(ChallengeResponse challengeResponse);
    @Mapping(source = "challengeId", target = "challengeId")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "userId", target = "userId")
    ChallengeResponse convertToDomain(ChallengeResponseDto challengeResponseDto);

    @Mapping(source = "challengeId", target = "challengeId")
    @Mapping(source = "otp", target = "otp")
    @Mapping(source = "expiresAt", target = "expiresAt")
    @Mapping(source = "userId", target = "userId")
    OTPVerificationRequestDto convertFromDomain(OTPVerificationRequest otpVerificationRequest);
    @Mapping(source = "challengeId", target = "challengeId")
    @Mapping(source = "otp", target = "otp")
    @Mapping(source = "expiresAt", target = "expiresAt")
    @Mapping(source = "userId", target = "userId")
    OTPVerificationRequest convertToDomain(OTPVerificationRequestDto otpVerificationRequestDto);

    @Mapping(source = "tokenId", target = "tokenId")
    @Mapping(source = "accessToken", target = "accessToken")
    @Mapping(source = "userId", target = "userId")
    TokenValidationRequestDto convertFromDomain(TokenValidationRequest tokenValidationRequest);
    @Mapping(source = "tokenId", target = "tokenId")
    @Mapping(source = "accessToken", target = "accessToken")
    @Mapping(source = "userId", target = "userId")
    TokenValidationRequest convertToDomain(TokenValidationRequestDto tokenValidationRequestDto);

    @Mapping(source = "tokenId", target = "tokenId")
    @Mapping(source = "accessToken", target = "accessToken")
    @Mapping(source = "expiresIn", target = "expiresIn")
    @Mapping(source = "userId", target = "userId")
    AuthTokenDto convertFromDomain(AuthToken authToken);
    @Mapping(source = "tokenId", target = "tokenId")
    @Mapping(source = "accessToken", target = "accessToken")
    @Mapping(source = "expiresIn", target = "expiresIn")
    @Mapping(source = "userId", target = "userId")
    AuthToken convertToDomain(AuthTokenDto authTokenDto);

    @Mapping(source = "accessToken", target = "accessToken")
    UserIdRequestDto convertFromDomain(UserIdRequest userIdRequest);
    @Mapping(source = "accessToken", target = "accessToken")
    UserIdRequest convertToDomain(UserIdRequestDto userIdRequestDto);

    @Mapping(source = "refreshTokenId", target = "refreshTokenId")
    @Mapping(source = "userId", target = "userId")
    RefreshTokenRequestDto convertFromDomain(RefreshTokenRequest refreshTokenRequest);
    @Mapping(source = "refreshTokenId", target = "refreshTokenId")
    @Mapping(source = "userId", target = "userId")
    RefreshTokenRequest convertToDomain(RefreshTokenRequestDto refreshTokenRequestDto);
}
