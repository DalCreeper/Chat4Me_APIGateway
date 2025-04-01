package com.advancia.chat4me_api_gateway.application;

import com.advancia.Chat4Me_API_Gateway.generated.application.api.MessagingApiDelegate;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageRequestDto;
import com.advancia.chat4me_api_gateway.application.mappers.MessageMappers;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessageRequest;
import com.advancia.chat4me_api_gateway.domain.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessagingApiDelegateImpl implements MessagingApiDelegate {
    private final MessageService messageService;
    private final MessageMappers messageMappers;

    @Override
    public ResponseEntity<List<MessageDto>> getMessages(String tokenSender, UUID userIdReceiver) {
        List<Message> messages = messageService.getMessages(tokenSender, userIdReceiver);
        return ResponseEntity.ok(messageMappers.convertFromDomain(messages));
    }

    @Override
    public ResponseEntity<MessageDto> newMessage(NewMessageRequestDto newMessageRequestDto) {
        NewMessageRequest newMessageRequest = messageMappers.convertToDomain(newMessageRequestDto);
        Message message = messageService.newMessage(newMessageRequest);
        URI location = URI.create("/messages/" + message.getId());
        return ResponseEntity.created(location).body(messageMappers.convertFromDomain(message));
    }
}