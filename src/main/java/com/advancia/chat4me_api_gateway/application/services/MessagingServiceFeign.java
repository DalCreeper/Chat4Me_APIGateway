package com.advancia.chat4me_api_gateway.application.services;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface MessagingServiceFeign {
    ResponseEntity<List<MessageDto>> getMessages(String tokenSender, UUID userIdReceiver);
    ResponseEntity<MessageDto> newMessage(NewMessageDto newMessageDto);
}