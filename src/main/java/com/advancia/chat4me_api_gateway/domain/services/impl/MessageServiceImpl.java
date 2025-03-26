package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.api.MessagingServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessage;
import com.advancia.chat4me_api_gateway.domain.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessagingServiceFeignClientService messagingFeignService;

    @Override
    public List<Message> resGetMessages(String tokenSender, UUID userIdReceiver) {
        return messagingFeignService.getMessages(tokenSender, userIdReceiver);
    }

    @Override
    public Message resNewMessage(NewMessage newMessage) {
        return messagingFeignService.newMessage(newMessage);
    }
}