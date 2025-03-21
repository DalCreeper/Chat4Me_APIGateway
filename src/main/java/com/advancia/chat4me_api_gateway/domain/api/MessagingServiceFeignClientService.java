package com.advancia.chat4me_api_gateway.domain.api;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface MessagingServiceFeignClientService {
    ResponseEntity<List<MessageDto>> getMessages(String tokenSender, UUID receiver);
    ResponseEntity<NewMessageDto> newMessage(NewMessageDto newMessageDto);
}