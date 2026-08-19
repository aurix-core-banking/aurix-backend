package com.aurix.platform.salary.service;

import com.aurix.platform.salary.dto.ContaSalarioRequest;
import com.aurix.platform.salary.dto.ContaSalarioResponse;
import com.aurix.platform.salary.dto.PortabilidadeRequest;
import com.aurix.platform.salary.dto.PortabilidadeResponse;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SalarioService {

    private static final Logger log = LoggerFactory.getLogger(SalarioService.class);

    private final ContaRepository contaRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public SalarioService(ContaRepository contaRepository,
                          KafkaTemplate<String, String> kafkaTemplate) {
        this.contaRepository = contaRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ContaSalarioResponse criarConta(ContaSalarioRequest request) {
        log.info("Criando conta salario para empresa: {}", request.getEmpresaId());
        String tenantId = TenantContext.getTenantId();

        Conta conta = new Conta();
        conta.setTenantId(tenantId);
        conta.setTipoConta(Conta.TipoConta.SALARIO);
        conta.setSaldo(request.getValorSalarioLiquido() != null
            ? request.getValorSalarioLiquido() : java.math.BigDecimal.ZERO);
        conta.setLimiteCredito(java.math.BigDecimal.ZERO);
        conta.setLimiteUtilizado(java.math.BigDecimal.ZERO);
        conta.setStatus(Conta.StatusConta.ATIVA);

        Conta contaSalva = contaRepository.save(conta);
        log.info("Conta salario criada: {}", contaSalva.getId());

        ContaSalarioResponse resp = new ContaSalarioResponse();
        resp.setId(contaSalva.getId());
        resp.setContaCorrenteId(request.getContaCorrenteId());
        resp.setEmpresaId(request.getEmpresaId());
        resp.setMatriculaFuncionario(request.getMatriculaFuncionario());
        resp.setCpfFuncionario(request.getCpfFuncionario());
        resp.setDataAdmissao(request.getDataAdmissao());
        resp.setValorSalarioBruto(request.getValorSalarioBruto());
        resp.setValorSalarioLiquido(request.getValorSalarioLiquido());
        resp.setDiaPagamento(request.getDiaPagamento());
        resp.setPortabilidadeAtiva(false);
        resp.setStatus(Conta.StatusConta.ATIVA.name());
        resp.setDataCriacao(contaSalva.getDataCriacao());
        return resp;
    }

    @Transactional(readOnly = true)
    public ContaSalarioResponse buscarPorId(Long id) {
        log.info("Buscando conta salario ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + id));
        return toResponse(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaSalarioResponse> listarPorEmpresa(Long empresaId) {
        log.info("Listando contas salario da empresa: {}", empresaId);
        String tenantId = TenantContext.getTenantId();
        return contaRepository.findByTenantId(tenantId).stream()
            .filter(c -> c.getTipoConta() == Conta.TipoConta.SALARIO)
            .map(this::toResponse).collect(Collectors.toList());
    }

    public void bloquearConta(Long id) {
        log.info("Bloqueando conta salario ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + id));
        conta.setStatus(Conta.StatusConta.BLOQUEADA);
        contaRepository.save(conta);
        log.info("Conta salario bloqueada: {}", id);
    }

    public void rescindirConta(Long id) {
        log.info("Rescindindo conta salario ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + id));
        conta.setStatus(Conta.StatusConta.FECHADA);
        conta.setDataFechamento(LocalDateTime.now());
        contaRepository.save(conta);
        log.info("Conta salario rescindida: {}", id);
    }

    public PortabilidadeResponse solicitarPortabilidade(Long id, PortabilidadeRequest request) {
        log.info("Solicitando portabilidade para conta salario: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + id));

        PortabilidadeResponse resp = new PortabilidadeResponse();
        resp.setContaSalarioId(id);
        resp.setContaDestinoId(request.getContaDestinoId());
        resp.setValorPortabilidade(request.getValorPortabilidade());
        resp.setBancoDestino(request.getBancoDestino());
        resp.setStatus("PENDENTE");
        resp.setDataSolicitacao(LocalDateTime.now());

        log.info("Portabilidade solicitada: conta={}, valor={}", id, request.getValorPortabilidade());
        return resp;
    }

    public void aprovarPortabilidade(Long id, Long portabilidadeId) {
        log.info("Aprovando portabilidade {} da conta salario {}", portabilidadeId, id);
    }

    public void cancelarPortabilidade(Long id, Long portabilidadeId) {
        log.info("Cancelando portabilidade {} da conta salario {}", portabilidadeId, id);
    }

    private ContaSalarioResponse toResponse(Conta conta) {
        ContaSalarioResponse resp = new ContaSalarioResponse();
        resp.setId(conta.getId());
        resp.setStatus(conta.getStatus() != null ? conta.getStatus().name() : null);
        resp.setDataCriacao(conta.getDataCriacao());
        resp.setDataAtualizacao(conta.getDataAtualizacao());
        return resp;
    }
}
