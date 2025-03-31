package com.advancia.chat4me_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Chat4MeApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(Chat4MeApiGatewayApplication.class, args);
    }
}