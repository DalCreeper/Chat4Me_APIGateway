package com.advancia.chat4me_api_gateway.application.api.feign;

import com.advancia.chat4me_api_gateway.generated.application.model.MessageDto;
import com.advancia.chat4me_api_gateway.generated.application.model.NewMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "messaging-feign-client", url = "${app.feign.clients.messaging-service.url}")
public interface MessagingServiceFeignClient {
    @GetMapping(value = "${app.feign.clients.messaging-service.api.getMessages}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    List<MessageDto> getMessages(@RequestParam("user_id_sender") UUID sender, @RequestParam("user_id_receiver") UUID receiver);

    @PostMapping(value = "${app.feign.clients.messaging-service.api.newMessage}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    MessageDto newMessage(@RequestBody NewMessageDto newMessageDto);
}