package com.advancia.chat4me_api_gateway.application.eceptions;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.ErrorMessage;
import com.advancia.chat4me_api_gateway.application.exceptions.ApiGatewayExceptionHandler;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ApiGatewayExceptionHandlerTest {
    private ApiGatewayExceptionHandler apiGatewayExceptionHandler;
    private WebRequest mockRequest;

    @BeforeEach
    void setUp() {
        apiGatewayExceptionHandler = new ApiGatewayExceptionHandler();
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("/test-endpoint");
        mockRequest = new ServletWebRequest(servletRequest);
    }

    @Test
    void shouldThrowAnException_whenIsAllOk() {
        Exception exception = new Exception("Test exception");

        ResponseEntity<ErrorMessage> response = apiGatewayExceptionHandler.handleException(exception, mockRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test exception", response.getBody().getErrorDesc());
        assertEquals("java.lang.Exception", response.getBody().getErrorCode());
    }

    @Test
    void shouldThrowARuntimeException_whenIsAllOk() {
        FeignException exception = FeignException.errorStatus("Test feign exception", Response.builder()
            .status(HttpStatus.BAD_GATEWAY.value())
            .request(Request.create(Request.HttpMethod.GET, "/test", Map.of(), null, Charset.defaultCharset(), null))
            .build());

        ResponseEntity<ErrorMessage> response = apiGatewayExceptionHandler.handleFeignException(exception, mockRequest);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getErrorDesc().contains("Test feign exception"));
        assertTrue(response.getBody().getErrorCode().contains("FeignException"));
    }
}