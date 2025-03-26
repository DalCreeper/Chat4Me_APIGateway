package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import com.advancia.chat4me_api_gateway.application.api.feign.MessagingServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.MessageMappers;
import com.advancia.chat4me_api_gateway.domain.api.MessagingServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessagingServiceFeignClientServiceImpl implements MessagingServiceFeignClientService {
    private final MessagingServiceFeignClient messagingServiceFeignClient;
    private final MessageMappers messageMappers;

    @Override
    public List<Message> getMessages(String tokenSender, UUID receiver) {
        List<MessageDto> messagesDto = messagingServiceFeignClient.getMessages(tokenSender, receiver);
        return messageMappers.convertToDomain(messagesDto);
    }

    @Override
    public Message newMessage(NewMessage newMessage) {
        NewMessageDto newMessageDto = messageMappers.convertFromDomain(newMessage);
        MessageDto messageDto = messagingServiceFeignClient.newMessage(newMessageDto);
        return messageMappers.convertToDomain(messageDto);
    }
}