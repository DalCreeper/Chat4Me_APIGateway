package com.advancia.chat4me_api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class Chat4MeApiGatewayApplicationTests {
    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> Chat4MeApiGatewayApplication.main(new String[]{}));
    }
}
