package com.aurix.platform.banking.poupanca.service;

import com.aurix.platform.banking.poupanca.dto.ExtratoResponse;
import com.aurix.platform.banking.poupanca.dto.ExtratoResponse.MovimentacaoItem;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExtratoPdfService {

    private static final Logger log = LoggerFactory.getLogger(ExtratoPdfService.class);

    private final MovimentacaoService movimentacaoService;

    public ExtratoPdfService(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    public byte[] gerarPdf(Long contaId, LocalDateTime inicio, LocalDateTime fim) {
        ExtratoResponse extrato = movimentacaoService.gerarExtrato(contaId, inicio, fim);
        return gerarConteudoPdf(extrato);
    }

    private static final DateTimeFormatter DATA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATA_HORA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private byte[] gerarConteudoPdf(ExtratoResponse extrato) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (var writer = new java.io.PrintWriter(baos, false, StandardCharsets.UTF_8)) {
            writer.println("=== EXTRATO CONTA POUPANCA ===");
            writer.println("Conta: " + extrato.getNumeroConta());
            writer.println("Periodo: " + extrato.getDataInicio().format(DATA_FMT)
                + " a " + extrato.getDataFim().format(DATA_FMT));
            writer.println("Saldo atual: R$ " + extrato.getSaldoAtual());
            writer.println("Rendimento periodo: R$ " + extrato.getRendimentoPeriodo());
            writer.println("---");
            writer.println(String.format("%-25s %-40s %10s %10s", "Data", "Descricao", "Valor", "Saldo"));
            writer.println("---");
            for (MovimentacaoItem item : extrato.getMovimentacoes()) {
                writer.println(String.format("%-25s %-40s %10.2f %10.2f",
                    item.getData().format(DATA_HORA_FMT),
                    item.getDescricao(), item.getValor(), item.getSaldo()));
            }
            writer.println("---");
            writer.flush();
        }
        log.info("PDF gerado para conta {} ({} bytes)", extrato.getContaId(), baos.size());
        return baos.toByteArray();
    }
}
