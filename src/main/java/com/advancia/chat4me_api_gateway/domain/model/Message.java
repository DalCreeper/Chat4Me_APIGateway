package com.advancia.chat4me_api_gateway.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private UUID id;
    private UUID sender;
    private UUID receiver;
    private String content;
    private Boolean received;
    private OffsetDateTime timestamp;
}