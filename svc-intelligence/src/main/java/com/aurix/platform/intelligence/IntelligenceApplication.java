package com.aurix.platform.intelligence;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaRepositories
@EnableKafka
@EnableCaching
@EntityScan(basePackages = {"com.aurix.platform.intelligence.entity", "com.aurix.platform.shared.entity"})
@OpenAPIDefinition(
    info = @Info(
        title = "Aurix Intelligence API",
        version = "1.0.0",
        description = "API para BI, Analytics, ML, Chatbot e Credit Score",
        contact = @Contact(
            name = "Aurix Platform Team",
            email = "dev@aurix.platform",
            url = "https://aurix.platform"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8091/api/intelligence", description = "Servidor de Desenvolvimento"),
        @Server(url = "https://api.aurix.platform/intelligence", description = "Servidor de Produção")
    }
)
public class IntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligenceApplication.class, args);
    }
}
