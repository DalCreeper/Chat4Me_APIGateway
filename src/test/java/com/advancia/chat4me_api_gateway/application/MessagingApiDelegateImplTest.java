package com.advancia.chat4me_api_gateway.application;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageRequestDto;
import com.advancia.chat4me_api_gateway.application.mappers.MessageMappers;
import com.advancia.chat4me_api_gateway.domain.model.Message;
import com.advancia.chat4me_api_gateway.domain.model.NewMessage;
import com.advancia.chat4me_api_gateway.domain.model.NewMessageRequest;
import com.advancia.chat4me_api_gateway.domain.services.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessagingApiDelegateImplTest {
    @Mock
    private MessageService messageService;
    @Mock
    private MessageMappers messageMappers;
    @InjectMocks
    private MessagingApiDelegateImpl messagingApiDelegateImpl;

    @Test
    void shouldReturnMessages_whenIsAllOk() {
        String tokenSender = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik";
        UUID userIdSender = UUID.randomUUID();
        UUID userIdReceiver = UUID.randomUUID();
        List<Message> messages = List.of(
            Message.builder()
                .id(UUID.randomUUID())
                .sender(userIdSender)
                .receiver(userIdReceiver)
                .content("content")
                .received(false)
                .timestamp(OffsetDateTime.now())
                .build(),
            Message.builder()
                .id(UUID.randomUUID())
                .sender(userIdSender)
                .receiver(userIdReceiver)
                .content("content2")
                .received(false)
                .timestamp(OffsetDateTime.now())
                .build()
        );
        List<MessageDto> messagesDto = List.of(
            new MessageDto()
                .id(messages.get(0).getId())
                .sender(messages.get(0).getSender())
                .receiver(messages.get(0).getReceiver())
                .content(messages.get(0).getContent())
                .received(messages.get(0).getReceived())
                .timestamp(messages.get(0).getTimestamp()),
            new MessageDto()
                .id(messages.get(1).getId())
                .sender(messages.get(1).getSender())
                .receiver(messages.get(1).getReceiver())
                .content(messages.get(1).getContent())
                .received(messages.get(1).getReceived())
                .timestamp(messages.get(1).getTimestamp())
        );

        doReturn(messages).when(messageService).getMessages(tokenSender, userIdReceiver);
        doReturn(messagesDto).when(messageMappers).convertFromDomain(messages);

        ResponseEntity<List<MessageDto>> response = messagingApiDelegateImpl.getMessages(tokenSender, userIdReceiver);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(messagesDto, response.getBody());

        verify(messageService).getMessages(tokenSender, userIdReceiver);
        verify(messageMappers).convertFromDomain(messages);
    }

    @Test
    void shouldPropagateException_whenMessageServiceFails() {
        String tokenSender = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik";
        UUID userIdReceiver = UUID.randomUUID();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doThrow(runtimeException).when(messageService).getMessages(tokenSender, userIdReceiver);

        Exception ex = assertThrowsExactly(RuntimeException.class, () -> messagingApiDelegateImpl.getMessages(tokenSender, userIdReceiver));
        assertSame(runtimeException, ex);

        verify(messageService).getMessages(tokenSender, userIdReceiver);
        verify(messageMappers, never()).convertFromDomain(anyList());
    }

    @Test
    void shouldReturnNewMessage_whenIsAllOk() {
        String tokenSender = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik";
        NewMessageRequestDto newMessageRequestDto = new NewMessageRequestDto()
            .tokenSender(tokenSender)
            .receiver(UUID.randomUUID())
            .content("test");
        NewMessageRequest newMessRequest = NewMessageRequest.builder()
            .tokenSender(newMessageRequestDto.getTokenSender())
            .receiver(newMessageRequestDto.getReceiver())
            .content(newMessageRequestDto.getContent())
            .build();
        Message newMessage = Message.builder()
            .id(UUID.randomUUID())
            .sender(UUID.randomUUID())
            .receiver(newMessRequest.getReceiver())
            .content(newMessRequest.getContent())
            .received(false)
            .timestamp(OffsetDateTime.now())
            .build();
        MessageDto newMessDto = new MessageDto()
            .id(newMessage.getId())
            .sender(newMessage.getSender())
            .receiver(newMessage.getReceiver())
            .content(newMessage.getContent())
            .received(newMessage.getReceived())
            .timestamp(newMessage.getTimestamp());
        URI location = URI.create("/messages/" + newMessage.getId());

        doReturn(newMessRequest).when(messageMappers).convertToDomain(newMessageRequestDto);
        doReturn(newMessage).when(messageService).newMessage(newMessRequest);
        doReturn(newMessDto).when(messageMappers).convertFromDomain(newMessage);

        ResponseEntity<MessageDto> response = messagingApiDelegateImpl.newMessage(newMessageRequestDto);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(newMessDto, response.getBody());
        assertEquals(location.toString(), Objects.requireNonNull(response.getHeaders().getLocation()).toString());

        verify(messageService).newMessage(newMessRequest);
        verify(messageMappers).convertToDomain(newMessageRequestDto);
        verify(messageMappers).convertFromDomain(newMessage);
    }

    @Test
    void shouldPropagateException_whenNewMessageServiceFails() {
        String tokenSender = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik";
        NewMessageRequestDto newMessageRequestDto = new NewMessageRequestDto()
            .tokenSender(tokenSender)
            .receiver(UUID.randomUUID())
            .content("test");
        NewMessageRequest newMessRequest = NewMessageRequest.builder()
            .tokenSender(newMessageRequestDto.getTokenSender())
            .receiver(newMessageRequestDto.getReceiver())
            .content(newMessageRequestDto.getContent())
            .build();
        RuntimeException runtimeException = new RuntimeException("Service error");

        doReturn(newMessRequest).when(messageMappers).convertToDomain(newMessageRequestDto);
        doThrow(runtimeException).when(messageService).newMessage(newMessRequest);

        Exception ex = assertThrowsExactly(RuntimeException.class, () -> messagingApiDelegateImpl.newMessage(newMessageRequestDto));
        assertSame(runtimeException, ex);

        verify(messageMappers).convertToDomain(newMessageRequestDto);
        verify(messageService).newMessage(newMessRequest);
        verify(messageMappers, never()).convertFromDomain(any(Message.class));
    }
}