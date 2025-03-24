package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import com.advancia.chat4me_api_gateway.application.services.MessagingServiceFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessagingServiceFeignImpl implements MessagingServiceFeign {
    private final MessagingApiDelegateImpl messagingApiDelegateImpl;

    @GetMapping("${app.feign.clients.api.getMessages}")
    @Override
    public ResponseEntity<List<MessageDto>> getMessages(String tokenSender, UUID userIdReceiver) {
        return messagingApiDelegateImpl.getMessages(tokenSender, userIdReceiver);
    }

    @GetMapping("${app.feign.clients.api.newMessage}")
    @Override
    public ResponseEntity<MessageDto> newMessage(NewMessageDto newMessageDto) {
        return messagingApiDelegateImpl.newMessage(newMessageDto);
    }
}