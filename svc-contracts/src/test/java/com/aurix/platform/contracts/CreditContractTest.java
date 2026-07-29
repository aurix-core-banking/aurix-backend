package com.aurix.platform.contracts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Contract tests for svc-credit (credit operations).
 * Verifies API contract compliance through the gateway.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("svc-credit Contract Tests")
class CreditContractTest {

    private static final String BASE_URL = System.getenv().getOrDefault("GATEWAY_URL", "http://localhost:8080");

    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("GET /api/credit/solicitacoes returns list of credit requests")
    void shouldListCreditRequests() {
        given()
            .accept("application/json")
        .when()
            .get("/api/credit/solicitacoes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("POST /api/credit/simulador requires valid simulation request")
    void shouldValidateSimulationRequest() {
        given()
            .contentType("application/json")
            .accept("application/json")
            .body("{}")
        .when()
            .post("/api/credit/simulador")
        .then()
            .statusCode(anyOf(is(400), is(401)));
    }

    @Test
    @DisplayName("GET /api/consignado/consignados returns consigned credit list")
    void shouldListConsignedCredits() {
        given()
            .accept("application/json")
        .when()
            .get("/api/consignado/consignados")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("GET /api/financiamento/contratos returns financing contracts")
    void shouldListFinancingContracts() {
        given()
            .accept("application/json")
        .when()
            .get("/api/financiamento/contratos")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }
}
