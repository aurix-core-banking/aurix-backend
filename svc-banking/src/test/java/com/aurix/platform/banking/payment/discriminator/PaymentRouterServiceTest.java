package com.aurix.platform.banking.payment.discriminator;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.banking.core.repository.ProcessingCodeRepository;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRouterServiceTest {

    @Mock
    private ProcessingCodeRepository repository;

    private PaymentRouterService service;
    private List<PaymentDiscriminator> discriminators;

    private ProcessingCode pixCode;
    private ProcessingCode tedCode;
    private ProcessingCode docCode;
    private ProcessingCode inactiveCode;

    @BeforeEach
    void setUp() {
        pixCode = new ProcessingCode();
        pixCode.setId(1L); pixCode.setCode("PIX01"); pixCode.setPaymentType("PIX"); pixCode.setActive(true); pixCode.setPriority(1);

        tedCode = new ProcessingCode();
        tedCode.setId(2L); tedCode.setCode("TED01"); tedCode.setPaymentType("TED"); tedCode.setActive(true); tedCode.setPriority(1);

        docCode = new ProcessingCode();
        docCode.setId(3L); docCode.setCode("DOC01"); docCode.setPaymentType("DOC"); docCode.setActive(true); docCode.setPriority(1);

        inactiveCode = new ProcessingCode();
        inactiveCode.setId(4L); inactiveCode.setCode("INACTIVE"); inactiveCode.setPaymentType("PIX"); inactiveCode.setActive(false); inactiveCode.setPriority(1);

        discriminators = List.of(new PixDiscriminator(), new TedDiscriminator(), new DocDiscriminator());
        service = new PaymentRouterService(repository, discriminators);
    }

    private TransacaoDTO createPixDTO() {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(10L);
        dto.setValor(BigDecimal.valueOf(100));
        dto.setTipoTransacao(Transacao.TipoTransacao.PIX);
        return dto;
    }

    @Test
    void route_comPix_deveRetornarOk() {
        when(repository.findByCode("PIX01")).thenReturn(Optional.of(pixCode));

        PaymentDiscriminator.PaymentResult result = service.route("PIX01", createPixDTO());

        assertTrue(result.isSuccess());
        assertTrue(result.getTransactionCode().startsWith("PIX-"));
    }

    @Test
    void route_comTed_deveRetornarOk() {
        when(repository.findByCode("TED01")).thenReturn(Optional.of(tedCode));

        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(10L);
        dto.setContaDestinoId(20L);
        dto.setValor(BigDecimal.valueOf(500));
        dto.setTipoTransacao(Transacao.TipoTransacao.TED);

        PaymentDiscriminator.PaymentResult result = service.route("TED01", dto);

        assertTrue(result.isSuccess());
        assertTrue(result.getTransactionCode().startsWith("TED-"));
    }

    @Test
    void route_comDocDentroDoLimite_deveRetornarOk() {
        when(repository.findByCode("DOC01")).thenReturn(Optional.of(docCode));

        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(10L);
        dto.setContaDestinoId(20L);
        dto.setValor(BigDecimal.valueOf(3000));
        dto.setTipoTransacao(Transacao.TipoTransacao.DOC);

        PaymentDiscriminator.PaymentResult result = service.route("DOC01", dto);

        assertTrue(result.isSuccess());
    }

    @Test
    void route_comDocAcimaDoLimite_deveFalhar() {
        when(repository.findByCode("DOC01")).thenReturn(Optional.of(docCode));

        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(10L);
        dto.setContaDestinoId(20L);
        dto.setValor(BigDecimal.valueOf(10000));
        dto.setTipoTransacao(Transacao.TipoTransacao.DOC);

        PaymentDiscriminator.PaymentResult result = service.route("DOC01", dto);

        assertFalse(result.isSuccess());
    }

    @Test
    void route_comPixSemValor_deveFalhar() {
        when(repository.findByCode("PIX01")).thenReturn(Optional.of(pixCode));

        TransacaoDTO dto = createPixDTO();
        dto.setValor(BigDecimal.ZERO);

        PaymentDiscriminator.PaymentResult result = service.route("PIX01", dto);

        assertFalse(result.isSuccess());
    }

    @Test
    void route_comCodigoInativo_deveFalhar() {
        when(repository.findByCode("INACTIVE")).thenReturn(Optional.of(inactiveCode));

        PaymentDiscriminator.PaymentResult result = service.route("INACTIVE", createPixDTO());

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("inactive"));
    }

    @Test
    void route_comCodigoInexistente_deveLancarExcecao() {
        when(repository.findByCode("XXXX")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
            () -> service.route("XXXX", createPixDTO()));
    }

    @Test
    void routeAuto_deveEncontrarDiscriminadorPorTipo() {
        when(repository.findByPaymentTypeAndActiveTrueOrderByPriorityAsc("PIX"))
            .thenReturn(List.of(pixCode));

        PaymentDiscriminator.PaymentResult result = service.routeAuto(createPixDTO());

        assertTrue(result.isSuccess());
    }

    @Test
    void routeAuto_semCodigoAtivo_deveFalhar() {
        when(repository.findByPaymentTypeAndActiveTrueOrderByPriorityAsc("PIX"))
            .thenReturn(List.of());

        PaymentDiscriminator.PaymentResult result = service.routeAuto(createPixDTO());

        assertFalse(result.isSuccess());
    }
}
