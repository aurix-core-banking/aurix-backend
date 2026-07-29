package com.aurix.platform.customer.service;

import com.aurix.platform.customer.entity.Cliente;
import com.aurix.platform.customer.repository.ClienteRepository;
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
class ClienteServiceTest {
    @Mock private ClienteRepository clienteRepository;
    @Mock private com.aurix.platform.customer.repository.EnderecoRepository enderecoRepository;
    @Mock private com.aurix.platform.customer.repository.ContatoRepository contatoRepository;
    @Mock private ClienteProducer clienteProducer;
    @InjectMocks private ClienteService clienteService;

    @Test
    void deveCriarClienteComStatusAtivo() {
        Cliente cliente = new Cliente();
        cliente.setNomeCompleto("Joao Silva");
        cliente.setDocumento("12345678901");
        cliente.setTipoPessoa("PF");
        cliente.setSegmento("PF");

        when(clienteRepository.save(any())).thenAnswer(inv -> {
            Cliente saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Cliente resultado = clienteService.criar(cliente);

        assertEquals("ATIVO", resultado.getStatus());
        assertEquals("Joao Silva", resultado.getNomeCompleto());
        verify(clienteProducer).clienteCriado(any());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> clienteService.buscarPorId(99L));
    }
}
