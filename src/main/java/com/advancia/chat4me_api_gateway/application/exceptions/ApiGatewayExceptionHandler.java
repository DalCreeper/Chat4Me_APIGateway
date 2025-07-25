package com.advancia.chat4me_api_gateway.application.exceptions;

import com.advancia.chat4me_api_gateway.generated.application.model.ErrorMessage;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@ControllerAdvice
public class ApiGatewayExceptionHandler {
    private void logError(Exception ex, WebRequest request) {
        log.error(
            "An error happened while calling {} API: {}",
            ((ServletWebRequest) request).getRequest().getRequestURI(),
            ex.getMessage(), ex
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(exception.getClass().getName());
        errorMessage.setErrorDesc(exception.getMessage());
        logError(exception, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignException(FeignException exception, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(exception.getClass().getName());
        errorMessage.setErrorDesc(exception.getMessage());
        logError(exception, request);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorMessage);
    }
}