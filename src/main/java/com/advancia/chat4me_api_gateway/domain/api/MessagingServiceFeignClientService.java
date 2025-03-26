package com.advancia.chat4me_api_gateway.domain.api;

import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessage;

import java.util.List;
import java.util.UUID;

public interface MessagingServiceFeignClientService {
    List<Message> getMessages(String tokenSender, UUID receiver);
    Message newMessage(NewMessage newMessage);
}