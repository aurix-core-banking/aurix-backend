package com.aurix.platform.payments.pix.service;

import com.aurix.platform.payments.pix.client.PixBacenClient;
import com.aurix.platform.payments.pix.client.dto.SpiResult;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.repository.PixChaveRepository;
import com.aurix.platform.shared.repository.PixTransferenciaRepository;
import com.aurix.platform.shared.dto.PixTransferenciaDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.PixChave;
import com.aurix.platform.shared.entity.PixTransferencia;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.aurix.platform.shared.exception.SaldoInsuficienteException;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PixTransferenciaServiceTest {

    @Mock
    private PixTransferenciaRepository pixTransferenciaRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private PixChaveRepository pixChaveRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private PixBacenClient pixBacenClient;

    @InjectMocks
    private PixTransferenciaService pixTransferenciaService;

    private PixTransferencia pixTransferencia;
    private PixTransferenciaDTO pixTransferenciaDTO;
    private Conta contaOrigem;

    @BeforeEach
    void setUp() {
        contaOrigem = new Conta();
        contaOrigem.setId(1L);
        contaOrigem.setTenantId(TenantContext.getTenantId());

        pixTransferencia = new PixTransferencia();
        pixTransferencia.setId(1L);
        pixTransferencia.setCodigoPix("PIX-123");
        pixTransferencia.setValor(BigDecimal.valueOf(100.00));
        pixTransferencia.setStatus(PixTransferencia.StatusPix.PENDENTE);
        pixTransferencia.setContaOrigem(contaOrigem);
        pixTransferencia.setChavePixDestino("chave@teste.com");

        pixTransferenciaDTO = new PixTransferenciaDTO();
        pixTransferenciaDTO.setContaOrigemId(1L);
        pixTransferenciaDTO.setChavePixDestino("chave@teste.com");
        pixTransferenciaDTO.setValor(BigDecimal.valueOf(100.00));
        pixTransferenciaDTO.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);
    }

    @Test
    void testCriarTransferenciaPix_Sucesso() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(new Conta()));
        when(pixTransferenciaRepository.save(any(PixTransferencia.class))).thenReturn(pixTransferencia);

        PixTransferenciaDTO resultado = pixTransferenciaService.criarTransferenciaPix(pixTransferenciaDTO);

        assertNotNull(resultado);
        assertEquals("PIX-123", resultado.getCodigoPix());
        verify(contaRepository).findById(1L);
        verify(pixTransferenciaRepository).save(any(PixTransferencia.class));
    }

    @Test
    void testProcessarTransferencia_Sucesso_DestinoExterno() {
        SpiResult spiResult = new SpiResult();
        spiResult.setSucesso(true);
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));
        when(contaRepository.debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor())))
                .thenReturn(1);
        when(pixChaveRepository.findChaveAtivaByChavePix("chave@teste.com")).thenReturn(Optional.empty());
        when(pixBacenClient.enviarPix(any())).thenReturn(spiResult);

        pixTransferenciaService.processarTransferencia(1L);

        assertEquals(PixTransferencia.StatusPix.PROCESSADA, pixTransferencia.getStatus());
        verify(contaRepository).debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor()));
        verify(contaRepository, never()).creditarSaldoAtomico(anyString(), any(), any());
        verify(pixTransferenciaRepository).save(pixTransferencia);
        verify(eventPublisher).publicarTransacaoRealizada(any(TransacaoEvent.class));
    }

    @Test
    void testProcessarTransferencia_Sucesso_DestinoLocal() {
        Conta contaDestino = new Conta();
        contaDestino.setId(2L);
        PixChave chaveDestino = new PixChave();
        chaveDestino.setConta(contaDestino);

        SpiResult spiResult = new SpiResult();
        spiResult.setSucesso(true);
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));
        when(contaRepository.debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor())))
                .thenReturn(1);
        when(pixChaveRepository.findChaveAtivaByChavePix("chave@teste.com")).thenReturn(Optional.of(chaveDestino));
        when(pixBacenClient.enviarPix(any())).thenReturn(spiResult);

        pixTransferenciaService.processarTransferencia(1L);

        assertEquals(PixTransferencia.StatusPix.PROCESSADA, pixTransferencia.getStatus());
        verify(contaRepository).creditarSaldoAtomico(anyString(), eq(2L), eq(pixTransferencia.getValor()));
    }

    @Test
    void shouldCallSpiAndProcessWhenSpiSucceeds() {
        SpiResult spiResult = new SpiResult();
        spiResult.setSucesso(true);
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));
        when(contaRepository.debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor())))
                .thenReturn(1);
        when(pixChaveRepository.findChaveAtivaByChavePix("chave@teste.com")).thenReturn(Optional.empty());
        when(pixBacenClient.enviarPix(any())).thenReturn(spiResult);

        pixTransferenciaService.processarTransferencia(1L);

        assertEquals(PixTransferencia.StatusPix.PROCESSADA, pixTransferencia.getStatus());
        assertNotNull(pixTransferencia.getCodigoPix());
        assertTrue(pixTransferencia.getCodigoPix().startsWith("E"));
        verify(pixBacenClient).enviarPix(any());
    }

    @Test
    void shouldCompensateWhenSpiFails() {
        SpiResult spiResult = new SpiResult();
        spiResult.setSucesso(false);
        spiResult.setMensagem("Fundo insuficiente na conta destino");
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));
        when(contaRepository.debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor())))
                .thenReturn(1);
        when(pixBacenClient.enviarPix(any())).thenReturn(spiResult);

        assertThrows(IllegalStateException.class, () -> pixTransferenciaService.processarTransferencia(1L));

        assertEquals(PixTransferencia.StatusPix.FALHADA, pixTransferencia.getStatus());
        verify(contaRepository).creditarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor()));
        verify(pixTransferenciaRepository).save(pixTransferencia);
    }

    @Test
    void shouldCompensateWhenSpiThrowsException() {
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));
        when(contaRepository.debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor())))
                .thenReturn(1);
        when(pixBacenClient.enviarPix(any())).thenThrow(new RuntimeException("Timeout"));

        assertThrows(IllegalStateException.class, () -> pixTransferenciaService.processarTransferencia(1L));

        assertEquals(PixTransferencia.StatusPix.FALHADA, pixTransferencia.getStatus());
        verify(contaRepository).creditarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor()));
        verify(pixTransferenciaRepository).save(pixTransferencia);
    }

    @Test
    void testProcessarTransferencia_SaldoInsuficiente() {
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));
        when(contaRepository.debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor())))
                .thenReturn(0);
        // A conta existe (mesmo tenant) — só o saldo é insuficiente. Sem isto, a
        // desambiguação em processarTransferencia concluiria "conta não encontrada"
        // (o default do Mockito para Optional<Conta> não-stubado é Optional.empty()).
        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaOrigem));

        assertThrows(SaldoInsuficienteException.class, () -> pixTransferenciaService.processarTransferencia(1L));

        assertEquals(PixTransferencia.StatusPix.FALHADA, pixTransferencia.getStatus());
        verify(contaRepository, never()).creditarSaldoAtomico(anyString(), any(), any());
        verify(pixTransferenciaRepository).save(pixTransferencia);
    }

    @Test
    void testProcessarTransferencia_ContaOrigemNaoEncontradaParaTenant() {
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));
        when(contaRepository.debitarSaldoAtomico(anyString(), eq(1L), eq(pixTransferencia.getValor())))
                .thenReturn(0);
        when(contaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> pixTransferenciaService.processarTransferencia(1L));

        assertEquals(PixTransferencia.StatusPix.FALHADA, pixTransferencia.getStatus());
        verify(contaRepository, never()).creditarSaldoAtomico(anyString(), any(), any());
    }

    @Test
    void testProcessarTransferencia_ErroJaProcessada() {
        pixTransferencia.setStatus(PixTransferencia.StatusPix.PROCESSADA);
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));

        assertThrows(IllegalStateException.class, () -> pixTransferenciaService.processarTransferencia(1L));

        verify(pixTransferenciaRepository, never()).save(any());
    }

    @Test
    void testCancelarTransferencia_Sucesso() {
        when(pixTransferenciaRepository.findById(1L)).thenReturn(Optional.of(pixTransferencia));

        pixTransferenciaService.cancelarTransferencia(1L);

        assertEquals(PixTransferencia.StatusPix.CANCELADA, pixTransferencia.getStatus());
        verify(pixTransferenciaRepository).save(pixTransferencia);
    }
}
