package com.aurix.platform.customer.kyc.service;

import com.aurix.platform.customer.kyc.entity.SolicitacaoKYC;
import com.aurix.platform.customer.kyc.repository.DocumentoKycRepository;
import com.aurix.platform.customer.kyc.repository.ScoreKycRepository;
import com.aurix.platform.customer.kyc.repository.SolicitacaoKycRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitacaoKycServiceTest {
    @Mock private SolicitacaoKycRepository solicitacaoRepository;
    @Mock private DocumentoKycRepository documentoRepository;
    @Mock private ScoreKycRepository scoreRepository;
    @Mock private KycProducer kycProducer;
    @InjectMocks private SolicitacaoKycService solicitacaoKycService;

    @Test
    void deveCriarSolicitacaoComStatusPendente() {
        when(solicitacaoRepository.save(any())).thenAnswer(inv -> {
            SolicitacaoKYC saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        SolicitacaoKYC resultado = solicitacaoKycService.criarSolicitacao(10L);

        assertEquals("PENDENTE", resultado.getStatus());
        assertEquals(10L, resultado.getClienteId());
        assertNotNull(resultado.getDataSolicitacao());
    }

    @Test
    void deveLancarExcecaoQuandoSolicitacaoNaoEncontrada() {
        when(solicitacaoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> solicitacaoKycService.buscarPorId(99L));
    }
}
