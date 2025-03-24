package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.ChallengeResponseDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthMappers {
    @Mapping(source = "challengeId", target = "challengeId")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "userId", target = "userId")
    ChallengeResponseDto convertFromDomain(ChallengeResponse challengeResponse);
    @Mapping(source = "challengeId", target = "challengeId")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "userId", target = "userId")
    ChallengeResponse convertToDomain(ChallengeResponseDto challengeResponseDto);

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
}
