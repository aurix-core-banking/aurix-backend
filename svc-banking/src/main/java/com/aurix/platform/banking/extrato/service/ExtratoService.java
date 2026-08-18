package com.aurix.platform.banking.extrato.service;

import com.aurix.platform.banking.extrato.dto.ExtratoRequest;
import com.aurix.platform.banking.extrato.dto.ExtratoResponse;
import com.aurix.platform.banking.extrato.dto.ExtratoResponse.MovimentacaoItem;
import com.aurix.platform.banking.extrato.entity.Extrato;
import com.aurix.platform.banking.extrato.repository.ExtratoRepository;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.repository.TransacaoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExtratoService {

    private static final Logger log = LoggerFactory.getLogger(ExtratoService.class);
    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final ExtratoRepository extratoRepository;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ExtratoService(ExtratoRepository extratoRepository,
                          ContaRepository contaRepository,
                          TransacaoRepository transacaoRepository) {
        this.extratoRepository = extratoRepository;
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public ExtratoResponse gerarExtrato(ExtratoRequest request) {
        log.info("Gerando extrato: contaId={}, de {} a {}",
            request.getContaId(), request.getDataInicio(), request.getDataFim());

        String tenantId = TenantContext.getTenantId();

        Conta conta = contaRepository.findByTenantIdAndId(tenantId, request.getContaId())
            .orElseThrow(() -> new IllegalArgumentException("Conta nao encontrada: " + request.getContaId()));

        if (request.getDataFim().isBefore(request.getDataInicio())) {
            throw new IllegalArgumentException("Data fim nao pode ser anterior a data inicio");
        }

        LocalDateTime dataInicio = request.getDataInicio().atStartOfDay();
        LocalDateTime dataFim = request.getDataFim().atTime(LocalTime.MAX);

        List<Transacao> transacoes = transacaoRepository.findByContaIdEPeriodo(
            request.getContaId(), dataInicio, dataFim);

        List<MovimentacaoItem> movimentacoes = transacoes.stream()
            .map(tx -> new MovimentacaoItem(
                tx.getId(),
                tx.getDataTransacao(),
                tx.getTipoTransacao() != null ? tx.getTipoTransacao().name() : "OUTROS",
                tx.getDescricao(),
                tx.getValor(),
                null
            ))
            .collect(Collectors.toList());

        BigDecimal totalCreditos = transacoes.stream()
            .filter(tx -> isCredito(tx.getTipoTransacao()))
            .map(Transacao::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebitos = transacoes.stream()
            .filter(tx -> !isCredito(tx.getTipoTransacao()))
            .map(Transacao::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Extrato extrato = new Extrato();
        extrato.setTenantId(tenantId);
        extrato.setContaId(conta.getId());
        extrato.setDataInicio(request.getDataInicio());
        extrato.setDataFim(request.getDataFim());
        extrato.setSaldoAnterior(conta.getSaldo().add(totalDebitos).subtract(totalCreditos));
        extrato.setSaldoFinal(conta.getSaldo());
        extrato.setTotalCreditos(totalCreditos);
        extrato.setTotalDebitos(totalDebitos);
        extrato.setQuantidadeMovimentacoes(transacoes.size());
        extratoRepository.save(extrato);

        ExtratoResponse response = new ExtratoResponse();
        response.setId(extrato.getId());
        response.setContaId(conta.getId());
        response.setContaNumero(conta.getNumeroConta());
        response.setDataInicio(request.getDataInicio());
        response.setDataFim(request.getDataFim());
        response.setSaldoAnterior(extrato.getSaldoAnterior());
        response.setSaldoFinal(extrato.getSaldoFinal());
        response.setTotalCreditos(totalCreditos);
        response.setTotalDebitos(totalDebitos);
        response.setQuantidadeMovimentacoes(transacoes.size());
        response.setDataGeracao(extrato.getDataGeracao());
        response.setMovimentacoes(movimentacoes);

        log.info("Extrato gerado: {} movimentacoes, saldo anterior={}, saldo final={}",
            movimentacoes.size(), extrato.getSaldoAnterior(), extrato.getSaldoFinal());

        return response;
    }

    public byte[] gerarPdf(Long contaId, LocalDate dataInicio, LocalDate dataFim) {
        log.info("Gerando PDF do extrato: contaId={}, de {} a {}", contaId, dataInicio, dataFim);

        ExtratoRequest request = new ExtratoRequest();
        request.setContaId(contaId);
        request.setDataInicio(dataInicio);
        request.setDataFim(dataFim);

        ExtratoResponse extrato = gerarExtrato(request);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (var writer = new java.io.PrintWriter(baos, false, StandardCharsets.UTF_8)) {
            writer.println("========================================");
            writer.println("           EXTRATO BANCARIO");
            writer.println("========================================");
            writer.println("Conta: " + extrato.getContaNumero());
            writer.println("Periodo: " + extrato.getDataInicio().format(FMT_DATA)
                + " a " + extrato.getDataFim().format(FMT_DATA));
            writer.println("----------------------------------------");
            writer.println("Saldo anterior:  R$ " + extrato.getSaldoAnterior());
            writer.println("Total creditos:  R$ " + extrato.getTotalCreditos());
            writer.println("Total debitos:   R$ " + extrato.getTotalDebitos());
            writer.println("Saldo final:     R$ " + extrato.getSaldoFinal());
            writer.println("========================================");
            writer.println();
            writer.println(String.format("%-20s %-35s %12s %12s",
                "DATA", "DESCRICAO", "VALOR", "SALDO"));
            writer.println(String.format("%-20s %-35s %12s %12s",
                "----", "---------", "-----", "-----"));

            BigDecimal saldoCorrente = extrato.getSaldoAnterior();
            if (extrato.getMovimentacoes() != null) {
                for (MovimentacaoItem item : extrato.getMovimentacoes()) {
                    saldoCorrente = saldoCorrente.add(item.getValor());
                    writer.println(String.format("%-20s %-35s %12.2f %12.2f",
                        item.getData() != null ? item.getData().format(FMT_DATA_HORA) : "",
                        item.getDescricao() != null ? item.getDescricao() : "",
                        item.getValor(),
                        saldoCorrente));
                }
            }

            writer.println();
            writer.println("Total de movimentacoes: " + extrato.getQuantidadeMovimentacoes());
            writer.println("========================================");
            writer.flush();
        } catch (Exception e) {
            log.error("Erro ao gerar PDF do extrato: {}", e.getMessage());
            throw new RuntimeException("Erro ao gerar PDF do extrato", e);
        }

        log.info("PDF gerado: {} bytes", baos.size());
        return baos.toByteArray();
    }

    @Transactional(readOnly = true)
    public ExtratoResponse buscarPorId(Long id) {
        String tenantId = TenantContext.getTenantId();
        Extrato extrato = extratoRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Extrato nao encontrado: " + id));

        ExtratoResponse response = new ExtratoResponse();
        response.setId(extrato.getId());
        response.setContaId(extrato.getContaId());
        response.setDataInicio(extrato.getDataInicio());
        response.setDataFim(extrato.getDataFim());
        response.setSaldoAnterior(extrato.getSaldoAnterior());
        response.setSaldoFinal(extrato.getSaldoFinal());
        response.setTotalCreditos(extrato.getTotalCreditos());
        response.setTotalDebitos(extrato.getTotalDebitos());
        response.setQuantidadeMovimentacoes(extrato.getQuantidadeMovimentacoes());
        response.setDataGeracao(extrato.getDataGeracao());
        return response;
    }

    private boolean isCredito(Transacao.TipoTransacao tipo) {
        if (tipo == null) {
            return false;
        }
        return switch (tipo) {
            case TRANSFERENCIA_RECEBIDA, DEPOSITO, CREDITO -> true;
            default -> false;
        };
    }
}
