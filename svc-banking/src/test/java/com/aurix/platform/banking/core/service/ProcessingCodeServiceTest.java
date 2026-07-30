package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.ProcessingCodeDTO;
import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.banking.core.repository.ProcessingCodeRepository;
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
class ProcessingCodeServiceTest {

    @Mock
    private ProcessingCodeRepository repository;

    private ProcessingCodeService service;

    @BeforeEach
    void setUp() {
        service = new ProcessingCodeService(repository);
    }

    private ProcessingCodeDTO createDTO(String code, String type, int priority) {
        ProcessingCodeDTO dto = new ProcessingCodeDTO();
        dto.setCode(code);
        dto.setDescription("Test " + code);
        dto.setPaymentType(type);
        dto.setActive(true);
        dto.setPriority(priority);
        return dto;
    }

    @Test
    void criar_deveSalvar() {
        ProcessingCodeDTO dto = createDTO("PIX01", "PIX", 1);
        when(repository.findByCode("PIX01")).thenReturn(Optional.empty());
        when(repository.save(any(ProcessingCode.class))).thenAnswer(i -> {
            ProcessingCode e = i.getArgument(0);
            e.setId(1L);
            return e;
        });

        ProcessingCodeDTO result = service.criar(dto);

        assertNotNull(result);
        assertEquals("PIX01", result.getCode());
    }

    @Test
    void criar_comCodeDuplicado_deveLancarExcecao() {
        ProcessingCodeDTO dto = createDTO("PIX01", "PIX", 1);
        when(repository.findByCode("PIX01")).thenReturn(Optional.of(new ProcessingCode()));

        assertThrows(IllegalArgumentException.class, () -> service.criar(dto));
    }

    @Test
    void buscarPorCode_deveRetornar() {
        ProcessingCode entity = new ProcessingCode();
        entity.setId(1L);
        entity.setCode("TED01");
        entity.setPaymentType("TED");
        when(repository.findByCode("TED01")).thenReturn(Optional.of(entity));

        Optional<ProcessingCodeDTO> result = service.buscarPorCode("TED01");

        assertTrue(result.isPresent());
        assertEquals("TED01", result.get().getCode());
    }

    @Test
    void listarAtivos_deveRetornarOrdenado() {
        ProcessingCode p1 = new ProcessingCode();
        p1.setId(1L); p1.setCode("PIX01"); p1.setPaymentType("PIX"); p1.setActive(true); p1.setPriority(1);
        ProcessingCode p2 = new ProcessingCode();
        p2.setId(2L); p2.setCode("PIX02"); p2.setPaymentType("PIX"); p2.setActive(true); p2.setPriority(2);

        when(repository.findByActiveTrueOrderByPriorityAsc()).thenReturn(List.of(p1, p2));

        List<ProcessingCodeDTO> result = service.listarAtivos();

        assertEquals(2, result.size());
    }

    @Test
    void atualizar_deveAlterarCampos() {
        ProcessingCode entity = new ProcessingCode();
        entity.setId(1L);
        entity.setCode("DOC01");
        entity.setPaymentType("DOC");
        entity.setActive(true);
        entity.setPriority(1);

        ProcessingCodeDTO update = createDTO("DOC01", "TED", 2);
        update.setActive(false);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(ProcessingCode.class))).thenAnswer(i -> i.getArgument(0));

        ProcessingCodeDTO result = service.atualizar(1L, update);

        assertEquals("TED", result.getPaymentType());
        assertFalse(result.isActive());
        assertEquals(2, result.getPriority());
    }

    @Test
    void deletar_deveChamarRepository() {
        service.deletar(99L);
        verify(repository).deleteById(99L);
    }
}
