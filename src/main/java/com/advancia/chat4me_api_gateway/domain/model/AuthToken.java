package com.advancia.chat4me_api_gateway.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private UUID tokenId;
    private String accessToken;
    private long expiresIn;
    private String message;
    private UUID userId;
}