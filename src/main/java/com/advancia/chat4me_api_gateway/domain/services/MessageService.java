package com.advancia.chat4me_api_gateway.domain.services;

import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessageRequest;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<Message> getMessages(String tokenSender, UUID userIdReceiver);
    Message newMessage(NewMessageRequest newMessageRequest);
}