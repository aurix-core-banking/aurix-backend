package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.TokenOpenFinanceDTO;
import com.aurix.platform.platform.service.LogAcessoOpenFinanceService;
import com.aurix.platform.platform.service.OpenFinanceDataService;
import com.aurix.platform.platform.service.TokenOpenFinanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenFinanceControllerTest {

    @Mock
    private TokenOpenFinanceService tokenService;

    @Mock
    private LogAcessoOpenFinanceService logService;

    @Mock
    private OpenFinanceDataService dataService;

    @Mock
    private HttpServletRequest request;

    private OpenFinanceController controller;

    @BeforeEach
    void setUp() {
        controller = new OpenFinanceController(tokenService, logService, dataService);
    }

    @Test
    void shouldReturnCreditCardsList() {
        var tokenDTO = new TokenOpenFinanceDTO();
        tokenDTO.setConsentId("consent-1");
        tokenDTO.setClientId("client-1");
        tokenDTO.setUserId(42L);

        when(tokenService.validarToken(anyString())).thenReturn(tokenDTO);
        when(tokenService.verificarRateLimit(anyString())).thenReturn(true);
        when(dataService.listarCartoesCreditoPorToken(any())).thenReturn(List.of(Map.of("brand", "VISA")));

        ResponseEntity<Map<String, Object>> response = controller.listarCartoesCredito("Bearer test-token", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        var data = (Map<String, Object>) response.getBody().get("data");
        assertNotNull(data);
        var creditCards = (List<Map<String, Object>>) data.get("creditCards");
        assertNotNull(creditCards);
        assertEquals(1, creditCards.size());
        assertEquals("VISA", creditCards.get(0).get("brand"));
        assertTrue(response.getBody().containsKey("links"));
        assertTrue(response.getBody().containsKey("meta"));
    }

    @Test
    void shouldReturnPersonalIdentifications() {
        var tokenDTO = new TokenOpenFinanceDTO();
        tokenDTO.setConsentId("consent-2");
        tokenDTO.setClientId("client-2");
        tokenDTO.setUserId(42L);

        when(tokenService.validarToken(anyString())).thenReturn(tokenDTO);
        when(tokenService.verificarRateLimit(anyString())).thenReturn(true);
        when(dataService.listarIdentificacoesPessoaisPorToken(any())).thenReturn(List.of(Map.of("civilName", "João")));

        ResponseEntity<Map<String, Object>> response = controller.listarIdentificacoesPessoais("Bearer test-token", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        var data = (Map<String, Object>) response.getBody().get("data");
        assertNotNull(data);
        var identifications = (List<Map<String, Object>>) data.get("personalIdentifications");
        assertNotNull(identifications);
        assertEquals(1, identifications.size());
        assertEquals("João", identifications.get(0).get("civilName"));
        assertTrue(response.getBody().containsKey("links"));
        assertTrue(response.getBody().containsKey("meta"));
    }
}
