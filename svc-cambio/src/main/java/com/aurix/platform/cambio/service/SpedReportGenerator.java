package com.aurix.platform.cambio.service;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SpedReportGenerator {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SpedReportGenerator.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final String SEP = "|";

    public String gerarEcd(LocalDate dataReferencia, String cnpj, List<String> linhasContabeis) {
        StringBuilder sb = new StringBuilder();
        sb.append("|0000|001|0|").append(dataReferencia.format(DATE_FORMAT)).append("|").append(cnpj).append("|ECD|1.0|\n");
        for (String linha : linhasContabeis) {
            sb.append("|0010|").append(linha).append("|\n");
        }
        sb.append("|9999|").append(linhasContabeis.size()).append("|\n");
        return sb.toString();
    }

    public String gerarEcf(LocalDate dataReferencia, String cnpj, List<String> linhasDemonstrativo) {
        StringBuilder sb = new StringBuilder();
        sb.append("|0000|001|0|").append(dataReferencia.format(DATE_FORMAT)).append("|").append(cnpj).append("|ECF|1.0|\n");
        for (String linha : linhasDemonstrativo) {
            sb.append("|0100|").append(linha).append("|\n");
        }
        sb.append("|9999|").append(linhasDemonstrativo.size()).append("|\n");
        return sb.toString();
    }

    public String gerarEfdReinf(LocalDate dataReferencia, String cnpj, List<String> linhasReinf) {
        StringBuilder sb = new StringBuilder();
        sb.append("|9001|").append(dataReferencia.format(DATE_FORMAT)).append("|").append(cnpj).append("|REINF|1.0|\n");
        for (String linha : linhasReinf) {
            sb.append("|9010|").append(linha).append("|\n");
        }
        sb.append("|9999|").append(linhasReinf.size()).append("|\n");
        return sb.toString();
    }

    public List<String> obterLinhasEcdPadrao() {
        List<String> linhas = new ArrayList<>();
        linhas.add("1.1.01.001|0,00|D");
        linhas.add("2.1.01.001|0,00|C");
        return linhas;
    }

    public List<String> obterLinhasEcfPadrao() {
        List<String> linhas = new ArrayList<>();
        linhas.add("RECEITA|0,00");
        linhas.add("DESPESA|0,00");
        return linhas;
    }

    public List<String> obterLinhasReinfPadrao() {
        List<String> linhas = new ArrayList<>();
        linhas.add("EVENTO|0,00");
        return linhas;
    }
}
