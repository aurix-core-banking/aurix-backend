package com.aurix.platform.contracts.service;

import com.aurix.platform.contracts.client.ClienteClient;
import com.aurix.platform.contracts.client.ProdutoClient;
import com.aurix.platform.contracts.dto.ContratoRequest;
import com.aurix.platform.contracts.dto.ContratoResponse;
import com.aurix.platform.contracts.dto.ContratoVersaoResponse;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.entity.ContratoVersao;
import com.aurix.platform.contracts.exception.ContratoNaoEncontradoException;
import com.aurix.platform.contracts.repository.ContratoRepository;
import com.aurix.platform.contracts.repository.ContratoVersaoRepository;
import com.aurix.platform.shared.event.ContratoCriadoEvent;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.Topics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final ContratoVersaoRepository versaoRepository;
    private final IntegracaoContratoService integracaoService;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public ContratoService(ContratoRepository contratoRepository,
                           ContratoVersaoRepository versaoRepository,
                           IntegracaoContratoService integracaoService,
                           EventPublisher eventPublisher,
                           ObjectMapper objectMapper) {
        this.contratoRepository = contratoRepository;
        this.versaoRepository = versaoRepository;
        this.integracaoService = integracaoService;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Contrato criar(ContratoRequest request) {
        if (contratoRepository.existsByNumeroContrato(request.numeroContrato())) {
            throw new IllegalArgumentException("Já existe contrato com o número: " + request.numeroContrato());
        }
        Contrato contrato = new Contrato();
        contrato.setNumeroContrato(request.numeroContrato());
        contrato.setClienteId(request.clienteId());
        contrato.setClienteDocumento(request.clienteDocumento());
        contrato.setTipoContrato(request.tipoContrato());
        contrato.setValor(request.valor());
        contrato.setPrazoMeses(request.prazoMeses());
        contrato.setValorParcela(request.valorParcela());
        contrato.setTaxaJuros(request.taxaJuros());
        contrato.setDataVigenciaInicio(request.dataVigenciaInicio());
        contrato.setDataVigenciaFim(request.dataVigenciaFim());
        contrato.setTermosTexto(request.termosTexto());
        contrato.setDadosJson(request.dadosJson());
        contrato.setStatus(Contrato.StatusContrato.RASCUNHO);

        enriquecerCliente(contrato, request);
        enriquecerProduto(contrato, request);

        contrato = contratoRepository.save(contrato);
        registraVersao(contrato, "Criação do contrato");

        eventPublisher.publish(Topics.CONTRATO_CRIADO, ContratoCriadoEvent.criado(
            contrato.getId(), contrato.getNumeroContrato(), contrato.getClienteId(),
            contrato.getTipoContrato().name(), contrato.getValor(), contrato.getPrazoMeses()));
        return contrato;
    }

    @Transactional
    public Contrato atualizar(Long id, ContratoRequest request, String motivoAlteracao) {
        Contrato contrato = buscarEntidade(id);
        if (!contrato.getNumeroContrato().equals(request.numeroContrato())) {
            throw new IllegalArgumentException("O número do contrato não pode ser alterado");
        }
        if (contrato.getStatus() == Contrato.StatusContrato.ATIVO
            || contrato.getStatus() == Contrato.StatusContrato.LIQUIDADO
            || contrato.getStatus() == Contrato.StatusContrato.CANCELADO) {
            throw new IllegalStateException("Contrato " + contrato.getStatus().name().toLowerCase() + " não pode ser editado");
        }
        contrato.setClienteId(request.clienteId());
        contrato.setTipoContrato(request.tipoContrato());
        contrato.setValor(request.valor());
        contrato.setPrazoMeses(request.prazoMeses());
        contrato.setValorParcela(request.valorParcela());
        contrato.setTaxaJuros(request.taxaJuros());
        contrato.setDataVigenciaInicio(request.dataVigenciaInicio());
        contrato.setDataVigenciaFim(request.dataVigenciaFim());
        contrato.setTermosTexto(request.termosTexto());
        contrato.setDadosJson(request.dadosJson());
        contrato = contratoRepository.save(contrato);
        registraVersao(contrato, motivoAlteracao);
        return contrato;
    }

    @Transactional(readOnly = true)
    public Contrato buscarEntidade(Long id) {
        return contratoRepository.findById(id)
            .orElseThrow(() -> new ContratoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public Contrato buscarPorNumero(String numeroContrato) {
        return contratoRepository.findByNumeroContrato(numeroContrato)
            .orElseThrow(() -> new ContratoNaoEncontradoException(numeroContrato));
    }

    @Transactional(readOnly = true)
    public List<Contrato> listar(Long clienteId, Contrato.StatusContrato status) {
        if (clienteId != null) {
            return contratoRepository.findByClienteId(clienteId);
        }
        if (status != null) {
            return contratoRepository.findByStatus(status);
        }
        return contratoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ContratoVersaoResponse> listarVersoes(Long contratoId) {
        buscarEntidade(contratoId);
        return versaoRepository.findByContratoIdOrderByNumeroVersaoDesc(contratoId).stream()
            .map(ContratoVersaoResponse::de)
            .toList();
    }

    @Transactional
    public Contrato liquidar(Long id) {
        Contrato contrato = buscarEntidade(id);
        if (contrato.getStatus() == Contrato.StatusContrato.CANCELADO) {
            throw new IllegalStateException("Contrato cancelado não pode ser liquidado");
        }
        contrato.setStatus(Contrato.StatusContrato.LIQUIDADO);
        contrato = contratoRepository.save(contrato);
        registraVersao(contrato, "Liquidação do contrato");
        eventPublisher.publish(Topics.CONTRATO_LIQUIDADO, ContratoCriadoEvent.criado(
            contrato.getId(), contrato.getNumeroContrato(), contrato.getClienteId(),
            contrato.getTipoContrato().name(), contrato.getValor(), contrato.getPrazoMeses()));
        return contrato;
    }

    @Transactional
    public Contrato cancelar(Long id) {
        Contrato contrato = buscarEntidade(id);
        if (contrato.getStatus() == Contrato.StatusContrato.LIQUIDADO) {
            throw new IllegalStateException("Contrato liquidado não pode ser cancelado");
        }
        contrato.setStatus(Contrato.StatusContrato.CANCELADO);
        contrato = contratoRepository.save(contrato);
        registraVersao(contrato, "Cancelamento do contrato");
        eventPublisher.publish(Topics.CONTRATO_CANCELADO, ContratoCriadoEvent.criado(
            contrato.getId(), contrato.getNumeroContrato(), contrato.getClienteId(),
            contrato.getTipoContrato().name(), contrato.getValor(), contrato.getPrazoMeses()));
        return contrato;
    }

    private void enriquecerCliente(Contrato contrato, ContratoRequest request) {
        if (contrato.getClienteDocumento() == null) {
            Optional<ClienteClient.ClientePerfil> perfil = integracaoService.buscarCliente(request.clienteId());
            perfil.ifPresent(p -> {
                if (contrato.getClienteDocumento() == null) {
                    contrato.setClienteDocumento(p.documento());
                }
            });
        }
    }

    private void enriquecerProduto(Contrato contrato, ContratoRequest request) {
        if (contrato.getProdutoId() == null && request.produtoCodigo() != null) {
            Optional<ProdutoClient.ProdutoCatalogo> produto = integracaoService.buscarProduto(request.produtoCodigo());
            produto.ifPresent(p -> {
                contrato.setProdutoId(p.id());
                contrato.setProdutoCodigo(p.codigo());
            });
        }
    }

    private void registraVersao(Contrato contrato, String motivo) {
        ContratoVersao versao = new ContratoVersao();
        versao.setContratoId(contrato.getId());
        versao.setNumeroVersao(versaoRepository.findByContratoIdOrderByNumeroVersaoDesc(contrato.getId())
            .stream().findFirst().map(v -> v.getNumeroVersao() + 1).orElse(1));
        versao.setMotivoAlteracao(motivo);
        versao.setDadosJson(serializar(contrato));
        versao.setDataVersao(LocalDateTime.now());
        versaoRepository.save(versao);
    }

    private String serializar(Contrato contrato) {
        try {
            return objectMapper.writeValueAsString(ContratoResponse.de(contrato));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Falha ao serializar snapshot do contrato", e);
        }
    }
}
