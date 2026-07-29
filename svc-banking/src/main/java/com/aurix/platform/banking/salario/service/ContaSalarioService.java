package com.aurix.platform.banking.salario.service;

import com.aurix.platform.banking.salario.client.ContaCorrenteClient;
import com.aurix.platform.banking.salario.dto.ContaSalarioRequest;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.banking.salario.dto.ContaSalarioResponse;
import com.aurix.platform.banking.salario.entity.ContaSalario;
import com.aurix.platform.banking.salario.entity.ContaSalario.StatusContaSalario;
import com.aurix.platform.banking.salario.entity.ConvenioEmpresa;
import com.aurix.platform.banking.salario.event.ContaSalarioCriadaEvent;
import com.aurix.platform.banking.salario.repository.ContaSalarioRepository;
import com.aurix.platform.banking.salario.repository.ConvenioEmpresaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContaSalarioService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContaSalarioService.class);

    private final ContaSalarioRepository contaSalarioRepository;
    private final ConvenioEmpresaRepository convenioEmpresaRepository;
    private final ContaCorrenteClient contaCorrenteClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ContaSalarioService(ContaSalarioRepository contaSalarioRepository,
                               ConvenioEmpresaRepository convenioEmpresaRepository,
                               ContaCorrenteClient contaCorrenteClient,
                               KafkaTemplate<String, String> kafkaTemplate,
                               ObjectMapper objectMapper) {
        this.contaSalarioRepository = contaSalarioRepository;
        this.convenioEmpresaRepository = convenioEmpresaRepository;
        this.contaCorrenteClient = contaCorrenteClient;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public ContaSalarioResponse criarConta(ContaSalarioRequest request) {
        log.info("Criando conta salario para matricula: {}", request.getMatriculaFuncionario());

        ConvenioEmpresa empresa = convenioEmpresaRepository.findByTenantIdAndId(
            TenantContext.getTenantId(),
            request.getEmpresaId()
        ).orElseThrow(() -> new IllegalArgumentException("Empresa conveniada nao encontrada: " + request.getEmpresaId()));

        ContaCorrenteClient.ContaCorrenteResponse cc = contaCorrenteClient.getConta(request.getContaCorrenteId());
        if (!"ATIVA".equals(cc.status())) {
            throw new IllegalArgumentException("Conta corrente nao esta ativa: " + request.getContaCorrenteId());
        }

        ContaSalario conta = new ContaSalario(
            request.getContaCorrenteId(),
            request.getEmpresaId(),
            request.getMatriculaFuncionario(),
            request.getCpfFuncionario(),
            request.getDataAdmissao(),
            request.getValorSalarioBruto(),
            request.getValorSalarioLiquido(),
            request.getDiaPagamento()
        );
        conta.setTenantId(TenantContext.getTenantId());

        ContaSalario salva = contaSalarioRepository.save(conta);

        publicarEventoContaCriada(salva);

        log.info("Conta salario criada: id={}, empresa={}", salva.getId(), empresa.getRazaoSocial());
        return converterParaResponse(salva);
    }

    @Transactional(readOnly = true)
    public ContaSalarioResponse buscarPorId(Long id) {
        ContaSalario conta = contaSalarioRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), id
        ).orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + id));
        return converterParaResponse(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaSalarioResponse> listarPorEmpresa(Long empresaId) {
        return contaSalarioRepository.findByTenantIdAndEmpresaId(
            TenantContext.getTenantId(), empresaId
        ).stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    public void bloquearConta(Long id) {
        ContaSalario conta = contaSalarioRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), id
        ).orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + id));
        conta.setStatus(StatusContaSalario.BLOQUEADA);
        contaSalarioRepository.save(conta);
        log.info("Conta salario bloqueada: {}", id);
    }

    public void rescindirConta(Long id) {
        ContaSalario conta = contaSalarioRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), id
        ).orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + id));
        conta.setStatus(StatusContaSalario.RESCINDIDA);
        conta.setDataRescisao(LocalDate.now());
        contaSalarioRepository.save(conta);
        log.info("Conta salario rescindida: {}", id);
    }

    private void publicarEventoContaCriada(ContaSalario conta) {
        try {
            String json = objectMapper.writeValueAsString(new ContaSalarioCriadaEvent(
                conta.getId(), null, conta.getEmpresaId(), conta.getDataAdmissao(),
                conta.getValorSalarioLiquido()));
            kafkaTemplate.send(Topics.SALARIO_CONTA_CRIADA, json);
        } catch (JsonProcessingException e) {
            log.warn("Falha ao serializar evento conta-criada: {}", e.getMessage());
        } catch (Exception e) {
            log.warn("Falha ao publicar evento conta-criada: {}", e.getMessage());
        }
    }

    private ContaSalarioResponse converterParaResponse(ContaSalario conta) {
        ContaSalarioResponse resp = new ContaSalarioResponse();
        resp.setId(conta.getId());
        resp.setContaCorrenteId(conta.getContaCorrenteId());
        resp.setEmpresaId(conta.getEmpresaId());
        resp.setMatriculaFuncionario(conta.getMatriculaFuncionario());
        resp.setCpfFuncionario(conta.getCpfFuncionario());
        resp.setDataAdmissao(conta.getDataAdmissao());
        resp.setDataRescisao(conta.getDataRescisao());
        resp.setValorSalarioBruto(conta.getValorSalarioBruto());
        resp.setValorSalarioLiquido(conta.getValorSalarioLiquido());
        resp.setDiaPagamento(conta.getDiaPagamento());
        resp.setPortabilidadeAtiva(conta.getPortabilidadeAtiva());
        resp.setStatus(conta.getStatus());
        resp.setDataCriacao(conta.getDataCriacao());
        resp.setDataAtualizacao(conta.getDataAtualizacao());
        return resp;
    }
}
