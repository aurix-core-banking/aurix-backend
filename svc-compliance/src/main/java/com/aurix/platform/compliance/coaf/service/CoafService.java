package com.aurix.platform.compliance.coaf.service;

import com.aurix.platform.compliance.coaf.entity.CoafNotificacao;
import com.aurix.platform.compliance.coaf.entity.CoafRelatorio;
import com.aurix.platform.compliance.coaf.repository.CoafNotificacaoRepository;
import com.aurix.platform.compliance.coaf.repository.CoafRelatorioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoafService {

    private static final Logger log = LoggerFactory.getLogger(CoafService.class);
    private static final int PRAZO_NOTIFICACAO_HORAS = 24;
    private static final BigDecimal LIMITE_VALOR_SUSPEITO = new BigDecimal("10000.00");

    private final CoafNotificacaoRepository notificacaoRepository;
    private final CoafRelatorioRepository relatorioRepository;

    public CoafService(CoafNotificacaoRepository notificacaoRepository,
                       CoafRelatorioRepository relatorioRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.relatorioRepository = relatorioRepository;
    }

    public CoafNotificacao notificar(CoafNotificacao notificacao) {
        log.info("Enviando notificacao COAF para cliente: {}", notificacao.getCpfCnpj());

        notificacao.setCodigoNotificacao("COAF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        notificacao.setDataNotificacao(LocalDateTime.now());
        notificacao.setPrazoNotificacao(LocalDateTime.now().plusHours(PRAZO_NOTIFICACAO_HORAS));
        notificacao.setStatus(CoafNotificacao.StatusNotificacaoCoaf.PENDENTE);

        boolean ehSuspeita = verificarTransacaoSuspeita(notificacao);
        if (ehSuspeita) {
            log.warn("Transacao suspeita detectada: cliente={}, valor={}",
                notificacao.getCpfCnpj(), notificacao.getValorOperacao());
        }

        notificacao.setDentroPrazo(true);
        notificacao.setDiasRestantes(PRAZO_NOTIFICACAO_HORAS);

        notificacao.setXmlNotificacao(gerarXmlCoaf(notificacao));

        CoafNotificacao salva = notificacaoRepository.save(notificacao);
        log.info("Notificacao COAF registrada: codigo={}, prazo={}",
            salva.getCodigoNotificacao(), salva.getPrazoNotificacao());
        return salva;
    }

    @Transactional(readOnly = true)
    public List<CoafNotificacao> listarNotificacoes() {
        return notificacaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CoafNotificacao> listarNotificacoesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return notificacaoRepository.findByPeriodo(inicio, fim);
    }

    @Transactional(readOnly = true)
    public List<CoafNotificacao> listarNotificacoesAtrasadas() {
        return notificacaoRepository.findNotificacoesAtrasadas(LocalDateTime.now());
    }

    public CoafRelatorio gerarRelatorio(LocalDateTime dataInicio, LocalDateTime dataFim,
                                         CoafRelatorio.TipoRelatorioCoaf tipo) {
        log.info("Gerando relatorio COAF: tipo={}, periodo={}/{}", tipo, dataInicio, dataFim);

        List<CoafNotificacao> notificacoes = notificacaoRepository.findByPeriodo(dataInicio, dataFim);

        CoafRelatorio relatorio = new CoafRelatorio();
        relatorio.setCodigoRelatorio("REL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        relatorio.setTipoRelatorio(tipo);
        relatorio.setStatus(CoafRelatorio.StatusRelatorioCoaf.GERADO);
        relatorio.setDataInicioPeriodo(dataInicio);
        relatorio.setDataFimPeriodo(dataFim);
        relatorio.setDataGeracao(LocalDateTime.now());
        relatorio.setTotalNotificacoes(notificacoes.size());
        relatorio.setTotalTransacoesAnalisadas(notificacoes.size());

        String resumoEstatistico = gerarResumoEstatistico(notificacoes);
        relatorio.setResumoEstatistico(resumoEstatistico);

        String conteudo = gerarConteudoRelatorio(notificacoes, relatorio);
        relatorio.setConteudoRelatorio(conteudo);

        String xml = gerarXmlRelatorio(relatorio, notificacoes);
        relatorio.setXmlRelatorio(xml);

        relatorio.setHashRelatorio(gerarHash(xml));

        CoafRelatorio salvo = relatorioRepository.save(relatorio);
        log.info("Relatorio COAF gerado: codigo={}, totalNotificacoes={}",
            salvo.getCodigoRelatorio(), salvo.getTotalNotificacoes());
        return salvo;
    }

    @Transactional(readOnly = true)
    public List<CoafRelatorio> listarRelatorios() {
        return relatorioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CoafRelatorio> listarRelatoriosPendentesEnvio() {
        return relatorioRepository.findRelatoriosPendentesEnvio();
    }

    public CoafRelatorio enviarRelatorio(String codigoRelatorio) {
        CoafRelatorio relatorio = relatorioRepository.findByCodigoRelatorio(codigoRelatorio)
            .orElseThrow(() -> new IllegalArgumentException("Relatorio nao encontrado: " + codigoRelatorio));
        relatorio.setStatus(CoafRelatorio.StatusRelatorioCoaf.ENVIADO);
        relatorio.setDataEnvio(LocalDateTime.now());
        CoafRelatorio salvo = relatorioRepository.save(relatorio);
        log.info("Relatorio COAF enviado: codigo={}", codigoRelatorio);
        return salvo;
    }

    public void verificarPrazos() {
        List<CoafNotificacao> atrasadas = notificacaoRepository.findNotificacoesAtrasadas(LocalDateTime.now());
        for (CoafNotificacao notificacao : atrasadas) {
            notificacao.setStatus(CoafNotificacao.StatusNotificacaoCoaf.FORA_PRAZO);
            notificacao.setDentroPrazo(false);
            notificacaoRepository.save(notificacao);
            log.warn("Notificacao COAF fora do prazo: codigo={}", notificacao.getCodigoNotificacao());
        }
    }

    private boolean verificarTransacaoSuspeita(CoafNotificacao notificacao) {
        if (notificacao.getValorOperacao() != null &&
            notificacao.getValorOperacao().compareTo(LIMITE_VALOR_SUSPEITO) > 0) {
            return true;
        }
        if (notificacao.getTipoNotificacao() == CoafNotificacao.TipoNotificacaoCoaf.JURISDICAO_RISCO) {
            return true;
        }
        return notificacao.getTipoNotificacao() == CoafNotificacao.TipoNotificacaoCoaf.OPERACAO_INCOMUM;
    }

    private String gerarXmlCoaf(CoafNotificacao notificacao) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<notificacaoCOAF>\n");
        xml.append("  <codigoNotificacao>").append(notificacao.getCodigoNotificacao()).append("</codigoNotificacao>\n");
        xml.append("  <cpfCnpj>").append(notificacao.getCpfCnpj()).append("</cpfCnpj>\n");
        xml.append("  <nomeCliente>").append(notificacao.getNomeCliente() != null ? notificacao.getNomeCliente() : "").append("</nomeCliente>\n");
        xml.append("  <tipoNotificacao>").append(notificacao.getTipoNotificacao()).append("</tipoNotificacao>\n");
        xml.append("  <dataOcorrencia>").append(notificacao.getDataOcorrencia()).append("</dataOcorrencia>\n");
        xml.append("  <valorOperacao>").append(notificacao.getValorOperacao() != null ? notificacao.getValorOperacao() : "0.00").append("</valorOperacao>\n");
        xml.append("  <moeda>").append(notificacao.getMoeda()).append("</moeda>\n");
        xml.append("  <tipoOperacao>").append(notificacao.getTipoOperacao() != null ? notificacao.getTipoOperacao() : "").append("</tipoOperacao>\n");
        xml.append("  <motivoSuspeita>").append(notificacao.getMotivoSuspeita() != null ? notificacao.getMotivoSuspeita() : "").append("</motivoSuspeita>\n");
        xml.append("  <descricaoOperacao>").append(notificacao.getDescricaoOperacao() != null ? notificacao.getDescricaoOperacao() : "").append("</descricaoOperacao>\n");
        xml.append("</notificacaoCOAF>");
        return xml.toString();
    }

    private String gerarResumoEstatistico(List<CoafNotificacao> notificacoes) {
        long total = notificacoes.size();
        long pendentes = notificacoes.stream()
            .filter(n -> n.getStatus() == CoafNotificacao.StatusNotificacaoCoaf.PENDENTE).count();
        long enviadas = notificacoes.stream()
            .filter(n -> n.getStatus() == CoafNotificacao.StatusNotificacaoCoaf.ENVIADA).count();

        BigDecimal valorTotal = notificacoes.stream()
            .map(n -> n.getValorOperacao() != null ? n.getValorOperacao() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return "{\"total\":" + total +
            ",\"pendentes\":" + pendentes +
            ",\"enviadas\":" + enviadas +
            ",\"valorTotal\":" + valorTotal + "}";
    }

    private String gerarConteudoRelatorio(List<CoafNotificacao> notificacoes, CoafRelatorio relatorio) {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATORIO DE SUSPEITAS - COAF\n");
        sb.append("Periodo: ").append(relatorio.getDataInicioPeriodo()).append(" a ").append(relatorio.getDataFimPeriodo()).append("\n");
        sb.append("Total de notificacoes: ").append(relatorio.getTotalNotificacoes()).append("\n\n");
        for (CoafNotificacao n : notificacoes) {
            sb.append("Notificacao: ").append(n.getCodigoNotificacao()).append("\n");
            sb.append("  Cliente: ").append(n.getCpfCnpj()).append("\n");
            sb.append("  Tipo: ").append(n.getTipoNotificacao()).append("\n");
            sb.append("  Valor: ").append(n.getValorOperacao()).append("\n");
            sb.append("  Motivo: ").append(n.getMotivoSuspeita()).append("\n\n");
        }
        return sb.toString();
    }

    private String gerarXmlRelatorio(CoafRelatorio relatorio, List<CoafNotificacao> notificacoes) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<relatorioCOAF>\n");
        xml.append("  <codigoRelatorio>").append(relatorio.getCodigoRelatorio()).append("</codigoRelatorio>\n");
        xml.append("  <tipoRelatorio>").append(relatorio.getTipoRelatorio()).append("</tipoRelatorio>\n");
        xml.append("  <periodo>\n");
        xml.append("    <inicio>").append(relatorio.getDataInicioPeriodo()).append("</inicio>\n");
        xml.append("    <fim>").append(relatorio.getDataFimPeriodo()).append("</fim>\n");
        xml.append("  </periodo>\n");
        xml.append("  <totalNotificacoes>").append(relatorio.getTotalNotificacoes()).append("</totalNotificacoes>\n");
        xml.append("  <notificacoes>\n");
        for (CoafNotificacao n : notificacoes) {
            xml.append("    <notificacao>\n");
            xml.append("      <codigo>").append(n.getCodigoNotificacao()).append("</codigo>\n");
            xml.append("      <cpfCnpj>").append(n.getCpfCnpj()).append("</cpfCnpj>\n");
            xml.append("      <tipo>").append(n.getTipoNotificacao()).append("</tipo>\n");
            xml.append("      <valor>").append(n.getValorOperacao()).append("</valor>\n");
            xml.append("    </notificacao>\n");
        }
        xml.append("  </notificacoes>\n");
        xml.append("</relatorioCOAF>");
        return xml.toString();
    }

    private String gerarHash(String conteudo) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(conteudo.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString();
        }
    }
}
