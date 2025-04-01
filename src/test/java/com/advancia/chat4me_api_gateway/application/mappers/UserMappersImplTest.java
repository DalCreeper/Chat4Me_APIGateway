package com.advancia.chat4me_api_gateway.application.mappers;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.UserDto;
import com.advancia.chat4me_api_gateway.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMappersImplTest {
    @InjectMocks
    private UserMappersImpl userMappersImpl;

    @Test
    void shouldConvertUserFromDomain_whenIsAllOk() {
        User user = User.builder()
            .id(UUID.randomUUID())
            .name("testName")
            .surname("testSurname")
            .username("testUser")
            .email("testEmail")
            .password("testPassword")
            .build();

        UserDto userDto = userMappersImpl.convertFromDomain(user);
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getSurname(), userDto.getSurname());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
    }

    @Test
    void shouldReturnNull_whenUserIsNull() {
        assertNull(userMappersImpl.convertFromDomain((User) null));
    }

    @Test
    void shouldConvertUserDtoToDomain_whenIsAllOk() {
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("testName");
        userDto.setSurname("testSurname");
        userDto.setUsername("testUser");
        userDto.setEmail("testEmail");
        userDto.setPassword("testPassword");

        User user = userMappersImpl.convertToDomain(userDto);
        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getSurname(), user.getSurname());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
    }

    @Test
    void shouldReturnNull_whenUserDtoIsNull() {
        assertNull(userMappersImpl.convertToDomain((UserDto) null));
    }

    @Test
    void shouldConvertUserListFromDomain_whenIsAllOk() {
        List<User> users = List.of(
            User.builder()
                .id(UUID.randomUUID())
                .name("testName")
                .surname("testSurname")
                .username("testUser")
                .email("testEmail")
                .password("testPassword")
                .build()
        );

        List<UserDto> usersDto = userMappersImpl.convertFromDomain(users);
        assertNotNull(usersDto);
        assertEquals(users.size(), usersDto.size());
        assertEquals(users.getFirst().getId(), usersDto.getFirst().getId());
    }

    @Test
    void shouldReturnNull_whenUserListIsNull() {
        assertNull(userMappersImpl.convertFromDomain((List<User>) null));
    }

    @Test
    void shouldConvertUserDtoListToDomain_whenIsAllOk() {
        List<UserDto> usersDto = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("testName");
        userDto.setSurname("testSurname");
        userDto.setUsername("testUser");
        userDto.setEmail("testEmail");
        userDto.setPassword("testPassword");
        usersDto.add(userDto);

        List<User> users = userMappersImpl.convertToDomain(usersDto);
        assertNotNull(users);
        assertEquals(usersDto.size(), users.size());
        assertEquals(usersDto.getFirst().getId(), users.getFirst().getId());
    }

    @Test
    void shouldReturnNull_whenUserDtoListIsNull() {
        assertNull(userMappersImpl.convertToDomain((List<UserDto>) null));
    }
}