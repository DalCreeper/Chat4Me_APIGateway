package com.advancia.chat4me_api_gateway.application.api.feign;

import com.advancia.chat4me_api_gateway.generated.application.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "auth-feign-client", url = "${app.feign.clients.auth-service.url}")
public interface AuthServiceFeignClient {
    @PostMapping(value = "${app.feign.clients.auth-service.api.startLogin}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ChallengeResponseDto startLogin(@RequestBody LoginRequestDto loginRequestDto);

    @PostMapping(value = "${app.feign.clients.auth-service.api.verifyOTP}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    AuthTokenDto verifyOTP(@RequestBody OTPVerificationRequestDto otpVerificationRequestDto);

    @GetMapping(value = "${app.feign.clients.auth-service.api.validateToken}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void validateToken(@RequestBody TokenValidationRequestDto tokenValidationRequestDto);

    @PostMapping(value = "${app.feign.clients.auth-service.api.extractUUID}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserDto extractUUID(@RequestBody UserIdRequestDto userIdRequestDto);

    @PostMapping(value = "${app.feign.clients.auth-service.api.refreshToken}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    AuthTokenDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto);

    @GetMapping(value = "${app.feign.clients.auth-service.api.getUsers}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    List<UserDto> getUsers(@RequestParam("access-token") String accessToken);
}