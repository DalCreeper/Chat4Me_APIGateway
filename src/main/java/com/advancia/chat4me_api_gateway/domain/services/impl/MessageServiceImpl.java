package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.api.MessagingServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.*;
import com.advancia.chat4me_api_gateway.domain.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessagingServiceFeignClientService messagingFeignService;
    private final AuthServiceFeignClientService authFeignService;

    @Override
    public List<Message> getMessages(String tokenSender, UUID userIdReceiver) {
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .accessToken(tokenSender)
            .build();
        authFeignService.validateToken(tokenValidationRequest);
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken(tokenSender)
            .build();
        User user = authFeignService.extractUUID(userIdRequest);
        return messagingFeignService.getMessages(user.getId(), userIdReceiver);
    }

    @Override
    public Message newMessage(NewMessageRequest newMessageRequest) {
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .accessToken(newMessageRequest.getTokenSender())
            .build();
        authFeignService.validateToken(tokenValidationRequest);
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken(newMessageRequest.getTokenSender())
            .build();
        User user = authFeignService.extractUUID(userIdRequest);
        NewMessage newMessage = NewMessage.builder()
            .sender(user.getId())
            .receiver(newMessageRequest.getReceiver())
            .content(newMessageRequest.getContent())
            .build();
        return messagingFeignService.newMessage(newMessage);
    }
}