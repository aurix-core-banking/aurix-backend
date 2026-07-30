package com.aurix.platform.platform.service;

import com.aurix.platform.platform.client.CoreApiClient;
import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import com.aurix.platform.platform.entity.TokenOpenFinance;
import com.aurix.platform.platform.repository.ConsentimentoOpenFinanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenFinanceDataServiceTest {

    @Mock
    private CoreApiClient coreApiClient;

    @Mock
    private ConsentimentoOpenFinanceRepository consentimentoRepository;

    private OpenFinanceDataService service;

    @BeforeEach
    void setUp() {
        service = new OpenFinanceDataService(consentimentoRepository, coreApiClient);
    }

    @Test
    void shouldListCreditCardsForAuthorizedAccounts() {
        var token = new TokenOpenFinance();
        token.setConsentId("1");
        var consent = new ConsentimentoOpenFinance();
        consent.setPermissoes(List.of(ConsentimentoOpenFinance.TipoConsentimento.CREDIT_CARDS_ACCOUNTS.name()));
        consent.setContasAutorizadas(List.of(1L, 2L));
        when(coreApiClient.obterConsentimento("1")).thenReturn(consent);
        when(coreApiClient.getCreditCards(List.of("1", "2")))
            .thenReturn(List.of(Map.of("id", "card-1", "brand", "VISA")));

        List<Map<String, Object>> result = service.listarCartoesCreditoPorToken(token);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("VISA", result.get(0).get("brand"));
        verify(coreApiClient).getCreditCards(List.of("1", "2"));
    }

    @Test
    void shouldReturnEmptyWhenNoCreditCardsPermission() {
        var token = new TokenOpenFinance();
        token.setConsentId("2");
        var consent = new ConsentimentoOpenFinance();
        consent.setPermissoes(List.of(ConsentimentoOpenFinance.TipoConsentimento.ACCOUNTS.name()));
        consent.setContasAutorizadas(List.of(1L));
        when(coreApiClient.obterConsentimento("2")).thenReturn(consent);

        assertTrue(service.listarCartoesCreditoPorToken(token).isEmpty());
        verify(coreApiClient, never()).getCreditCards(anyList());
    }

    @Test
    void shouldListPersonalIdentifications() {
        var token = new TokenOpenFinance();
        token.setConsentId("3");
        token.setUserId(42L);
        var consent = new ConsentimentoOpenFinance();
        consent.setPermissoes(List.of(ConsentimentoOpenFinance.TipoConsentimento.CUSTOMERS_PERSONAL_IDENTIFICATIONS.name()));
        when(coreApiClient.obterConsentimento("3")).thenReturn(consent);
        when(coreApiClient.getPersonalIdentifications(42L)).thenReturn(Map.of("civilName", "João", "cpf", "12345678901"));

        List<Map<String, Object>> result = service.listarIdentificacoesPessoaisPorToken(token);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João", result.get(0).get("civilName"));
    }

    @Test
    void shouldReturnEmptyListWhenNoIdentificacaoPermission() {
        var token = new TokenOpenFinance();
        token.setConsentId("4");
        var consent = new ConsentimentoOpenFinance();
        consent.setPermissoes(List.of(ConsentimentoOpenFinance.TipoConsentimento.ACCOUNTS.name()));
        when(coreApiClient.obterConsentimento("4")).thenReturn(consent);

        List<Map<String, Object>> result = service.listarIdentificacoesPessoaisPorToken(token);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(coreApiClient, never()).getPersonalIdentifications(anyLong());
    }
}
