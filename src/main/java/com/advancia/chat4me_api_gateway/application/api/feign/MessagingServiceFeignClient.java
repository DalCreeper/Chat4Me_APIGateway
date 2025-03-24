package com.advancia.chat4me_api_gateway.application.api.feign;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.MessageDto;
import com.advancia.Chat4Me_API_Gateway.generated.application.model.NewMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "messaging-feign-client", url = "${app.feign.clients.url}")
public interface MessagingServiceFeignClient {
    @GetMapping(value = "${app.feign.clients.api.getMessages}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<MessageDto>> getMessages(@RequestParam("token_sender") String tokenSender, @RequestParam("user_id_receiver") UUID receiver);

    @GetMapping(value = "${app.feign.clients.api.newMessage}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<MessageDto> newMessage(@RequestBody NewMessageDto newMessageDto);
}