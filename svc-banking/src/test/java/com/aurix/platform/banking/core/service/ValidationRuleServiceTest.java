package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.ValidationRuleDTO;
import com.aurix.platform.banking.core.entity.ValidationRule;
import com.aurix.platform.banking.core.repository.ValidationRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationRuleServiceTest {

    @Mock
    private ValidationRuleRepository repository;

    private ValidationRuleService service;

    @BeforeEach
    void setUp() {
        service = new ValidationRuleService(repository);
    }

    private ValidationRuleDTO createDTO(String name, String expression, String scope) {
        ValidationRuleDTO dto = new ValidationRuleDTO();
        dto.setName(name);
        dto.setSpelExpression(expression);
        dto.setErrorCode("E0100_CUSTOM");
        dto.setErrorMessage("Custom rule failed");
        dto.setScope(scope);
        dto.setActive(true);
        dto.setPriority(1);
        dto.setRuleCategory("TEST");
        return dto;
    }

    @Test
    void criar_deveSalvarERetornarDTO() {
        ValidationRuleDTO dto = createDTO("Test Rule", "#valorTotal > 1000", "TRANSACAO");
        ValidationRule entity = dto.toEntity();
        entity.setId(1L);

        when(repository.save(any(ValidationRule.class))).thenReturn(entity);

        ValidationRuleDTO result = service.criar(dto);

        assertNotNull(result);
        assertEquals("Test Rule", result.getName());
        assertEquals("#valorTotal > 1000", result.getSpelExpression());
    }

    @Test
    void buscarPorId_deveRetornarDTO() {
        ValidationRule entity = createDTO("Find Rule", "#valorTotal > 500", "TRANSACAO").toEntity();
        entity.setId(99L);

        when(repository.findById(99L)).thenReturn(Optional.of(entity));

        Optional<ValidationRuleDTO> result = service.buscarPorId(99L);

        assertTrue(result.isPresent());
        assertEquals("Find Rule", result.get().getName());
    }

    @Test
    void buscarPorId_deveRetornarEmpty() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        assertTrue(service.buscarPorId(999L).isEmpty());
    }

    @Test
    void listarTodas_deveRetornarLista() {
        ValidationRule e1 = createDTO("Rule 1", "#valorTotal > 100", "TRANSACAO").toEntity();
        ValidationRule e2 = createDTO("Rule 2", "#sources.size() <= 1", "TRANSACAO").toEntity();

        when(repository.findAll()).thenReturn(List.of(e1, e2));

        List<ValidationRuleDTO> result = service.listarTodas();

        assertEquals(2, result.size());
    }

    @Test
    void deletar_deveChamarRepository() {
        service.deletar(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void toggleActive_deveAlternarStatus() {
        ValidationRule entity = createDTO("Toggle Rule", "#valorTotal > 100", "TRANSACAO").toEntity();
        entity.setId(5L);
        entity.setActive(true);

        when(repository.findById(5L)).thenReturn(Optional.of(entity));
        when(repository.save(any(ValidationRule.class))).thenAnswer(i -> i.getArgument(0));

        ValidationRuleDTO result = service.toggleActive(5L);

        assertFalse(result.getActive());
        verify(repository).save(entity);
    }

    @Test
    void atualizar_deveAlterarCampos() {
        ValidationRule entity = createDTO("Old Name", "#valorTotal > 100", "TRANSACAO").toEntity();
        entity.setId(10L);

        ValidationRuleDTO update = createDTO("New Name", "#valorTotal > 200", "TRANSACAO");

        when(repository.findById(10L)).thenReturn(Optional.of(entity));
        when(repository.save(any(ValidationRule.class))).thenAnswer(i -> i.getArgument(0));

        ValidationRuleDTO result = service.atualizar(10L, update);

        assertEquals("New Name", result.getName());
        assertEquals("#valorTotal > 200", result.getSpelExpression());
    }
}
