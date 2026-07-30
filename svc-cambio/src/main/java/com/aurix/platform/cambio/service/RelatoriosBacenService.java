package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.entity.RelatorioBacen;
import com.aurix.platform.cambio.repository.RelatorioBacenRepository;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class RelatoriosBacenService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RelatoriosBacenService.class);
    private final RelatorioBacenRepository relatorioBacenRepository;
    private final CosifReportGenerator cosifReportGenerator;
    private final EFinanceiraReportGenerator eFinanceiraReportGenerator;
    private final ScrCcsReportGenerator scrCcsReportGenerator;
    private final SpedReportGenerator spedReportGenerator;
    private final BacenJudReportGenerator bacenJudReportGenerator;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public static final String COSIF_PREFIX = "COSIF";
    public static final String PIX_PREFIX = "PIX";
    public static final String CREDITO_PREFIX = "CREDITO";
    public static final String RISCO_PREFIX = "RISCO";
    public static final String LIQUIDEZ_PREFIX = "LIQ";
    public static final String EFINANCEIRA_PREFIX = "EFINANCEIRA";
    public static final String SCR_CCS_PREFIX = "SCR_CCS";
    public static final String SPED_ECD_PREFIX = "SPED_ECD";
    public static final String SPED_ECF_PREFIX = "SPED_ECF";
    public static final String SPED_REINF_PREFIX = "SPED_REINF";
    public static final String BACEN_JUD_PREFIX = "BACEN_JUD";

    public RelatorioBacen agendarRelatorio(String codigoRelatorio, String nomeRelatorio, RelatorioBacen.CategoriaRelatorio categoria, LocalDate dataReferencia, String periodicidade) {
        RelatorioBacen rel = RelatorioBacen.builder().codigoRelatorio(codigoRelatorio).nomeRelatorio(nomeRelatorio).categoria(categoria).tipoRelatorio(RelatorioBacen.TipoRelatorio.OBRIGATORIO).status(RelatorioBacen.StatusRelatorio.PENDENTE).dataReferencia(dataReferencia).dataVencimento(calcularVencimentoPadrao(dataReferencia, periodicidade)).periodicidade(periodicidade).build();
        return relatorioBacenRepository.save(rel);
    }

    public RelatorioBacen gerarRelatorioCOSIF(LocalDate dataReferencia) {
        String codigo = COSIF_PREFIX + "-" + dataReferencia;
        Optional<RelatorioBacen> existente = relatorioBacenRepository.findByCodigoRelatorioAndDataReferencia(codigo, dataReferencia);
        if (existente.isPresent()) {
            return existente.get();
        }
        RelatorioBacen rel = RelatorioBacen.builder().codigoRelatorio(codigo).nomeRelatorio("Plano Contábil das Instituições do SFN - COSIF").descricao("Relatório COSIF para data de referência " + dataReferencia).tipoRelatorio(RelatorioBacen.TipoRelatorio.OBRIGATORIO).categoria(RelatorioBacen.CategoriaRelatorio.OUTROS).status(RelatorioBacen.StatusRelatorio.GERANDO).dataReferencia(dataReferencia).dataVencimento(calcularVencimentoPadrao(dataReferencia, "MENSAL")).periodicidade("MENSAL").versaoFormato("COSIF-2024").build();
        rel = relatorioBacenRepository.save(rel);
        try {
            String cnpjInstituicao = "12345678000190";
            List<CosifReportGenerator.ContaCosif> contas = cosifReportGenerator.obterContasCosifPadrao();
            String conteudoCOSIF = cosifReportGenerator.gerarRelatorioCOSIF(dataReferencia, cnpjInstituicao, contas);
            rel.setArquivoGerado("cosif_" + dataReferencia + ".txt");
            rel.setStatus(RelatorioBacen.StatusRelatorio.GERADO);
            rel.setTamanhoArquivo((long) conteudoCOSIF.length());
            rel.setHashArquivo(calcularHashArquivo(conteudoCOSIF));
        } catch (Exception e) {
            log.error("Erro ao gerar COSIF: {}", e.getMessage());
            rel.setStatus(RelatorioBacen.StatusRelatorio.REJEITADO);
            rel.setErrosValidacao(e.getMessage());
        }
        return relatorioBacenRepository.save(rel);
    }

    public RelatorioBacen gerarRelatorioPIX(LocalDate dataReferencia) {
        String codigo = PIX_PREFIX + "-" + dataReferencia;
        Optional<RelatorioBacen> existente = relatorioBacenRepository.findByCodigoRelatorioAndDataReferencia(codigo, dataReferencia);
        if (existente.isPresent()) {
            return existente.get();
        }
        RelatorioBacen rel = RelatorioBacen.builder().codigoRelatorio(codigo).nomeRelatorio("Relatório de Operações PIX").descricao("Relatório regulatório de transações PIX - " + dataReferencia).tipoRelatorio(RelatorioBacen.TipoRelatorio.OBRIGATORIO).categoria(RelatorioBacen.CategoriaRelatorio.PIX).status(RelatorioBacen.StatusRelatorio.GERANDO).dataReferencia(dataReferencia).dataVencimento(calcularVencimentoPadrao(dataReferencia, "DIARIO")).periodicidade("DIARIO").versaoFormato("PIX-REG-2024").build();
        rel = relatorioBacenRepository.save(rel);
        try {
            rel.setArquivoGerado("pix_" + dataReferencia + ".txt");
            rel.setStatus(RelatorioBacen.StatusRelatorio.GERADO);
        } catch (Exception e) {
            log.error("Erro ao gerar relatório PIX: {}", e.getMessage());
            rel.setStatus(RelatorioBacen.StatusRelatorio.REJEITADO);
            rel.setErrosValidacao(e.getMessage());
        }
        return relatorioBacenRepository.save(rel);
    }

    public RelatorioBacen gerarRelatorioCredito(LocalDate dataReferencia) {
        String codigo = CREDITO_PREFIX + "-" + dataReferencia;
        Optional<RelatorioBacen> existente = relatorioBacenRepository.findByCodigoRelatorioAndDataReferencia(codigo, dataReferencia);
        if (existente.isPresent()) {
            return existente.get();
        }
        RelatorioBacen rel = RelatorioBacen.builder().codigoRelatorio(codigo).nomeRelatorio("Relatório de Crédito").descricao("Relatório de operações de crédito - " + dataReferencia).tipoRelatorio(RelatorioBacen.TipoRelatorio.OBRIGATORIO).categoria(RelatorioBacen.CategoriaRelatorio.CREDITO).status(RelatorioBacen.StatusRelatorio.GERANDO).dataReferencia(dataReferencia).dataVencimento(calcularVencimentoPadrao(dataReferencia, "MENSAL")).periodicidade("MENSAL").versaoFormato("CRED-2024").build();
        rel = relatorioBacenRepository.save(rel);
        try {
            rel.setArquivoGerado("credito_" + dataReferencia + ".txt");
            rel.setStatus(RelatorioBacen.StatusRelatorio.GERADO);
        } catch (Exception e) {
            log.error("Erro ao gerar relatório de crédito: {}", e.getMessage());
            rel.setStatus(RelatorioBacen.StatusRelatorio.REJEITADO);
            rel.setErrosValidacao(e.getMessage());
        }
        return relatorioBacenRepository.save(rel);
    }

    public RelatorioBacen marcarComoEnviado(Long relatorioId, String protocoloBacen) {
        RelatorioBacen rel = relatorioBacenRepository.findById(relatorioId).orElseThrow(() -> new RuntimeException("Relatório não encontrado"));
        rel.setStatus(RelatorioBacen.StatusRelatorio.ENVIADO);
        rel.setDataEnvio(LocalDate.now());
        rel.setProtocoloBacen(protocoloBacen);
        RelatorioBacen saved = relatorioBacenRepository.save(rel);

        Map<String, Object> event = new LinkedHashMap<>();
        event.put("eventId", java.util.UUID.randomUUID().toString());
        event.put("relatorioId", saved.getId());
        event.put("codigoRelatorio", saved.getCodigoRelatorio());
        event.put("protocoloBacen", protocoloBacen);
        event.put("status", saved.getStatus().name());
        event.put("dataEnvio", saved.getDataEnvio().toString());
        kafkaTemplate.send(Topics.RELATORIO_ENVIADO, String.valueOf(saved.getId()), event);

        return saved;
    }

    public List<RelatorioBacen> listarPendentes() {
        return relatorioBacenRepository.findPendentesComVencimentoProximo(LocalDate.now());
    }

    public List<RelatorioBacen> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return relatorioBacenRepository.findByPeriodo(inicio, fim);
    }

    public RelatorioBacen gerarRelatorioEFinanceira(LocalDate dataReferencia) {
        return gerarRelatorioGenerico(EFINANCEIRA_PREFIX, "E-Financeira (Receita Federal)", RelatorioBacen.CategoriaRelatorio.E_FINANCEIRA, dataReferencia, "MENSAL", "EFIN-2024", () -> {
            String cnpj = "12345678000190";
            String conteudo = eFinanceiraReportGenerator.gerar(dataReferencia, cnpj, eFinanceiraReportGenerator.obterLinhasPadrao());
            return new ConteudoRelatorio("efin_" + dataReferencia + ".txt", conteudo);
        });
    }

    public RelatorioBacen gerarRelatorioScrCcs(LocalDate dataReferencia) {
        return gerarRelatorioGenerico(SCR_CCS_PREFIX, "SCR/CCS - Central de Riscos", RelatorioBacen.CategoriaRelatorio.SCR_CCS, dataReferencia, "MENSAL", "SCR-2024", () -> {
            String cnpj = "12345678000190";
            String conteudo = scrCcsReportGenerator.gerar(dataReferencia, cnpj, scrCcsReportGenerator.obterRegistrosPadrao());
            return new ConteudoRelatorio("scr_ccs_" + dataReferencia + ".txt", conteudo);
        });
    }

    public RelatorioBacen gerarRelatorioSpedEcd(LocalDate dataReferencia) {
        return gerarRelatorioGenerico(SPED_ECD_PREFIX, "SPED ECD - Escrituracao Contabil Digital", RelatorioBacen.CategoriaRelatorio.SPED, dataReferencia, "MENSAL", "ECD-2024", () -> {
            String cnpj = "12345678000190";
            String conteudo = spedReportGenerator.gerarEcd(dataReferencia, cnpj, spedReportGenerator.obterLinhasEcdPadrao());
            return new ConteudoRelatorio("sped_ecd_" + dataReferencia + ".txt", conteudo);
        });
    }

    public RelatorioBacen gerarRelatorioSpedEcf(LocalDate dataReferencia) {
        return gerarRelatorioGenerico(SPED_ECF_PREFIX, "SPED ECF - Escrituracao Contabil Fiscal", RelatorioBacen.CategoriaRelatorio.SPED, dataReferencia, "ANUAL", "ECF-2024", () -> {
            String cnpj = "12345678000190";
            String conteudo = spedReportGenerator.gerarEcf(dataReferencia, cnpj, spedReportGenerator.obterLinhasEcfPadrao());
            return new ConteudoRelatorio("sped_ecf_" + dataReferencia + ".txt", conteudo);
        });
    }

    public RelatorioBacen gerarRelatorioSpedReinf(LocalDate dataReferencia) {
        return gerarRelatorioGenerico(SPED_REINF_PREFIX, "SPED EFD-Reinf", RelatorioBacen.CategoriaRelatorio.SPED, dataReferencia, "MENSAL", "REINF-2024", () -> {
            String cnpj = "12345678000190";
            String conteudo = spedReportGenerator.gerarEfdReinf(dataReferencia, cnpj, spedReportGenerator.obterLinhasReinfPadrao());
            return new ConteudoRelatorio("sped_reinf_" + dataReferencia + ".txt", conteudo);
        });
    }

    public RelatorioBacen gerarRelatorioBacenJud(LocalDate dataReferencia) {
        return gerarRelatorioGenerico(BACEN_JUD_PREFIX, "BACEN Jud - Bloqueios Judiciais", RelatorioBacen.CategoriaRelatorio.BACEN_JUD, dataReferencia, "DIARIO", "BACENJUD-2024", () -> {
            String cnpj = "12345678000190";
            String conteudo = bacenJudReportGenerator.gerar(dataReferencia, cnpj, bacenJudReportGenerator.obterBloqueiosPadrao());
            return new ConteudoRelatorio("bacen_jud_" + dataReferencia + ".txt", conteudo);
        });
    }

    private RelatorioBacen gerarRelatorioGenerico(String prefix, String nome, RelatorioBacen.CategoriaRelatorio categoria, LocalDate dataReferencia, String periodicidade, String versaoFormato, java.util.function.Supplier<ConteudoRelatorio> gerador) {
        String codigo = prefix + "-" + dataReferencia;
        Optional<RelatorioBacen> existente = relatorioBacenRepository.findByCodigoRelatorioAndDataReferencia(codigo, dataReferencia);
        if (existente.isPresent()) {
            return existente.get();
        }
        RelatorioBacen rel = RelatorioBacen.builder().codigoRelatorio(codigo).nomeRelatorio(nome).tipoRelatorio(RelatorioBacen.TipoRelatorio.OBRIGATORIO).categoria(categoria).status(RelatorioBacen.StatusRelatorio.GERANDO).dataReferencia(dataReferencia).dataVencimento(calcularVencimentoPadrao(dataReferencia, periodicidade)).periodicidade(periodicidade).versaoFormato(versaoFormato).build();
        rel = relatorioBacenRepository.save(rel);
        try {
            ConteudoRelatorio c = gerador.get();
            rel.setArquivoGerado(c.nomeArquivo);
            rel.setStatus(RelatorioBacen.StatusRelatorio.GERADO);
            rel.setTamanhoArquivo((long) c.conteudo.length());
            rel.setHashArquivo(calcularHashArquivo(c.conteudo));

            Map<String, Object> event = new LinkedHashMap<>();
            event.put("eventId", java.util.UUID.randomUUID().toString());
            event.put("relatorioId", rel.getId());
            event.put("codigoRelatorio", rel.getCodigoRelatorio());
            event.put("nomeRelatorio", rel.getNomeRelatorio());
            event.put("status", rel.getStatus().name());
            event.put("dataReferencia", rel.getDataReferencia().toString());
            kafkaTemplate.send(Topics.RELATORIO_GERADO, String.valueOf(rel.getId()), event);
        } catch (Exception e) {
            log.error("Erro ao gerar relatorio {}: {}", prefix, e.getMessage());
            rel.setStatus(RelatorioBacen.StatusRelatorio.REJEITADO);
            rel.setErrosValidacao(e.getMessage());
        }
        return relatorioBacenRepository.save(rel);
    }


    private static class ConteudoRelatorio {
        final String nomeArquivo;
        final String conteudo;

        ConteudoRelatorio(String nomeArquivo, String conteudo) {
            this.nomeArquivo = nomeArquivo;
            this.conteudo = conteudo;
        }
    }

    public java.util.Map<String, Object> obterDashboardStatus() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicio = hoje.minusMonths(1);
        LocalDate fim = hoje.plusMonths(1);
        List<RelatorioBacen> todos = relatorioBacenRepository.findByPeriodo(inicio, fim);
        long pendentes = todos.stream().filter(r -> r.getStatus() == RelatorioBacen.StatusRelatorio.PENDENTE).count();
        long gerados = todos.stream().filter(r -> r.getStatus() == RelatorioBacen.StatusRelatorio.GERADO).count();
        long enviados = todos.stream().filter(r -> r.getStatus() == RelatorioBacen.StatusRelatorio.ENVIADO).count();
        long rejeitados = todos.stream().filter(r -> r.getStatus() == RelatorioBacen.StatusRelatorio.REJEITADO).count();
        List<RelatorioBacen> ultimos = relatorioBacenRepository.findByPeriodo(hoje.minusDays(30), hoje);
        java.util.Map<String, Object> map = new java.util.LinkedHashMap<>();
        map.put("pendentes", pendentes);
        map.put("gerados", gerados);
        map.put("enviados", enviados);
        map.put("rejeitados", rejeitados);
        map.put("ultimosRelatorios", ultimos.stream().limit(20).toList());
        map.put("periodo", inicio + " a " + fim);
        return map;
    }

    private LocalDate calcularVencimentoPadrao(LocalDate dataReferencia, String periodicidade) {
        if ("DIARIO".equals(periodicidade)) {
            return dataReferencia.plusDays(1);
        }
        if ("MENSAL".equals(periodicidade)) {
            return dataReferencia.plusMonths(1).withDayOfMonth(10);
        }
        if ("TRIMESTRAL".equals(periodicidade)) {
            return dataReferencia.plusMonths(3).withDayOfMonth(15);
        }
        return dataReferencia.plusDays(15);
    }

    private String calcularHashArquivo(String conteudo) {
        return "hash_" + conteudo.hashCode();
    }

    @java.lang.SuppressWarnings("all")
    public RelatoriosBacenService(final RelatorioBacenRepository relatorioBacenRepository, final CosifReportGenerator cosifReportGenerator, final EFinanceiraReportGenerator eFinanceiraReportGenerator, final ScrCcsReportGenerator scrCcsReportGenerator, final SpedReportGenerator spedReportGenerator, final BacenJudReportGenerator bacenJudReportGenerator, final KafkaTemplate<String, Object> kafkaTemplate) {
        this.relatorioBacenRepository = relatorioBacenRepository;
        this.cosifReportGenerator = cosifReportGenerator;
        this.eFinanceiraReportGenerator = eFinanceiraReportGenerator;
        this.scrCcsReportGenerator = scrCcsReportGenerator;
        this.spedReportGenerator = spedReportGenerator;
        this.bacenJudReportGenerator = bacenJudReportGenerator;
        this.kafkaTemplate = kafkaTemplate;
    }
}
