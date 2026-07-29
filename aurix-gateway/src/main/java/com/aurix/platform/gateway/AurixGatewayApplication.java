package com.aurix.platform.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AurixGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AurixGatewayApplication.class, args);
    }
}
