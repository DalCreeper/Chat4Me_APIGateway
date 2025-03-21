package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import com.advancia.chat4me_api_gateway.application.api.feign.MessagingServiceFeignClient;
import com.advancia.chat4me_api_gateway.domain.api.MessagingServiceFeignClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessagingServiceFeignClientServiceImpl implements MessagingServiceFeignClientService {
    private final MessagingServiceFeignClient messagingServiceFeignClient;

    @Override
    public ResponseEntity<List<MessageDto>> getMessages(String tokenSender, UUID receiver) {
        return messagingServiceFeignClient.getMessages(tokenSender, receiver);
    }

    @Override
    public ResponseEntity<NewMessageDto> newMessage(NewMessageDto newMessageDto) {
        return messagingServiceFeignClient.newMessage(newMessageDto);
    }
}