package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Override
    public ResponseEntity<List<Message>> resGetMessages(ResponseEntity<List<Message>> resMessages) {
        return null;
    }

    @Override
    public ResponseEntity<Message> resNewMessage(ResponseEntity<Message> resMessage) {
        return null;
    }
}