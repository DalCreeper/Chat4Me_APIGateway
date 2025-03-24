package com.advancia.chat4me_api_gateway.application.services.impl;

import com.advancia.Chat4Me_API_Gateway.generated.application.api.MessagingApiDelegate;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import com.advancia.chat4me_api_gateway.application.api.feign.MessagingServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.MessageMappers;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessagingApiDelegateImpl implements MessagingApiDelegate {
    private final MessagingServiceFeignClient messagingServiceFeignClient;
    private final MessageService messageService;
    private final MessageMappers messageMappers;

    @Override
    public ResponseEntity<List<MessageDto>> getMessages(String tokenSender, UUID userIdReceiver) {
        ResponseEntity<List<MessageDto>> messagesDto = messagingServiceFeignClient.getMessages(tokenSender, userIdReceiver);
        ResponseEntity<List<Message>> messages = messageService.resGetMessages(
            ResponseEntity.status(messagesDto.getStatusCode()).body(messageMappers.convertToDomain(messagesDto.getBody()))
        );
        return ResponseEntity.status(messages.getStatusCode()).body(messageMappers.convertFromDomain(messages.getBody()));
    }

    @Override
    public ResponseEntity<MessageDto> newMessage(NewMessageDto newMessageDto) {
        ResponseEntity<MessageDto> newMessDto = messagingServiceFeignClient.newMessage(newMessageDto);
        ResponseEntity<Message> message = messageService.resNewMessage(
            ResponseEntity.status(newMessDto.getStatusCode()).body(messageMappers.convertToDomain(newMessDto.getBody()))
        );
        return ResponseEntity.status(message.getStatusCode()).body(messageMappers.convertFromDomain(message.getBody()));
    }
}