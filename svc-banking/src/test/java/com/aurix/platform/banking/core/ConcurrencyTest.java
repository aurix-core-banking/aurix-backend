package com.aurix.platform.banking.core;

import com.aurix.platform.banking.core.dto.MovimentoContaDTO;
import com.aurix.platform.banking.core.service.ControleSaldoService;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.integration.BoletoProvider;
import com.aurix.platform.shared.repository.ClienteRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.banking.core.repository.HistoricoSaldoRepository;
import com.aurix.platform.banking.core.repository.MovimentoContaRepository;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
public class ConcurrencyTest {

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private BoletoProvider boletoProvider;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private RedisConnectionFactory redisConnectionFactory;

    @MockitoBean
    private org.springframework.data.redis.connection.ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

    @Autowired
    private ControleSaldoService controleSaldoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ControleSaldoRepository controleSaldoRepository;

    @Autowired
    private MovimentoContaRepository movimentoContaRepository;

    @Autowired
    private HistoricoSaldoRepository historicoSaldoRepository;

    private Long contaId;

    @BeforeEach
    public void setup() {
        TenantContext.clear();
        TenantContext.setTenantId("test-tenant");

        // Limpar dados anteriores (Filhos primeiro)
        historicoSaldoRepository.deleteAll();
        movimentoContaRepository.deleteAll();
        controleSaldoRepository.deleteAll();
        contaRepository.deleteAll();
        clienteRepository.deleteAll();

        // Criar Cliente
        Cliente cliente = new Cliente();
        cliente.setTenantId("test-tenant");
        cliente.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        cliente.setCpf("12345678901");
        cliente.setNome("Test Client");
        cliente.setEmail("test@aurix.com");
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
        cliente = clienteRepository.save(cliente);

        // Criar Conta
        Conta conta = new Conta();
        conta.setTenantId("test-tenant");
        conta.setNumeroConta("12345-6");
        conta.setCliente(cliente);
        conta.setTipoConta(Conta.TipoConta.CORRENTE);
        conta.setSaldoAtual(BigDecimal.valueOf(100.0));
        conta.setStatus(Conta.StatusConta.ATIVA);
        conta = contaRepository.save(conta);

        this.contaId = conta.getId();

        // Criar ControleSaldo
        ControleSaldo controle = new ControleSaldo();
        controle.setConta(conta);
        controle.setSaldoDisponivel(conta.getSaldoAtual());
        controle.setSaldoBloqueado(BigDecimal.ZERO);
        controle.setSaldoPendente(BigDecimal.ZERO);
        controle.setSaldoTotal(conta.getSaldoAtual());
        controle.setLimiteCredito(BigDecimal.valueOf(5000.0));
        controle.setLimiteUtilizado(BigDecimal.ZERO);
        controle.setLimiteDisponivel(BigDecimal.valueOf(5000.0));
        controle.setDataUltimaAtualizacao(LocalDateTime.now());
        controle.setSaldoConsistente(true);
        controle.setBloqueioOperacoes(false);
        controle.setTenantId("test-tenant");
        controle.setVersaoSaldo(1);
        controleSaldoRepository.save(controle);
    }

    @Test
    public void testConcurrentBalanceUpdates() throws InterruptedException {
        // Setup inicial
        TenantContext.setTenantId("test-tenant");
        BigDecimal valorInicial = controleSaldoService.obterControleSaldo(this.contaId).getSaldoDisponivel();

        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    TenantContext.setTenantId("test-tenant");
                    MovimentoContaDTO mov = new MovimentoContaDTO();
                    mov.setContaId(contaId);
                    mov.setTipoMovimento("CREDITO");
                    mov.setValorMovimento(BigDecimal.ONE);

                    controleSaldoService.processarMovimento(mov);
                    successCount.incrementAndGet();
                } catch (ObjectOptimisticLockingFailureException e) {
                    failureCount.incrementAndGet();
                } catch (Exception e) {
                    System.err.println("Erro inesperado: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        BigDecimal valorFinal = controleSaldoService.obterControleSaldo(this.contaId).getSaldoDisponivel();

        System.out.println("Sucessos: " + successCount.get());
        System.out.println("Falhas (Optimistic Lock): " + failureCount.get());

        // Com Optimistic Locking, o valor final DEVE ser igual ao inicial + sucessos
        // Sem ele, haveria "Lost Updates" e o valor seria inconsistente.
        assertEquals(valorInicial.add(new BigDecimal(successCount.get())), valorFinal);

        System.out.println("Integridade de saldo garantida!");
    }
}
