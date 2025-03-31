package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageRequestDto;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessage;
import com.advancia.chat4me_api_gateway.domain.model.NewMessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MessageMappers {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "received", target = "received")
    @Mapping(source = "timestamp", target = "timestamp")
    MessageDto convertFromDomain(Message message);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "received", target = "received")
    @Mapping(source = "timestamp", target = "timestamp")
    Message convertToDomain(MessageDto messageDto);

    @Mapping(source = "tokenSender", target = "tokenSender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    NewMessageRequestDto convertFromDomain(NewMessageRequest newMessageRequest);
    @Mapping(source = "tokenSender", target = "tokenSender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    NewMessageRequest convertToDomain(NewMessageRequestDto newMessageRequestDto);

    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    NewMessageDto convertFromDomain(NewMessage newMessage);
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    NewMessage convertToDomain(NewMessageDto newMessageDto);

    List<MessageDto> convertFromDomain(List<Message> messages);
    List<Message> convertToDomain(List<MessageDto> messagesDto);
}