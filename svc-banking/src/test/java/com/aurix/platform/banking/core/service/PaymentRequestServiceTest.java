package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.PaymentRequestDTO;
import com.aurix.platform.banking.core.entity.PaymentRequest;
import com.aurix.platform.banking.core.repository.PaymentRequestRepository;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRequestServiceTest {

    @Mock
    private PaymentRequestRepository repository;

    @Mock
    private TransacaoService transacaoService;

    private PaymentRequestService service;

    @BeforeEach
    void setUp() {
        service = new PaymentRequestService(repository, transacaoService);
    }

    private PaymentRequestDTO createDTO(BigDecimal amount, Long requesterId) {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setAmount(amount);
        dto.setDescription("Test payment");
        dto.setRequesterId(requesterId);
        dto.setRequesterAccountNumber("12345-6");
        return dto;
    }

    private PaymentRequest createPendingEntity(Long id, String token, BigDecimal amount, Long requesterId) {
        PaymentRequest entity = new PaymentRequest();
        entity.setId(id);
        entity.setToken(token);
        entity.setAmount(amount);
        entity.setDescription("Test payment");
        entity.setRequesterId(requesterId);
        entity.setRequesterAccountNumber("12345-6");
        entity.setStatus(PaymentRequest.PaymentRequestStatus.PENDING);
        entity.setExpiresAt(LocalDateTime.now().plusHours(24));
        return entity;
    }

    @Test
    void criar_deveSalvarERetornarDTO() {
        PaymentRequestDTO dto = createDTO(BigDecimal.valueOf(500), 1L);

        when(repository.save(any(PaymentRequest.class))).thenAnswer(i -> {
            PaymentRequest saved = i.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        PaymentRequestDTO result = service.criar(dto);

        assertNotNull(result);
        assertNotNull(result.getToken());
        assertEquals(16, result.getToken().length());
        assertEquals(BigDecimal.valueOf(500), result.getAmount());
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void buscarPorToken_deveRetornarDTO() {
        PaymentRequest entity = createPendingEntity(1L, "abc123token", BigDecimal.valueOf(250), 1L);
        when(repository.findByToken("abc123token")).thenReturn(Optional.of(entity));

        Optional<PaymentRequestDTO> result = service.buscarPorToken("abc123token");

        assertTrue(result.isPresent());
        assertEquals("abc123token", result.get().getToken());
    }

    @Test
    void buscarPorToken_deveRetornarEmpty() {
        when(repository.findByToken("invalid")).thenReturn(Optional.empty());
        assertTrue(service.buscarPorToken("invalid").isEmpty());
    }

    @Test
    void buscarPorId_deveRetornarDTO() {
        PaymentRequest entity = createPendingEntity(99L, "token99", BigDecimal.valueOf(300), 2L);
        when(repository.findById(99L)).thenReturn(Optional.of(entity));

        Optional<PaymentRequestDTO> result = service.buscarPorId(99L);

        assertTrue(result.isPresent());
        assertEquals(99L, result.get().getId());
    }

    @Test
    void listarPorRequerente_deveRetornarLista() {
        when(repository.findByRequesterIdOrderByDataCriacaoDesc(1L))
            .thenReturn(List.of(createPendingEntity(1L, "t1", BigDecimal.valueOf(100), 1L),
                                createPendingEntity(2L, "t2", BigDecimal.valueOf(200), 1L)));

        List<PaymentRequestDTO> result = service.listarPorRequerente(1L);

        assertEquals(2, result.size());
    }

    @Test
    void pagarPorToken_deveProcessarPagamento() {
        PaymentRequest entity = createPendingEntity(1L, "paytoken", BigDecimal.valueOf(500), 1L);
        when(repository.findByToken("paytoken")).thenReturn(Optional.of(entity));

        TransacaoDTO txResult = new TransacaoDTO();
        txResult.setId(100L);
        when(transacaoService.criar(any(TransacaoDTO.class))).thenReturn(txResult);

        when(repository.save(any(PaymentRequest.class))).thenAnswer(i -> i.getArgument(0));

        PaymentRequestDTO result = service.pagarPorToken("paytoken", 2L, "67890-1", 10L);

        assertEquals("PAID", result.getStatus());
        assertNotNull(result.getPaidAt());
        assertEquals(100L, result.getTransactionId());

        verify(transacaoService).criar(argThat(dto ->
            dto.getContaOrigemId() == 10L &&
            dto.getTipoTransacao() == Transacao.TipoTransacao.PIX &&
            dto.getValor().compareTo(BigDecimal.valueOf(500)) == 0
        ));
    }

    @Test
    void pagarPorToken_tokenInvalido_deveLancarExcecao() {
        when(repository.findByToken("invalid")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
            () -> service.pagarPorToken("invalid", 2L, "67890-1", 10L));
    }

    @Test
    void pagarPorToken_jaPago_deveLancarExcecao() {
        PaymentRequest entity = createPendingEntity(1L, "done", BigDecimal.valueOf(100), 1L);
        entity.setStatus(PaymentRequest.PaymentRequestStatus.PAID);
        when(repository.findByToken("done")).thenReturn(Optional.of(entity));

        assertThrows(IllegalStateException.class,
            () -> service.pagarPorToken("done", 2L, "67890-1", 10L));
    }

    @Test
    void cancelar_deveMudarStatus() {
        PaymentRequest entity = createPendingEntity(1L, "cancelme", BigDecimal.valueOf(100), 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(PaymentRequest.class))).thenAnswer(i -> i.getArgument(0));

        PaymentRequestDTO result = service.cancelar(1L, 1L);

        assertEquals("CANCELLED", result.getStatus());
    }

    @Test
    void cancelar_requesterDiferente_deveLancarExcecao() {
        PaymentRequest entity = createPendingEntity(1L, "mine", BigDecimal.valueOf(100), 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        assertThrows(IllegalArgumentException.class,
            () -> service.cancelar(1L, 999L));
    }

    @Test
    void expirarVencidas_deveProcessarExpiradas() {
        PaymentRequest e1 = createPendingEntity(1L, "exp1", BigDecimal.valueOf(100), 1L);
        e1.setExpiresAt(LocalDateTime.now().minusHours(1));
        PaymentRequest e2 = createPendingEntity(2L, "exp2", BigDecimal.valueOf(200), 2L);
        e2.setExpiresAt(LocalDateTime.now().minusMinutes(30));

        when(repository.findByStatusAndExpiresAtBefore(eq(PaymentRequest.PaymentRequestStatus.PENDING), any()))
            .thenReturn(List.of(e1, e2));

        int count = service.expirarVencidas();

        assertEquals(2, count);
        verify(repository, times(2)).save(any(PaymentRequest.class));
    }
}
