package com.aurix.platform.banking.ted.service;

import com.aurix.platform.banking.ted.dto.TedRequest;
import com.aurix.platform.banking.ted.dto.TedResponse;
import com.aurix.platform.banking.ted.entity.TransferenciaTed;
import com.aurix.platform.banking.ted.entity.TransferenciaTed.StatusTed;
import com.aurix.platform.banking.ted.repository.TransferenciaTedRepository;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TedService {

    private static final Logger log = LoggerFactory.getLogger(TedService.class);
    private static final LocalTime HORARIO_INICIO_SPI = LocalTime.of(9, 0);
    private static final LocalTime HORARIO_FIM_SPI = LocalTime.of(17, 0);
    private static final int TAMANHO_ISPB = 8;

    private final TransferenciaTedRepository tedRepository;
    private final ContaRepository contaRepository;

    public TedService(TransferenciaTedRepository tedRepository, ContaRepository contaRepository) {
        this.tedRepository = tedRepository;
        this.contaRepository = contaRepository;
    }

    public TedResponse criar(TedRequest request) {
        log.info("Criando TED: contaOrigem={}, valor={}", request.getContaOrigemId(), request.getValor());

        String tenantId = TenantContext.getTenantId();

        Conta contaOrigem = contaRepository.findByTenantIdAndId(tenantId, request.getContaOrigemId())
            .orElseThrow(() -> new IllegalArgumentException("Conta origem nao encontrada: " + request.getContaOrigemId()));

        if (contaOrigem.getStatus() != Conta.StatusConta.ATIVA) {
            throw new IllegalArgumentException("Conta origem nao esta ativa");
        }

        if (contaOrigem.getSaldo().compareTo(request.getValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para TED");
        }

        if (request.getIspbDestino() == null || request.getIspbDestino().length() != TAMANHO_ISPB) {
            throw new IllegalArgumentException("ISPB destino deve ter " + TAMANHO_ISPB + " caracteres");
        }

        if (!isDentroHorarioSpb()) {
            log.warn("TED solicitada fora do horario SPI (09:00-17:00). Sera processada no proximo horario.");
        }

        TransferenciaTed ted = new TransferenciaTed();
        ted.setTenantId(tenantId);
        ted.setContaOrigemId(contaOrigem.getId());
        ted.setContaOrigemNumero(contaOrigem.getNumeroConta());
        ted.setIspbDestino(request.getIspbDestino());
        ted.setContaDestinoAgencia(request.getAgenciaDestino());
        ted.setContaDestinoConta(request.getContaDestino());
        ted.setContaDestinoNome(request.getNomeDestinatario());
        ted.setContaDestinoDocumento(request.getDocumentoDestinatario());
        ted.setValor(request.getValor());
        ted.setDescricao(request.getDescricao());
        ted.setCodigoBancoDestino(request.getCodigoBancoDestino());
        ted.setAgenciaDestino(request.getAgenciaDestino());
        ted.setStatus(StatusTed.PENDENTE);

        TransferenciaTed salva = tedRepository.save(ted);

        log.info("TED criada: id={}, protocolo={}", salva.getId(), salva.getSpiProtocolo());

        return converterParaResponse(salva);
    }

    @Transactional(readOnly = true)
    public TedResponse buscarPorId(Long id) {
        String tenantId = TenantContext.getTenantId();
        TransferenciaTed ted = tedRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("TED nao encontrada: " + id));
        return converterParaResponse(ted);
    }

    @Transactional(readOnly = true)
    public List<TedResponse> listarTransferencias(Long contaOrigemId) {
        String tenantId = TenantContext.getTenantId();
        List<TransferenciaTed> teds;
        if (contaOrigemId != null) {
            teds = tedRepository.findByTenantIdAndContaOrigemId(tenantId, contaOrigemId);
        } else {
            teds = tedRepository.findByTenantId(tenantId);
        }
        return teds.stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    public void processarTed(Long tedId) {
        log.info("Processando TED: {}", tedId);
        String tenantId = TenantContext.getTenantId();

        TransferenciaTed ted = tedRepository.findByTenantIdAndId(tenantId, tedId)
            .orElseThrow(() -> new IllegalArgumentException("TED nao encontrada: " + tedId));

        if (ted.getStatus() != StatusTed.PENDENTE) {
            throw new IllegalArgumentException("TED nao esta pendente de processamento");
        }

        try {
            ted.setStatus(StatusTed.PROCESSADA);
            ted.setDataProcessamento(java.time.LocalDateTime.now());
            ted.setSpiProtocolo("SPI-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase());
            tedRepository.save(ted);

            log.info("TED processada com sucesso: id={}, protocolo={}", ted.getId(), ted.getSpiProtocolo());

        } catch (Exception e) {
            log.error("Erro ao processar TED {}: {}", tedId, e.getMessage());
            ted.setStatus(StatusTed.FALHOU);
            ted.setMotivoFalha(e.getMessage());
            tedRepository.save(ted);
        }
    }

    public void confirmarTed(Long tedId) {
        log.info("Confirmando TED: {}", tedId);
        String tenantId = TenantContext.getTenantId();

        TransferenciaTed ted = tedRepository.findByTenantIdAndId(tenantId, tedId)
            .orElseThrow(() -> new IllegalArgumentException("TED nao encontrada: " + tedId));

        if (ted.getStatus() != StatusTed.PROCESSADA) {
            throw new IllegalArgumentException("TED nao esta processada");
        }

        ted.setStatus(StatusTed.CONFIRMADA);
        ted.setDataConfirmacao(java.time.LocalDateTime.now());
        tedRepository.save(ted);

        log.info("TED confirmada: id={}", ted.getId());
    }

    public void cancelarTed(Long tedId) {
        log.info("Cancelando TED: {}", tedId);
        String tenantId = TenantContext.getTenantId();

        TransferenciaTed ted = tedRepository.findByTenantIdAndId(tenantId, tedId)
            .orElseThrow(() -> new IllegalArgumentException("TED nao encontrada: " + tedId));

        if (ted.getStatus() == StatusTed.CONFIRMADA) {
            throw new IllegalArgumentException("TED confirmada nao pode ser cancelada");
        }

        ted.setStatus(StatusTed.CANCELADA);
        tedRepository.save(ted);

        log.info("TED cancelada: id={}", ted.getId());
    }

    public boolean isDentroHorarioSpb() {
        LocalTime agora = LocalTime.now();
        return !agora.isBefore(HORARIO_INICIO_SPI) && !agora.isAfter(HORARIO_FIM_SPI);
    }

    private TedResponse converterParaResponse(TransferenciaTed ted) {
        TedResponse response = new TedResponse();
        response.setId(ted.getId());
        response.setContaOrigemId(ted.getContaOrigemId());
        response.setContaOrigemNumero(ted.getContaOrigemNumero());
        response.setIspbDestino(ted.getIspbDestino());
        response.setContaDestinoAgencia(ted.getContaDestinoAgencia());
        response.setContaDestinoConta(ted.getContaDestinoConta());
        response.setContaDestinoNome(ted.getContaDestinoNome());
        response.setValor(ted.getValor());
        response.setDescricao(ted.getDescricao());
        response.setStatus(ted.getStatus());
        response.setDataCriacao(ted.getDataCriacao());
        response.setDataProcessamento(ted.getDataProcessamento());
        response.setDataConfirmacao(ted.getDataConfirmacao());
        response.setSpiProtocolo(ted.getSpiProtocolo());
        response.setMotivoFalha(ted.getMotivoFalha());
        return response;
    }
}
