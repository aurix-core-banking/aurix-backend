package com.aurix.platform.accounts.service;

import com.aurix.platform.accounts.dto.ContaRequest;
import com.aurix.platform.accounts.dto.ContaResponse;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.event.ContaEvent;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.exception.ContaNaoEncontradaException;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import com.aurix.platform.shared.util.ContaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContaService {

    private static final Logger log = LoggerFactory.getLogger(ContaService.class);

    private final ContaRepository contaRepository;
    private final EventPublisher eventPublisher;

    public ContaService(ContaRepository contaRepository, EventPublisher eventPublisher) {
        this.contaRepository = contaRepository;
        this.eventPublisher = eventPublisher;
    }

    public ContaResponse criarConta(ContaRequest request) {
        log.info("Criando conta para cliente ID: {}", request.getClienteId());
        String tenantId = TenantContext.getTenantId();
        String numeroConta = gerarNumeroContaUnico();

        Conta conta = new Conta();
        conta.setTenantId(tenantId);
        conta.setNumeroConta(numeroConta);
        conta.setTipoConta(request.getTipoConta() != null
            ? Conta.TipoConta.valueOf(request.getTipoConta()) : Conta.TipoConta.CORRENTE);
        conta.setSaldo(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);
        conta.setLimiteCredito(request.getLimiteCredito() != null ? request.getLimiteCredito() : BigDecimal.ZERO);
        conta.setLimiteUtilizado(BigDecimal.ZERO);
        conta.setStatus(Conta.StatusConta.ATIVA);
        conta.setDadosExtras(request.getDadosExtras());

        Conta contaSalva = contaRepository.save(conta);
        log.info("Conta criada com numero: {}", contaSalva.getNumeroConta());

        try {
            eventPublisher.publicarContaCriada(ContaEvent.contaCriada(
                String.valueOf(contaSalva.getId()),
                String.valueOf(contaSalva.getCliente().getId()),
                contaSalva.getSaldo(),
                contaSalva.getTipoConta() != null ? contaSalva.getTipoConta().name() : "CORRENTE"));
        } catch (Exception e) {
            log.warn("Falha ao publicar evento conta-criada: {}", e.getMessage());
        }

        return converterParaResponse(contaSalva);
    }

    @Transactional(readOnly = true)
    public ContaResponse buscarPorId(Long id) {
        log.info("Buscando conta por ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
        return converterParaResponse(conta);
    }

    @Transactional(readOnly = true)
    public ContaResponse buscarPorNumero(String numeroConta) {
        log.info("Buscando conta por numero: {}", numeroConta);
        if (!ContaUtil.isValid(numeroConta)) {
            throw new IllegalArgumentException("Numero da conta invalido: " + numeroConta);
        }
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndNumeroConta(tenantId, numeroConta)
            .orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
        return converterParaResponse(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaResponse> listarPorCliente(Long clienteId) {
        log.info("Listando contas do cliente ID: {}", clienteId);
        String tenantId = TenantContext.getTenantId();
        return contaRepository.findByTenantIdAndClienteId(tenantId, clienteId).stream()
            .map(this::converterParaResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContaResponse> listarTodas() {
        log.info("Listando todas as contas");
        String tenantId = TenantContext.getTenantId();
        return contaRepository.findByTenantId(tenantId).stream()
            .map(this::converterParaResponse).collect(Collectors.toList());
    }

    public ContaResponse atualizarConta(Long id, ContaRequest request) {
        log.info("Atualizando conta ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));

        conta.setLimiteCredito(request.getLimiteCredito());
        conta.setDadosExtras(request.getDadosExtras());
        Conta contaAtualizada = contaRepository.save(conta);
        log.info("Conta atualizada com sucesso");
        return converterParaResponse(contaAtualizada);
    }

    public void fecharConta(Long id) {
        log.info("Fechando conta ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
        conta.setStatus(Conta.StatusConta.FECHADA);
        conta.setDataFechamento(java.time.LocalDateTime.now());
        contaRepository.save(conta);
        log.info("Conta fechada com sucesso");
    }

    @Transactional(readOnly = true)
    public BigDecimal consultarSaldo(Long id) {
        log.info("Consultando saldo da conta ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
        return conta.getSaldo();
    }

    private String gerarNumeroContaUnico() {
        String numeroConta;
        int tentativas = 0;
        int maxTentativas = 100;
        do {
            numeroConta = ContaUtil.gerarNumeroConta();
            tentativas++;
            if (tentativas >= maxTentativas) {
                throw new RuntimeException("Nao foi possivel gerar numero de conta unico apos "
                    + maxTentativas + " tentativas");
            }
        } while (contaRepository.existsByTenantIdAndNumeroConta(TenantContext.getTenantId(), numeroConta));
        return numeroConta;
    }

    private ContaResponse converterParaResponse(Conta conta) {
        ContaResponse resp = new ContaResponse();
        resp.setId(conta.getId());
        resp.setNumeroConta(conta.getNumeroConta());
        resp.setClienteId(conta.getCliente() != null ? conta.getCliente().getId() : null);
        resp.setClienteNome(conta.getCliente() != null ? conta.getCliente().getNome() : null);
        resp.setTipoConta(conta.getTipoConta() != null ? conta.getTipoConta().name() : null);
        resp.setSaldo(conta.getSaldo());
        resp.setLimiteCredito(conta.getLimiteCredito());
        resp.setLimiteUtilizado(conta.getLimiteUtilizado());
        resp.setLimiteDisponivel(conta.getLimiteDisponivel());
        resp.setStatus(conta.getStatus() != null ? conta.getStatus().name() : null);
        resp.setDataAbertura(conta.getDataAbertura());
        resp.setDataFechamento(conta.getDataFechamento());
        resp.setDadosExtras(conta.getDadosExtras());
        resp.setDataCriacao(conta.getDataCriacao());
        resp.setDataAtualizacao(conta.getDataAtualizacao());
        return resp;
    }
}
