package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MessageMappers {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "tokenSender", target = "tokenSender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "received", target = "received")
    @Mapping(source = "timestamp", target = "timestamp")
    Message convertToDomain(MessageDto messageDto);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "tokenSender", target = "tokenSender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "received", target = "received")
    @Mapping(source = "timestamp", target = "timestamp")
    MessageDto convertFromDomain(Message message);

    List<Message> convertToDomain(List<MessageDto> messagesDto);
    List<MessageDto> convertFromDomain(List<Message> messages);
}