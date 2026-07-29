package com.aurix.platform.banking.core;

import com.aurix.platform.banking.core.dto.MovimentoContaDTO;
import com.aurix.platform.banking.core.service.ControleSaldoService;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.integration.BoletoProvider;
import com.aurix.platform.banking.core.repository.ClienteRepository;
import com.aurix.platform.banking.core.repository.ContaRepository;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class MassiveLoadTest {

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

    private static final Logger log = LoggerFactory.getLogger(MassiveLoadTest.class);

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
        TenantContext.setTenantId("massive-test-tenant");

        // Limpar dados anteriores (Filhos primeiro)
        historicoSaldoRepository.deleteAll();
        movimentoContaRepository.deleteAll();
        controleSaldoRepository.deleteAll();
        contaRepository.deleteAll();
        clienteRepository.deleteAll();

        // Criar Cliente
        Cliente cliente = new Cliente();
        cliente.setTenantId("massive-test-tenant");
        cliente.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        cliente.setCpf("12345678901");
        cliente.setNome("Massive Test Client");
        cliente.setEmail("massive@aurix.com");
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
        cliente = clienteRepository.save(cliente);

        // Criar Conta
        Conta conta = new Conta();
        conta.setTenantId("massive-test-tenant");
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
        controle.setTenantId("massive-test-tenant");
        controle.setVersaoSaldo(1);
        controleSaldoRepository.save(controle);
    }

    @Test
    public void testMassiveConcurrentUpdates() throws InterruptedException {
        int TOTAL_TRANSACTIONS = 10000;
        int CONCURRENCY_LEVEL = 100; // Número de threads simultâneas
        String tenantId = "massive-test-tenant";

        TenantContext.setTenantId(tenantId);

        // Garante que o controle de saldo existe
        BigDecimal saldoInicial = controleSaldoService.obterControleSaldo(this.contaId).getSaldoDisponivel();
        log.info("Iniciando teste massivo. Saldo inicial: {}", saldoInicial);

        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY_LEVEL);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(TOTAL_TRANSACTIONS);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger conflictCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        long startTime = System.nanoTime();

        for (int i = 0; i < TOTAL_TRANSACTIONS; i++) {
            executor.submit(() -> {
                try {
                    startGate.await(); // Aguarda o sinal de partida para sincronismo máximo
                    TenantContext.setTenantId(tenantId);

                    MovimentoContaDTO mov = new MovimentoContaDTO();
                    mov.setContaId(this.contaId);
                    mov.setTipoMovimento("CREDITO");
                    mov.setValorMovimento(BigDecimal.ONE);

                    controleSaldoService.processarMovimento(mov);
                    successCount.incrementAndGet();
                } catch (ObjectOptimisticLockingFailureException e) {
                    conflictCount.incrementAndGet();
                } catch (Exception e) {
                    log.error("Erro inesperado no worker", e);
                    errorCount.incrementAndGet();
                } finally {
                    endGate.countDown();
                }
            });
        }

        // DISPARO!
        startGate.countDown();
        endGate.await(5, TimeUnit.MINUTES);
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        executor.shutdown();

        BigDecimal saldoFinal = controleSaldoService.obterControleSaldo(this.contaId).getSaldoDisponivel();
        BigDecimal saldoEsperado = saldoInicial.add(new BigDecimal(successCount.get()));

        log.info("--- RESULTADOS DO TESTE MASSIVO ---");
        log.info("Tempo total: {}ms", duration);
        log.info("Total de transações tentadas: {}", TOTAL_TRANSACTIONS);
        log.info("Sucessos: {}", successCount.get());
        log.info("Conflitos (Optimistic Lock): {}", conflictCount.get());
        log.info("Erros: {}", errorCount.get());
        log.info("Saldo Final: {}", saldoFinal);
        log.info("Saldo Esperado: {}", saldoEsperado);
        log.info("TPS Efetivo (Sucessos/Tempo): {}", (double) successCount.get() / (duration / 1000.0));

        assertEquals(saldoEsperado, saldoFinal, "A integridade do saldo deve ser mantida mesmo sob stress massivo");

        log.info("Integridade verificada com sucesso!");
    }
}
