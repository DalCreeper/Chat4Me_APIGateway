package com.advancia.chat4me_api_gateway.domain.services;

import com.advancia.chat4me_api_gateway.domain.model.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageService {
    ResponseEntity<List<Message>> resGetMessages(ResponseEntity<List<Message>> resMessages);
    ResponseEntity<Message> resNewMessage(ResponseEntity<Message> resMessage);
}