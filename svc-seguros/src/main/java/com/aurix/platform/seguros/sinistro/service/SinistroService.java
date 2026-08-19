package com.aurix.platform.seguros.sinistro.service;

import com.aurix.platform.seguros.apolice.entity.Apolice;
import com.aurix.platform.seguros.apolice.entity.StatusApolice;
import com.aurix.platform.seguros.apolice.repository.ApoliceRepository;
import com.aurix.platform.seguros.sinistro.dto.DocumentoRequest;
import com.aurix.platform.seguros.sinistro.dto.SinistroRequest;
import com.aurix.platform.seguros.sinistro.dto.SinistroResponse;
import com.aurix.platform.seguros.sinistro.entity.DocumentoSinistro;
import com.aurix.platform.seguros.sinistro.entity.Sinistro;
import com.aurix.platform.seguros.sinistro.entity.StatusSinistro;
import com.aurix.platform.seguros.sinistro.repository.DocumentoSinistroRepository;
import com.aurix.platform.seguros.sinistro.repository.SinistroRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SinistroService {

    private static final Logger log = LoggerFactory.getLogger(SinistroService.class);

    private static final int PRAZO_ABERTURA_DIAS = 30;
    private static final int PRAZO_ANALISE_DIAS = 15;
    private static final int PRAZO_PAGAMENTO_DIAS = 5;

    private final SinistroRepository sinistroRepository;
    private final DocumentoSinistroRepository documentoRepository;
    private final ApoliceRepository apoliceRepository;

    public SinistroService(SinistroRepository sinistroRepository,
                           DocumentoSinistroRepository documentoRepository,
                           ApoliceRepository apoliceRepository) {
        this.sinistroRepository = sinistroRepository;
        this.documentoRepository = documentoRepository;
        this.apoliceRepository = apoliceRepository;
    }

    @Transactional
    public SinistroResponse abrir(SinistroRequest request) {
        var apolice = apoliceRepository.findById(request.apoliceId())
            .orElseThrow(() -> new IllegalArgumentException("Apólice não encontrada: " + request.apoliceId()));

        if (apolice.getStatus() != StatusApolice.ATIVA
                && apolice.getStatus() != StatusApolice.EMITIDA) {
            throw new IllegalStateException(
                "Apólice não está ativa para abertura de sinistro: " + apolice.getStatus());
        }

        long diasDesdeEvento = ChronoUnit.DAYS.between(request.dataEvento(), LocalDate.now());
        if (diasDesdeEvento > PRAZO_ABERTURA_DIAS) {
            throw new IllegalStateException(
                "Prazo de abertura excedido. Evento deve ter ocorrido nos últimos "
                + PRAZO_ABERTURA_DIAS + " dias. Dias desde o evento: " + diasDesdeEvento);
        }

        var sinistro = new Sinistro(
            request.tenantId(), request.apoliceId(), request.clienteId(),
            request.produtoId(), request.produtoTipo(), request.descricaoEvento(),
            request.dataEvento(), request.valorSolicitado()
        );

        sinistro = sinistroRepository.save(sinistro);

        log.info("Sinistro aberto: id={}, apoliceId={}, evento={}",
            sinistro.getId(), request.apoliceId(), request.dataEvento());

        return toResponse(sinistro);
    }

    @Transactional(readOnly = true)
    public SinistroResponse buscarPorId(Long id) {
        var sinistro = sinistroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Sinistro não encontrado: " + id));
        return toResponse(sinistro);
    }

    @Transactional(readOnly = true)
    public List<SinistroResponse> listarPorCliente(Long clienteId) {
        return sinistroRepository.findByClienteId(clienteId).stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public void enviarDocumento(DocumentoRequest request) {
        var sinistro = sinistroRepository.findById(request.sinistroId())
            .orElseThrow(() -> new IllegalArgumentException("Sinistro não encontrado: " + request.sinistroId()));

        if (sinistro.getStatus() == StatusSinistro.PAGO
                || sinistro.getStatus() == StatusSinistro.CANCELADO) {
            throw new IllegalStateException("Sinistro já foi finalizado: " + request.sinistroId());
        }

        var documento = new DocumentoSinistro(
            request.sinistroId(), request.tipoDocumento(),
            request.nomeArquivo(), request.caminhoArquivo(), request.descricao()
        );
        documentoRepository.save(documento);

        if (sinistro.getStatus() == StatusSinistro.ABERTO) {
            sinistro.setStatus(StatusSinistro.DOCUMENTACAO_PENDENTE);
            sinistroRepository.save(sinistro);
        }

        log.info("Documento enviado para sinistro: sinistroId={}, tipo={}",
            request.sinistroId(), request.tipoDocumento());
    }

    @Transactional
    public void analisar(Long sinistroId, boolean aprovado, String motivo) {
        var sinistro = sinistroRepository.findById(sinistroId)
            .orElseThrow(() -> new IllegalArgumentException("Sinistro não encontrado: " + sinistroId));

        sinistro.setDataAnalise(LocalDate.now());

        if (aprovado) {
            sinistro.setStatus(StatusSinistro.APROVADO);
            sinistro.setValorAprovado(sinistro.getValorSolicitado());
            sinistro.setDataAprovacao(LocalDate.now());
            log.info("Sinistro aprovado: id={}", sinistroId);
        } else {
            sinistro.setStatus(StatusSinistro.REPROVADO);
            sinistro.setMotivoReprovacao(motivo);
            log.info("Sinistro reprovado: id={}, motivo={}", sinistroId, motivo);
        }

        sinistroRepository.save(sinistro);
    }

    @Transactional
    public void pagar(Long sinistroId) {
        var sinistro = sinistroRepository.findById(sinistroId)
            .orElseThrow(() -> new IllegalArgumentException("Sinistro não encontrado: " + sinistroId));

        if (sinistro.getStatus() != StatusSinistro.APROVADO) {
            throw new IllegalStateException("Sinistro deve estar aprovado para pagamento: " + sinistroId);
        }

        sinistro.setStatus(StatusSinistro.PAGO);
        sinistro.setDataPagamento(LocalDate.now());
        sinistroRepository.save(sinistro);

        log.info("Sinistro pago: id={}, valor={}", sinistroId, sinistro.getValorAprovado());
    }

    private SinistroResponse toResponse(Sinistro s) {
        var documentos = documentoRepository.findBySinistroId(s.getId()).stream()
            .map(d -> new SinistroResponse.DocumentoResponse(
                d.getId(), d.getTipoDocumento(), d.getNomeArquivo(),
                d.getDescricao(), d.getDataUpload()
            )).toList();

        return new SinistroResponse(
            s.getId(), s.getTenantId(), s.getApoliceId(), s.getClienteId(),
            s.getProdutoId(), s.getProdutoTipo(), s.getDescricaoEvento(),
            s.getDataEvento(), s.getDataAbertura(), s.getValorSolicitado(),
            s.getValorAprovado(), s.getStatus().name(), s.getMotivoReprovacao(),
            s.getDataAnalise(), s.getDataAprovacao(), s.getDataPagamento(),
            documentos, s.getDataCriacao()
        );
    }
}
