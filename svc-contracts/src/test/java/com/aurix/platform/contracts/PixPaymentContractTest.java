package com.aurix.platform.contracts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;

/**
 * Contract tests for svc-payments (PIX operations).
 * Verifies API contract compliance through the gateway.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("svc-payments PIX Contract Tests")
class PixPaymentContractTest {

    private static final String BASE_URL = System.getenv().getOrDefault("GATEWAY_URL", "http://localhost:8080");

    @BeforeAll
    void setup() {
        assumeTrue(gatewayAlive(BASE_URL), "Gateway indisponivel em " + BASE_URL + " - teste ignorado");
        RestAssured.baseURI = BASE_URL;
    }

    private static boolean gatewayAlive(String url) {
        try {
            URI uri = URI.create(url);
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(uri.getHost(), uri.getPort()), 1500);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    @DisplayName("GET /api/pix/chaves returns list of PIX keys")
    void shouldListPixKeys() {
        given()
            .accept("application/json")
        .when()
            .get("/api/pix/chaves")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("$", isA(java.util.List.class));
    }

    @Test
    @DisplayName("POST /api/pix/transferencias requires valid request body")
    void shouldValidatePixTransferRequest() {
        given()
            .contentType("application/json")
            .accept("application/json")
            .body("{}")
        .when()
            .post("/api/pix/transferencias")
        .then()
            .statusCode(anyOf(is(400), is(401)));
    }

    @Test
    @DisplayName("GET /api/pix/qr returns QR code generation endpoint")
    void shouldExposeQrPixEndpoint() {
        given()
            .accept("application/json")
        .when()
            .get("/api/pix/qr")
        .then()
            .statusCode(anyOf(is(200), is(400), is(401)));
    }
}
