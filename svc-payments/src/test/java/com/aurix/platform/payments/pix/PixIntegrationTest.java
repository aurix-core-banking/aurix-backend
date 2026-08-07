package com.aurix.platform.payments.pix;

import com.aurix.platform.payments.pix.client.PixBacenClient;
import com.aurix.platform.payments.pix.client.dto.SpiResult;
import com.aurix.platform.shared.repository.ClienteRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.payments.pix.service.PixTransferenciaService;
import com.aurix.platform.shared.dto.PixTransferenciaDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.PixTransferencia;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(PixIntegrationTest.TestConfig.class)
public class PixIntegrationTest {

    @Autowired
    private PixTransferenciaService pixTransferenciaService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Conta contaTeste;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);

        clienteRepository.deleteAll();
        contaRepository.deleteAll();

        Cliente cliente = new Cliente();
        cliente.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        cliente.setCpf("12345678901");
        cliente.setNome("Jackson Wendel");
        cliente.setEmail("jackson@aurix.com");
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
        cliente = clienteRepository.save(cliente);

        Conta conta = new Conta();
        conta.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        conta.setNumeroConta("12345-6");
        conta.setCliente(cliente);
        conta.setTipoConta(Conta.TipoConta.CORRENTE);
        conta.setSaldo(BigDecimal.valueOf(1000));
        conta.setStatus(Conta.StatusConta.ATIVA);
        contaTeste = contaRepository.save(conta);
    }

    @Test
    public void testFullPixFlow() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.TEN);
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);

        PixTransferenciaDTO salva = pixTransferenciaService.criarTransferenciaPix(dto);
        assertNotNull(salva.getId());
        assertEquals(PixTransferencia.StatusPix.PENDENTE, salva.getStatus());

        pixTransferenciaService.processarTransferencia(salva.getId());

        PixTransferenciaDTO processada = pixTransferenciaService.buscarTransferenciaPorId(salva.getId());
        assertEquals(PixTransferencia.StatusPix.PROCESSADA, processada.getStatus());
        assertNotNull(processada.getDataProcessamento());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public PixBacenClient mockPixBacenClient() {
            PixBacenClient mock = Mockito.mock(PixBacenClient.class);
            SpiResult result = new SpiResult();
            result.setSucesso(true);
            result.setEndToEndId("E0000000020240710TESTENDTOEND01");
            result.setStatus("LIQUIDADA");
            Mockito.when(mock.enviarPix(Mockito.any())).thenReturn(result);
            return mock;
        }
    }
}
