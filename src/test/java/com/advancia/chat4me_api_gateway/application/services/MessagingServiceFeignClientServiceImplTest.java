package com.advancia.chat4me_api_gateway.application.services;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import com.advancia.chat4me_api_gateway.application.api.feign.MessagingServiceFeignClient;
import com.advancia.chat4me_api_gateway.application.mappers.MessageMappers;
import com.advancia.chat4me_api_gateway.application.services.impl.MessagingServiceFeignClientServiceImpl;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessagingServiceFeignClientServiceImplTest {
    @Mock
    private MessagingServiceFeignClient messagingServiceFeignClient;
    @Mock
    private MessageMappers messageMappers;
    @InjectMocks
    private MessagingServiceFeignClientServiceImpl messagingFeignServiceImpl;

    @Test
    void shouldReturnMessages_whenFeignClientWorks() {
        UUID sender = UUID.randomUUID();
        UUID receiver = UUID.randomUUID();
        List<MessageDto> messagesDto = List.of(
            new MessageDto()
                .id(UUID.randomUUID())
                .sender(sender)
                .receiver(receiver)
                .content("content")
                .received(false)
                .timestamp(OffsetDateTime.now()),
            new MessageDto()
                .id(UUID.randomUUID())
                .sender(sender)
                .receiver(receiver)
                .content("content2")
                .received(false)
                .timestamp(OffsetDateTime.now())
        );
        List<Message> messages = List.of(
            Message.builder()
                .id(messagesDto.get(0).getId())
                .sender(messagesDto.get(0).getSender())
                .receiver(messagesDto.get(0).getReceiver())
                .content(messagesDto.get(0).getContent())
                .received(messagesDto.get(0).getReceived())
                .timestamp(messagesDto.get(0).getTimestamp())
                .build(),
            Message.builder()
                .id(messagesDto.get(1).getId())
                .sender(messagesDto.get(1).getSender())
                .receiver(messagesDto.get(1).getReceiver())
                .content(messagesDto.get(1).getContent())
                .received(messagesDto.get(1).getReceived())
                .timestamp(messagesDto.get(1).getTimestamp())
                .build()
        );

        doReturn(messagesDto).when(messagingServiceFeignClient).getMessages(sender, receiver);
        doReturn(messages).when(messageMappers).convertToDomain(messagesDto);

        List<Message> result = messagingFeignServiceImpl.getMessages(sender, receiver);
        assertEquals(messages, result);

        verify(messagingServiceFeignClient).getMessages(sender, receiver);
        verify(messageMappers).convertToDomain(messagesDto);
    }

    @Test
    void shouldPropagateException_whenMessageServiceFails() {
        UUID userIdSender = UUID.randomUUID();
        UUID userIdReceiver = UUID.randomUUID();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doThrow(runtimeException).when(messagingServiceFeignClient).getMessages(userIdSender, userIdReceiver);

        Exception ex = assertThrowsExactly(RuntimeException.class, () -> messagingFeignServiceImpl.getMessages(userIdSender, userIdReceiver));
        assertSame(runtimeException, ex);

        verify(messagingServiceFeignClient).getMessages(userIdSender, userIdReceiver);
        verify(messageMappers, never()).convertToDomain(anyList());
    }

    @Test
    void shouldReturnNewMessage_whenFeignClientWorks() {
        NewMessage newMessage = NewMessage.builder()
            .sender(UUID.randomUUID())
            .receiver(UUID.randomUUID())
            .content("test")
            .build();
        NewMessageDto newMessageDto = new NewMessageDto()
            .sender(newMessage.getSender())
            .receiver(newMessage.getReceiver())
            .content(newMessage.getContent());
        MessageDto messageDto = new MessageDto()
            .id(UUID.randomUUID())
            .sender(newMessage.getSender())
            .receiver(newMessage.getReceiver())
            .content(newMessage.getContent())
            .received(false)
            .timestamp(OffsetDateTime.now());
        Message message = Message.builder()
            .id(messageDto.getId())
            .sender(messageDto.getSender())
            .receiver(messageDto.getReceiver())
            .content(messageDto.getContent())
            .received(messageDto.getReceived())
            .timestamp(messageDto.getTimestamp())
            .build();


        doReturn(newMessageDto).when(messageMappers).convertFromDomain(newMessage);
        doReturn(messageDto).when(messagingServiceFeignClient).newMessage(newMessageDto);
        doReturn(message).when(messageMappers).convertToDomain(messageDto);

        Message result = messagingFeignServiceImpl.newMessage(newMessage);

        assertEquals(message, result);
        verify(messageMappers).convertFromDomain(newMessage);
        verify(messagingServiceFeignClient).newMessage(newMessageDto);
        verify(messageMappers).convertToDomain(messageDto);
    }

    @Test
    void shouldPropagateException_whenNewMessageServiceFails() {
        NewMessage newMessage = NewMessage.builder()
            .sender(UUID.randomUUID())
            .receiver(UUID.randomUUID())
            .content("test")
            .build();
        NewMessageDto newMessageDto = new NewMessageDto()
            .sender(newMessage.getSender())
            .receiver(newMessage.getReceiver())
            .content(newMessage.getContent());
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(newMessageDto).when(messageMappers).convertFromDomain(newMessage);
        doThrow(runtimeException).when(messagingServiceFeignClient).newMessage(newMessageDto);

        Exception ex = assertThrowsExactly(RuntimeException.class, () -> messagingFeignServiceImpl.newMessage(newMessage));
        assertSame(runtimeException, ex);

        verify(messageMappers).convertFromDomain(newMessage);
        verify(messagingServiceFeignClient).newMessage(newMessageDto);
        verify(messageMappers, never()).convertToDomain(any(MessageDto.class));
    }
}