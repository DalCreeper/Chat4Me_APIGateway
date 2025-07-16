package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.api.MessagingServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.*;
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
public class MessageServiceImplTest {
    @Mock
    private MessagingServiceFeignClientService messagingFeignService;
    @Mock
    private AuthServiceFeignClientService authFeignService;

    @InjectMocks
    private MessageServiceImpl messageServiceImpl;

    @Test
    void shouldReturnMessages_whenGetMessagesCalled() {
        String tokenSender = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik";
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .accessToken(tokenSender)
            .build();
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken(tokenSender)
            .build();
        UUID userIdSender = UUID.randomUUID();
        UUID userIdReceiver = UUID.randomUUID();
        User user = User.builder()
            .id(userIdSender)
            .name("testName")
            .surname("testSurname")
            .username("testUser")
            .email("testEmail")
            .password("testPassword")
            .build();
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

        doNothing().when(authFeignService).validateToken(tokenValidationRequest);
        doReturn(user).when(authFeignService).extractUUID(userIdRequest);
        doReturn(messages).when(messagingFeignService).getMessages(userIdSender, userIdReceiver);

        List<Message> result = messageServiceImpl.getMessages(userIdRequest.getAccessToken(), userIdReceiver);
        assertEquals(messages, result);

        verify(authFeignService).validateToken(tokenValidationRequest);
        verify(authFeignService).extractUUID(userIdRequest);
        verify(messagingFeignService).getMessages(user.getId(), userIdReceiver);
    }

    @Test
    void shouldPropagateException_whenGetMessagesFails() {
        String tokenSender = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik";
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .accessToken(tokenSender)
            .build();
        UUID userIdReceiver = UUID.randomUUID();
        RuntimeException exception = new RuntimeException("Service error");

        doThrow(exception).when(authFeignService).validateToken(tokenValidationRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> messageServiceImpl.getMessages(tokenSender, userIdReceiver));
        assertSame(exception, ex);

        verify(authFeignService).validateToken(tokenValidationRequest);
        verify(authFeignService, never()).extractUUID(any(UserIdRequest.class));
        verify(messagingFeignService, never()).getMessages(any(UUID.class), any(UUID.class));
    }

    @Test
    void shouldReturnMessage_whenNewMessageCalled() {
        NewMessageRequest newMessageRequest = NewMessageRequest.builder()
            .tokenSender("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .receiver(UUID.randomUUID())
            .content("test")
            .build();
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .accessToken(newMessageRequest.getTokenSender())
            .build();
        UserIdRequest userIdRequest = UserIdRequest.builder()
            .accessToken(newMessageRequest.getTokenSender())
            .build();
        User user = User.builder()
            .id(UUID.randomUUID())
            .name("testName")
            .surname("testSurname")
            .username("testUser")
            .email("testEmail")
            .password("testPassword")
            .build();
        NewMessage newMessage = NewMessage.builder()
            .sender(user.getId())
            .receiver(newMessageRequest.getReceiver())
            .content(newMessageRequest.getContent())
            .build();
        Message message = Message.builder()
            .id(UUID.randomUUID())
            .sender(user.getId())
            .receiver(newMessageRequest.getReceiver())
            .content(newMessageRequest.getContent())
            .received(false)
            .timestamp(OffsetDateTime.now())
            .build();

        doNothing().when(authFeignService).validateToken(tokenValidationRequest);
        doReturn(user).when(authFeignService).extractUUID(userIdRequest);
        doReturn(message).when(messagingFeignService).newMessage(newMessage);

        Message result = messageServiceImpl.newMessage(newMessageRequest);
        assertEquals(message, result);

        verify(authFeignService).validateToken(tokenValidationRequest);
        verify(authFeignService).extractUUID(userIdRequest);
        verify(messagingFeignService).newMessage(newMessage);
    }

    @Test
    void shouldPropagateException_whenNewMessageFails() {
        NewMessageRequest newMessageRequest = NewMessageRequest.builder()
            .tokenSender("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDExMDczMDAsImV4cCI6MTc0MTE5MzcwMH0.lVCPs_piZa-se2ABiy6xjfor5oAvKSvv1T_n5YYKnik")
            .receiver(UUID.randomUUID())
            .content("test")
            .build();
        TokenValidationRequest tokenValidationRequest = TokenValidationRequest.builder()
            .accessToken(newMessageRequest.getTokenSender())
            .build();
        RuntimeException exception = new RuntimeException("Service error");

        doThrow(exception).when(authFeignService).validateToken(tokenValidationRequest);

        Exception ex = assertThrows(RuntimeException.class, () -> messageServiceImpl.newMessage(newMessageRequest));
        assertSame(exception, ex);

        verify(authFeignService).validateToken(tokenValidationRequest);
        verify(authFeignService, never()).extractUUID(any(UserIdRequest.class));
        verify(messagingFeignService, never()).newMessage(any(NewMessage.class));
    }
}