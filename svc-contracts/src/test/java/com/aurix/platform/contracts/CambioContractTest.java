package com.aurix.platform.contracts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Contract tests for svc-cambio (exchange operations).
 * Verifies API contract compliance through the gateway.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("svc-cambio Contract Tests")
class CambioContractTest {

    private static final String BASE_URL = System.getenv().getOrDefault("GATEWAY_URL", "http://localhost:8080");

    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("GET /api/cambio/cotacoes returns exchange rates")
    void shouldListExchangeRates() {
        given()
            .accept("application/json")
        .when()
            .get("/api/cambio/cotacoes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("GET /api/cambio/contratos returns exchange contracts")
    void shouldListExchangeContracts() {
        given()
            .accept("application/json")
        .when()
            .get("/api/cambio/contratos")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("GET /api/cambio/clientes returns exchange clients")
    void shouldListExchangeClients() {
        given()
            .accept("application/json")
        .when()
            .get("/api/cambio/clientes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("POST /api/cambio/remessas requires valid remittance request")
    void shouldValidateRemittanceRequest() {
        given()
            .contentType("application/json")
            .accept("application/json")
            .body("{}")
        .when()
            .post("/api/cambio/remessas")
        .then()
            .statusCode(anyOf(is(400), is(401)));
    }
}
