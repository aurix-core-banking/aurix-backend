package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.AccountApplicationDTO;
import com.aurix.platform.banking.core.entity.AccountApplication;
import com.aurix.platform.banking.core.repository.AccountApplicationRepository;
import com.aurix.platform.shared.dto.ContaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountApplicationServiceTest {

    @Mock
    private AccountApplicationRepository repository;

    @Mock
    private ContaService contaService;

    private AccountApplicationService service;

    @BeforeEach
    void setUp() {
        service = new AccountApplicationService(repository, contaService);
    }

    private AccountApplicationDTO createDTO(Long clienteId, String name, String cpf) {
        AccountApplicationDTO dto = new AccountApplicationDTO();
        dto.setClienteId(clienteId);
        dto.setCustomerName(name);
        dto.setCpfCnpj(cpf);
        dto.setEmail("test@example.com");
        dto.setAccountType("CORRENTE");
        return dto;
    }

    private AccountApplication createEntity(Long id, Long clienteId, String status) {
        AccountApplication entity = new AccountApplication();
        entity.setId(id);
        entity.setClienteId(clienteId);
        entity.setCustomerName("Test User");
        entity.setCpfCnpj("12345678901");
        entity.setAccountType("CORRENTE");
        entity.setStatus(AccountApplication.AccountApplicationStatus.valueOf(status));
        return entity;
    }

    @Test
    void criar_deveSalvarComoDraft() {
        AccountApplicationDTO dto = createDTO(1L, "João", "12345678901");
        when(repository.save(any(AccountApplication.class))).thenAnswer(i -> {
            AccountApplication e = i.getArgument(0);
            e.setId(1L);
            return e;
        });

        AccountApplicationDTO result = service.criar(dto);

        assertNotNull(result);
        assertEquals("DRAFT", result.getStatus());
        assertEquals("João", result.getCustomerName());
    }

    @Test
    void buscarPorId_deveRetornarDTO() {
        AccountApplication entity = createEntity(99L, 1L, "DRAFT");
        when(repository.findById(99L)).thenReturn(Optional.of(entity));

        Optional<AccountApplicationDTO> result = service.buscarPorId(99L);

        assertTrue(result.isPresent());
        assertEquals(99L, result.get().getId());
    }

    @Test
    void listarPorCliente_deveRetornarLista() {
        when(repository.findByClienteIdOrderByDataCriacaoDesc(1L))
            .thenReturn(List.of(createEntity(1L, 1L, "DRAFT"), createEntity(2L, 1L, "SUBMITTED")));

        List<AccountApplicationDTO> result = service.listarPorCliente(1L);

        assertEquals(2, result.size());
    }

    @Test
    void submit_deveMudarParaSubmitted() {
        AccountApplication entity = createEntity(1L, 1L, "DRAFT");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(AccountApplication.class))).thenAnswer(i -> i.getArgument(0));

        AccountApplicationDTO result = service.submit(1L);

        assertEquals("SUBMITTED", result.getStatus());
        assertNotNull(result.getSubmittedAt());
    }

    @Test
    void submit_devePermitirDePendingDocuments() {
        AccountApplication entity = createEntity(2L, 1L, "PENDING_DOCUMENTS");
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        when(repository.save(any(AccountApplication.class))).thenAnswer(i -> i.getArgument(0));

        AccountApplicationDTO result = service.submit(2L);

        assertEquals("SUBMITTED", result.getStatus());
    }

    @Test
    void submit_comStatusInvalido_deveLancarExcecao() {
        AccountApplication entity = createEntity(3L, 1L, "APPROVED");
        when(repository.findById(3L)).thenReturn(Optional.of(entity));

        assertThrows(IllegalStateException.class, () -> service.submit(3L));
    }

    @Test
    void requestDocuments_deveMudarParaPendingDocuments() {
        AccountApplication entity = createEntity(1L, 1L, "SUBMITTED");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(AccountApplication.class))).thenAnswer(i -> i.getArgument(0));

        AccountApplicationDTO result = service.requestDocuments(1L, "Please upload ID");

        assertEquals("PENDING_DOCUMENTS", result.getStatus());
        assertEquals("Please upload ID", result.getReviewNotes());
    }

    @Test
    void startReview_deveMudarParaUnderReview() {
        AccountApplication entity = createEntity(1L, 1L, "SUBMITTED");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(AccountApplication.class))).thenAnswer(i -> i.getArgument(0));

        AccountApplicationDTO result = service.startReview(1L, 100L);

        assertEquals("UNDER_REVIEW", result.getStatus());
        assertEquals(100L, result.getReviewerId());
    }

    @Test
    void approve_deveCriarContaEMudarStatus() {
        AccountApplication entity = createEntity(1L, 1L, "UNDER_REVIEW");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(500L);
        when(contaService.criarConta(any(ContaDTO.class))).thenReturn(contaDTO);
        when(repository.save(any(AccountApplication.class))).thenAnswer(i -> i.getArgument(0));

        AccountApplicationDTO result = service.approve(1L, "Approved", 100L);

        assertEquals("APPROVED", result.getStatus());
        assertEquals(500L, result.getAccountId());
        assertNotNull(result.getReviewedAt());
        verify(contaService).criarConta(any(ContaDTO.class));
    }

    @Test
    void reject_deveMudarParaRejected() {
        AccountApplication entity = createEntity(1L, 1L, "UNDER_REVIEW");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(AccountApplication.class))).thenAnswer(i -> i.getArgument(0));

        AccountApplicationDTO result = service.reject(1L, "Insufficient documents", 100L);

        assertEquals("REJECTED", result.getStatus());
        assertEquals("Insufficient documents", result.getReviewNotes());
        assertEquals(100L, result.getReviewerId());
    }

    @Test
    void approve_comStatusInvalido_deveLancarExcecao() {
        AccountApplication entity = createEntity(1L, 1L, "DRAFT");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        assertThrows(IllegalStateException.class,
            () -> service.approve(1L, "ok", 100L));
    }
}
