package com.advancia.chat4me_api_gateway.application.api.feign;

import com.advancia.Chat4Me_API_Gateway.generated.application.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "auth-feign-client", url = "${app.feign.clients.url}")
public interface AuthServiceFeignClient {
    @GetMapping(value = "${app.feign.clients.api.startLogin}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ChallengeResponseDto> startLogin(@RequestBody LoginRequestDto loginRequestDto);

    @GetMapping(value = "${app.feign.clients.api.verifyOTP}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AuthTokenDto> verifyOTP(@RequestBody OTPVerificationRequestDto otpVerificationRequestDto);

    @GetMapping(value = "${app.feign.clients.api.validateToken}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Void> validateToken(@RequestBody TokenValidationRequestDto tokenValidationRequestDto);

    @GetMapping(value = "${app.feign.clients.api.refreshToken}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AuthTokenDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto);

    @GetMapping(value = "${app.feign.clients.api.getUsers}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<UserDto>> getUsers();
}