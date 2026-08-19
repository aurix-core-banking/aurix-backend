package com.aurix.platform.seguros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Clock;

@SpringBootApplication
@ComponentScan(basePackages = {"com.aurix.platform.seguros", "com.aurix.platform.shared"})
@EntityScan(basePackages = {"com.aurix.platform.seguros", "com.aurix.platform.shared"})
@EnableJpaRepositories(basePackages = {"com.aurix.platform.seguros", "com.aurix.platform.shared"})
public class SegurosApplication {
    public static void main(String[] args) {
        SpringApplication.run(SegurosApplication.class, args);
    }

    @Bean
    @Primary
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    @Profile("test")
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .build();
    }
}
