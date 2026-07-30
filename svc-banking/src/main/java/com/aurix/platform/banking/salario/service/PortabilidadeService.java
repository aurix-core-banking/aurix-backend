package com.aurix.platform.banking.salario.service;

import com.aurix.platform.banking.salario.dto.PortabilidadeRequest;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.banking.salario.dto.PortabilidadeResponse;
import com.aurix.platform.banking.salario.entity.ContaSalario;
import com.aurix.platform.banking.salario.entity.ConvenioEmpresa;
import com.aurix.platform.banking.salario.entity.SolicitacaoPortabilidade;
import com.aurix.platform.banking.salario.event.PortabilidadeSolicitadaEvent;
import com.aurix.platform.banking.salario.repository.ContaSalarioRepository;
import com.aurix.platform.banking.salario.repository.ConvenioEmpresaRepository;
import com.aurix.platform.banking.salario.repository.SolicitacaoPortabilidadeRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PortabilidadeService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PortabilidadeService.class);

    private final SolicitacaoPortabilidadeRepository solicitacaoRepository;
    private final ContaSalarioRepository contaSalarioRepository;
    private final ConvenioEmpresaRepository convenioEmpresaRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PortabilidadeService(SolicitacaoPortabilidadeRepository solicitacaoRepository,
                                ContaSalarioRepository contaSalarioRepository,
                                ConvenioEmpresaRepository convenioEmpresaRepository,
                                KafkaTemplate<String, String> kafkaTemplate,
                                ObjectMapper objectMapper) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.contaSalarioRepository = contaSalarioRepository;
        this.convenioEmpresaRepository = convenioEmpresaRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public PortabilidadeResponse solicitar(PortabilidadeRequest request) {
        log.info("Solicitando portabilidade para conta salario: {}", request.getContaSalarioId());

        ContaSalario conta = contaSalarioRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), request.getContaSalarioId())
            .orElseThrow(() -> new IllegalArgumentException("Conta salario nao encontrada: " + request.getContaSalarioId()));

        ConvenioEmpresa empresa = convenioEmpresaRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), conta.getEmpresaId())
            .orElseThrow(() -> new IllegalArgumentException("Empresa conveniada nao encontrada: " + conta.getEmpresaId()));
        if (!Boolean.TRUE.equals(empresa.getAtivo())) {
            throw new IllegalArgumentException("Empresa conveniada nao esta ativa: " + empresa.getId());
        }

        SolicitacaoPortabilidade solicitacao = new SolicitacaoPortabilidade(
            request.getContaSalarioId(), request.getCodigoBancoDestino(),
            request.getAgenciaDestino(), request.getContaDestino());
        solicitacao.setValorPercentual(request.getValorPercentual());
        solicitacao.setStatus(SolicitacaoPortabilidade.StatusPortabilidade.ATIVA);

        SolicitacaoPortabilidade salva = solicitacaoRepository.save(solicitacao);

        // Ativar portabilidade na conta
        conta.setPortabilidadeAtiva(true);
        contaSalarioRepository.save(conta);

        publicarEventoPortabilidade(salva);

        log.info("Portabilidade solicitada: id={}, banco={}", salva.getId(), request.getCodigoBancoDestino());
        return converterParaResponse(salva);
    }

    @Transactional(readOnly = true)
    public List<PortabilidadeResponse> listarPorConta(Long contaSalarioId) {
        return solicitacaoRepository.findByContaSalarioId(contaSalarioId).stream()
            .map(this::converterParaResponse).collect(Collectors.toList());
    }

    public void cancelar(Long id) {
        SolicitacaoPortabilidade solicitacao = solicitacaoRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), id)
            .orElseThrow(() -> new IllegalArgumentException("Portabilidade nao encontrada: " + id));
        solicitacao.setStatus(SolicitacaoPortabilidade.StatusPortabilidade.CANCELADA);
        solicitacaoRepository.save(solicitacao);

        // Se for a única ativa, desativar portabilidade na conta
        List<SolicitacaoPortabilidade> ativas = solicitacaoRepository
            .findByContaSalarioIdAndStatus(solicitacao.getContaSalarioId(),
                SolicitacaoPortabilidade.StatusPortabilidade.ATIVA);
        if (ativas.isEmpty()) {
            ContaSalario conta = contaSalarioRepository.findByTenantIdAndId(
                TenantContext.getTenantId(), solicitacao.getContaSalarioId())
                .orElseThrow();
            conta.setPortabilidadeAtiva(false);
            contaSalarioRepository.save(conta);
        }

        log.info("Portabilidade cancelada: {}", id);
    }

    private void publicarEventoPortabilidade(SolicitacaoPortabilidade solicitacao) {
        try {
            String json = objectMapper.writeValueAsString(new PortabilidadeSolicitadaEvent(
                solicitacao.getId(), solicitacao.getContaSalarioId(),
                solicitacao.getCodigoBancoDestino(), solicitacao.getValorPercentual()));
            kafkaTemplate.send(Topics.SALARIO_PORTABILIDADE_SOLICITADA, json);
        } catch (Exception e) {
            log.warn("Falha ao publicar evento portabilidade: {}", e.getMessage());
        }
    }

    private PortabilidadeResponse converterParaResponse(SolicitacaoPortabilidade s) {
        PortabilidadeResponse resp = new PortabilidadeResponse();
        resp.setId(s.getId());
        resp.setContaSalarioId(s.getContaSalarioId());
        resp.setCodigoBancoDestino(s.getCodigoBancoDestino());
        resp.setAgenciaDestino(s.getAgenciaDestino());
        resp.setContaDestino(s.getContaDestino());
        resp.setValorPercentual(s.getValorPercentual());
        resp.setStatus(s.getStatus());
        resp.setDataSolicitacao(s.getDataSolicitacao());
        resp.setDataCriacao(s.getDataCriacao());
        return resp;
    }
}
