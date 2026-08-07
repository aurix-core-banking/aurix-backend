package com.aurix.platform.gateway.integration;

import com.aurix.platform.gateway.AurixGatewayApplication;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AurixGatewayApplication.class,
    properties = {
        "spring.main.web-application-type=reactive",
        "spring.autoconfigure.exclude=" +
            "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
            "org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration," +
            "org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration," +
            "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration",
        "aurix.security.jwt.enabled=true",
        "aurix.security.jwt.secret=minha-chave-secreta-super-secreta-para-testes-2026",
        "aurix.gateway.rate-limit.enabled=false"
    }
)
@ActiveProfiles("test")
class JwtSecurityTest {

    private static final String SEGREDO = "minha-chave-secreta-super-secreta-para-testes-2026";

    @LocalServerPort
    private int port;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void deveExigirTokenJwt() {
        client.get().uri("/api/gateway/sandbox")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void deveAceitarTokenJwtValido() throws Exception {
        String token = gerarTokenJwt();
        client.get().uri("/api/gateway/sandbox")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sandbox").isEqualTo(true);
    }

    @Test
    void deveRejeitarTokenJwtInvalido() {
        client.get().uri("/api/gateway/sandbox")
                .header("Authorization", "Bearer token-invalido")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    private String gerarTokenJwt() throws Exception {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("cliente-1")
                .issuer("aurix-gateway-test")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3_600_000L))
                .build();
        SignedJWT jwt = new SignedJWT(header, claims);
        jwt.sign(new MACSigner(SEGREDO.getBytes(StandardCharsets.UTF_8)));
        return jwt.serialize();
    }
}
