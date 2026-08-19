package com.aurix.platform.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@SpringBootApplication(scanBasePackages = {
    "com.aurix.platform.accounts",
    "com.aurix.platform.shared"
})
@EntityScan(basePackages = {
    "com.aurix.platform.accounts",
    "com.aurix.platform.shared"
})
@EnableJpaRepositories(basePackages = {
    "com.aurix.platform.accounts.repository",
    "com.aurix.platform.shared.repository",
    "com.aurix.platform.shared.eventhub"
})
@EnableScheduling
@EnableCaching
public class AccountsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

    @Bean
    @Primary
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
