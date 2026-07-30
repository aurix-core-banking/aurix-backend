package com.aurix.platform.banking.salario.client;

import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CnabParser {

    private static final int LINE_LENGTH = 240;

    public Resultado parse(String arquivoNome, InputStream inputStream) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.length() != LINE_LENGTH) {
                    throw new IllegalArgumentException(
                        "Linha CNAB invalida: esperado 240 caracteres, obtido " + linha.length());
                }
                linhas.add(linha);
            }
        }

        if (linhas.size() < 3) {
            throw new IllegalArgumentException("Arquivo CNAB muito curto: " + linhas.size() + " linhas");
        }

        String header = linhas.get(0);
        String trailer = linhas.get(linhas.size() - 1);

        String codigoBanco = header.substring(0, 3);
        String nomeEmpresa = header.substring(72, 102).trim();
        LocalDate dataGeracao = LocalDate.parse(header.substring(144, 152),
            DateTimeFormatter.ofPattern("ddMMyyyy"));
        int totalFuncionarios = Integer.parseInt(trailer.substring(17, 23).trim());
        BigDecimal valorTotal = new BigDecimal(trailer.substring(23, 39).trim())
            .divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_EVEN);

        List<Detalhe> detalhes = new ArrayList<>();
        for (int i = 1; i < linhas.size() - 1; i++) {
            String det = linhas.get(i);
            String segmento = det.substring(13, 14);
            if ("A".equals(segmento)) {
                String cpf = det.substring(30, 41).trim();
                String matricula = det.substring(66, 86).trim();
                BigDecimal valor = new BigDecimal(det.substring(120, 135).trim())
                    .divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_EVEN);
                detalhes.add(new Detalhe(cpf, matricula, valor));
            }
        }

        return new Resultado(codigoBanco, nomeEmpresa, dataGeracao, totalFuncionarios,
            valorTotal, detalhes, arquivoNome);
    }

    public record Resultado(
        String codigoBanco,
        String nomeEmpresa,
        LocalDate dataGeracao,
        int totalFuncionarios,
        BigDecimal valorTotal,
        List<Detalhe> detalhes,
        String arquivoNome
    ) {}

    public record Detalhe(
        String cpf,
        String matricula,
        BigDecimal valor
    ) {}
}
