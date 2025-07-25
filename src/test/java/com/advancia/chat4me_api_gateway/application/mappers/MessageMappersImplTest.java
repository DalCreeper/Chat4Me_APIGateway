package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.chat4me_api_gateway.generated.application.model.MessageDto;
import com.advancia.chat4me_api_gateway.generated.application.model.NewMessageDto;
import com.advancia.chat4me_api_gateway.generated.application.model.NewMessageRequestDto;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessage;
import com.advancia.chat4me_api_gateway.domain.model.NewMessageRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessageMappersImplTest {
    @InjectMocks
    private MessageMappersImpl messageMappersImpl;

    @Test
    void shouldConvertMessageFromDomain_whenIsAllOk() {
        Message message = Message.builder()
            .id(UUID.randomUUID())
            .sender(UUID.randomUUID())
            .receiver(UUID.randomUUID())
            .content("test")
            .received(false)
            .timestamp(OffsetDateTime.now())
            .build();

        MessageDto messageDto = messageMappersImpl.convertFromDomain(message);
        assertNotNull(messageDto);
        assertEquals(message.getId(), messageDto.getId());
        assertEquals(message.getSender(), messageDto.getSender());
        assertEquals(message.getReceiver(), messageDto.getReceiver());
        assertEquals(message.getContent(), messageDto.getContent());
        assertEquals(message.getReceived(), messageDto.getReceived());
        assertEquals(message.getTimestamp(), messageDto.getTimestamp());
    }

    @Test
    void shouldReturnNull_whenMessageIsNull() {
        assertNull(messageMappersImpl.convertFromDomain((Message) null));
    }

    @Test
    void shouldConvertMessageToDomain_whenIsAllOk() {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(UUID.randomUUID());
        messageDto.setSender(UUID.randomUUID());
        messageDto.setReceiver(UUID.randomUUID());
        messageDto.setContent("test");
        messageDto.setReceived(false);
        messageDto.setTimestamp(OffsetDateTime.now());

        Message message = messageMappersImpl.convertToDomain(messageDto);
        assertNotNull(message);
        assertEquals(messageDto.getId(), message.getId());
        assertEquals(messageDto.getSender(), message.getSender());
        assertEquals(messageDto.getReceiver(), message.getReceiver());
        assertEquals(messageDto.getContent(), message.getContent());
        assertEquals(messageDto.getReceived(), message.getReceived());
        assertEquals(messageDto.getTimestamp(), message.getTimestamp());
    }

    @Test
    void shouldReturnNull_whenMessageDtoIsNull() {
        assertNull(messageMappersImpl.convertToDomain((MessageDto) null));
    }

    @Test
    void shouldConvertNewMessageRequestFromDomain_whenIsAllOk() {
        NewMessageRequest newMessageRequest = NewMessageRequest.builder()
            .tokenSender("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .receiver(UUID.randomUUID())
            .content("test")
            .build();

        NewMessageRequestDto newMessageRequestDto = messageMappersImpl.convertFromDomain(newMessageRequest);
        assertNotNull(newMessageRequestDto);
        assertEquals(newMessageRequest.getTokenSender(), newMessageRequestDto.getTokenSender());
        assertEquals(newMessageRequest.getReceiver(), newMessageRequestDto.getReceiver());
        assertEquals(newMessageRequest.getContent(), newMessageRequestDto.getContent());
    }

    @Test
    void shouldReturnNull_whenNewMessageRequestIsNull() {
        assertNull(messageMappersImpl.convertFromDomain((NewMessageRequest) null));
    }

    @Test
    void shouldConvertNewMessageRequestToDomain_whenIsAllOk() {
        NewMessageRequestDto newMessageRequestDto = new NewMessageRequestDto();
        newMessageRequestDto.setTokenSender("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik");
        newMessageRequestDto.setReceiver(UUID.randomUUID());
        newMessageRequestDto.setContent("test");

        NewMessageRequest newMessageRequest = messageMappersImpl.convertToDomain(newMessageRequestDto);
        assertNotNull(newMessageRequest);
        assertEquals(newMessageRequestDto.getTokenSender(), newMessageRequest.getTokenSender());
        assertEquals(newMessageRequestDto.getReceiver(), newMessageRequest.getReceiver());
        assertEquals(newMessageRequestDto.getContent(), newMessageRequest.getContent());
    }

    @Test
    void shouldReturnNull_whenNewMessageRequestDtoIsNull() {
        assertNull(messageMappersImpl.convertToDomain((NewMessageRequestDto) null));
    }

    @Test
    void shouldConvertNewMessageFromDomain_whenIsAllOk() {
        NewMessage newMessage = NewMessage.builder()
            .sender(UUID.randomUUID())
            .receiver(UUID.randomUUID())
            .content("test")
            .build();

        NewMessageDto newMessageDto = messageMappersImpl.convertFromDomain(newMessage);
        assertNotNull(newMessageDto);
        assertEquals(newMessage.getSender(), newMessageDto.getSender());
        assertEquals(newMessage.getReceiver(), newMessageDto.getReceiver());
        assertEquals(newMessage.getContent(), newMessageDto.getContent());
    }

    @Test
    void shouldReturnNull_whenNewMessageIsNull() {
        assertNull(messageMappersImpl.convertFromDomain((NewMessage) null));
    }

    @Test
    void shouldConvertNewMessageToDomain_whenIsAllOk() {
        NewMessageDto newMessageDto = new NewMessageDto();
        newMessageDto.setSender(UUID.randomUUID());
        newMessageDto.setReceiver(UUID.randomUUID());
        newMessageDto.setContent("test");

        NewMessage newMessage = messageMappersImpl.convertToDomain(newMessageDto);
        assertNotNull(newMessage);
        assertEquals(newMessageDto.getSender(), newMessage.getSender());
        assertEquals(newMessageDto.getReceiver(), newMessage.getReceiver());
        assertEquals(newMessageDto.getContent(), newMessage.getContent());
    }

    @Test
    void shouldReturnNull_whenNewMessageDtoIsNull() {
        assertNull(messageMappersImpl.convertToDomain((NewMessageDto) null));
    }

    @Test
    void shouldConvertMessageListFromDomain_whenIsAllOk() {
        Message message = Message.builder()
            .id(UUID.randomUUID())
            .sender(UUID.randomUUID())
            .receiver(UUID.randomUUID())
            .content("test")
            .received(false)
            .timestamp(OffsetDateTime.now())
            .build();

        List<MessageDto> messagesDto = messageMappersImpl.convertFromDomain(List.of(message));
        assertNotNull(messagesDto);
        assertEquals(1, messagesDto.size());
        assertEquals(messagesDto.getFirst().getId(), message.getId());
    }

    @Test
    void shouldReturnNull_whenMessageListIsNull() {
        assertNull(messageMappersImpl.convertFromDomain((List<Message>) null));
    }

    @Test
    void shouldConvertMessageListToDomain_whenIsAllOk() {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(UUID.randomUUID());
        messageDto.setSender(UUID.randomUUID());
        messageDto.setReceiver(UUID.randomUUID());
        messageDto.setContent("test");
        messageDto.setReceived(false);
        messageDto.setTimestamp(OffsetDateTime.now());

        List<Message> messages = messageMappersImpl.convertToDomain(List.of(messageDto));
        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals(messages.getFirst().getId(), messageDto.getId());
    }

    @Test
    void shouldReturnNull_whenMessageDtoListIsNull() {
        assertNull(messageMappersImpl.convertToDomain((List<MessageDto>) null));
    }
}