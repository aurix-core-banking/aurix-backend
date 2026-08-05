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
 * Cross-service integration contract tests.
 * Verifies that services can communicate through the gateway.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Cross-Service Integration Contracts")
class CrossServiceContractTest {

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
    @DisplayName("Gateway routes /api/core to svc-banking")
    void shouldRouteCoreToBanking() {
        given()
            .accept("application/json")
        .when()
            .get("/api/core/clientes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/pix to svc-payments")
    void shouldRoutePixToPayments() {
        given()
            .accept("application/json")
        .when()
            .get("/api/pix/chaves")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/credit to svc-credit")
    void shouldRouteCreditToCredit() {
        given()
            .accept("application/json")
        .when()
            .get("/api/credit/solicitacoes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/cambio to svc-cambio")
    void shouldRouteCambioToCambio() {
        given()
            .accept("application/json")
        .when()
            .get("/api/cambio/cotacoes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/cards to svc-cards")
    void shouldRouteCardsToCards() {
        given()
            .accept("application/json")
        .when()
            .get("/api/cards")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/finance to svc-finance-mgmt")
    void shouldRouteFinanceToFinanceMgmt() {
        given()
            .accept("application/json")
        .when()
            .get("/api/finance/impostos")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/intelligence to svc-intelligence")
    void shouldRouteIntelligenceToIntelligence() {
        given()
            .accept("application/json")
        .when()
            .get("/api/intelligence/bi")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/platform to svc-platform")
    void shouldRoutePlatformToPlatform() {
        given()
            .accept("application/json")
        .when()
            .get("/api/platform/openfinance")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/fraud to svc-fraud")
    void shouldRouteFraudToFraud() {
        given()
            .accept("application/json")
        .when()
            .get("/api/fraud/fraudes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/compliance to svc-compliance")
    void shouldRouteComplianceToCompliance() {
        given()
            .accept("application/json")
        .when()
            .get("/api/compliance/regulacoes")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Gateway routes /api/ai to svc-ai")
    void shouldRouteAiToAi() {
        given()
            .accept("application/json")
        .when()
            .get("/api/ai/health")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("All services expose actuator health endpoint")
    void shouldExposeHealthEndpoints() {
        String[] services = {
            "svc-banking:8200",
            "svc-payments:8201",
            "svc-credit:8082",
            "svc-customer:8083",
            "svc-cambio:8093",
            "svc-cards:8094",
            "svc-finance-mgmt:8089",
            "svc-intelligence:8091",
            "svc-platform:8092",
            "svc-compliance:8205",
            "svc-fraud:8207",
            "svc-ai:8206"
        };

        for (String service : services) {
            String[] parts = service.split(":");
            String host = parts[0];
            int port = Integer.parseInt(parts[1]);

            given()
                .baseUri("http://" + host)
                .port(port)
                .accept("application/json")
            .when()
                .get("/actuator/health")
            .then()
                .statusCode(200);
        }
    }
}
