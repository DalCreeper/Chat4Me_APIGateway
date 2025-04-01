package com.advancia.chat4me_api_gateway.domain.services.impl;

import com.advancia.chat4me_api_gateway.domain.api.AuthServiceFeignClientService;
import com.advancia.chat4me_api_gateway.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private AuthServiceFeignClientService authFeignService;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void shouldReturnUserList_whenIsAllOk() {
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDE4NjUxMDksImV4cCI6MTc0MTk1MTUwOX0.das6YB90HEXhxzSOh8ukhHXmCjwPBmzHUx4yjIvaWJI";
        List<User> users = List.of(
            User.builder()
                .id(UUID.randomUUID())
                .name("testName")
                .surname("testSurname")
                .username("testUsername")
                .email("testEmail")
                .password("testPassword")
                .tokenId(UUID.randomUUID())
                .build(),
            User.builder()
                .id(UUID.randomUUID())
                .name("testName2")
                .surname("testSurname2")
                .username("testUsername2")
                .email("testEmail2")
                .password("testPassword2")
                .tokenId(UUID.randomUUID())
                .build()
        );

        doReturn(users).when(authFeignService).getUsers(accessToken);

        List<User> usersResult = userServiceImpl.getUsers(accessToken);
        assertEquals(users, usersResult);

        verify(authFeignService).getUsers(accessToken);
    }

    @Test
    void shouldPropagateException_whenFeignClientFails() {
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ZjExM2JiMi0zOGViLTQ3ZTctODRhMi1jZjI3MDMwMDRiODYiLCJpYXQiOjE3NDE4NjUxMDksImV4cCI6MTc0MTk1MTUwOX0.das6YB90HEXhxzSOh8ukhHXmCjwPBmzHUx4yjIvaWJI";
        RuntimeException exception = new RuntimeException("Service error");

        when(authFeignService.getUsers(accessToken)).thenThrow(exception);

        Exception ex = assertThrows(RuntimeException.class, () -> userServiceImpl.getUsers(accessToken));
        assertSame(exception, ex);

        verify(authFeignService).getUsers(accessToken);
    }
}