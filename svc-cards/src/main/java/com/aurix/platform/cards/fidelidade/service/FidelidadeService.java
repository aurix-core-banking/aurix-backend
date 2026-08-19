package com.aurix.platform.cards.fidelidade.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class FidelidadeService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FidelidadeService.class);

    private static final int PONTOS_POR_REAL = 1;
    private static final int MESES_VALIDADE_PONTOS = 24;
    private static final BigDecimal TAXA_MILHAS = new BigDecimal("0.80");
    private static final BigDecimal TAXA_CASHBACK = new BigDecimal("0.05");

    private final Map<Long, ContaFidelidade> contas = new HashMap<>();
    private final Map<Long, List<MovimentacaoPontos>> movimentacoes = new HashMap<>();
    private static final AtomicLong ID_COUNTER = new AtomicLong(System.currentTimeMillis());

    public Map<String, Object> consultarPontos(Long contaId) {
        ContaFidelidade conta = contas.computeIfAbsent(contaId, k -> novaContaFidelidade(contaId));
        expirarPontos(conta);
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("contaId", contaId);
        resultado.put("pontosDisponiveis", conta.getPontosDisponiveis());
        resultado.put("pontosExpiradosMes", conta.getPontosExpiradosMes());
        resultado.put("totalAcumulado", conta.getTotalAcumulado());
        resultado.put("totalResgatado", conta.getTotalResgatado());
        return resultado;
    }

    public Map<String, Object> resgatar(Long contaId, Integer pontos, String tipoResgate, String parceiro) {
        ContaFidelidade conta = contas.computeIfAbsent(contaId, k -> novaContaFidelidade(contaId));
        expirarPontos(conta);

        if (pontos <= 0) {
            throw new RuntimeException("Quantidade de pontos deve ser maior que zero");
        }
        if (conta.getPontosDisponiveis() < pontos) {
            throw new RuntimeException("Saldo insuficiente. Disponível: " + conta.getPontosDisponiveis());
        }

        BigDecimal valorResgate = switch (tipoResgate.toUpperCase()) {
            case "MILHAS" -> BigDecimal.valueOf(pontos).multiply(TAXA_MILHAS).setScale(2, RoundingMode.HALF_UP);
            case "CASHBACK" -> BigDecimal.valueOf(pontos).multiply(TAXA_CASHBACK).setScale(2, RoundingMode.HALF_UP);
            case "PRODUTOS" -> BigDecimal.valueOf(pontos).setScale(2, RoundingMode.HALF_UP);
            default -> throw new RuntimeException("Tipo de resgate inválido. Use: MILHAS, CASHBACK, PRODUTOS");
        };

        conta.setPontosDisponiveis(conta.getPontosDisponiveis() - pontos);
        conta.setTotalResgatado(conta.getTotalResgatado() + pontos);

        MovimentacaoPontos movimentacao = new MovimentacaoPontos();
        movimentacao.setId(ID_COUNTER.incrementAndGet());
        movimentacao.setContaId(contaId);
        movimentacao.setTipo(TipoMovimentacao.RESGATE);
        movimentacao.setPontos(pontos);
        movimentacao.setTipoResgate(tipoResgate);
        movimentacao.setParceiro(parceiro);
        movimentacao.setValorEstimado(valorResgate);
        movimentacao.setData(LocalDateTime.now());
        movimentacoes.computeIfAbsent(contaId, k -> new ArrayList<>()).add(0, movimentacao);

        log.info("Pontos resgatados: contaId={}, pontos={}, tipo={}", contaId, pontos, tipoResgate);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("contaId", contaId);
        resultado.put("pontosResgatados", pontos);
        resultado.put("tipoResgate", tipoResgate);
        resultado.put("parceiro", parceiro);
        resultado.put("valorEstimado", valorResgate);
        resultado.put("saldoRestante", conta.getPontosDisponiveis());
        resultado.put("dataResgate", movimentacao.getData());
        return resultado;
    }

    public List<Map<String, Object>> historico(Long contaId, Integer pagina, Integer tamanhoPagina) {
        List<MovimentacaoPontos> todas = movimentacoes.getOrDefault(contaId, Collections.emptyList());
        int inicio = pagina * tamanhoPagina;
        int fim = Math.min(inicio + tamanhoPagina, todas.size());
        if (inicio >= todas.size()) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (MovimentacaoPontos m : todas.subList(inicio, fim)) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", m.getId());
            item.put("tipo", m.getTipo().name());
            item.put("pontos", m.getPontos());
            item.put("tipoResgate", m.getTipoResgate());
            item.put("parceiro", m.getParceiro());
            item.put("valorEstimado", m.getValorEstimado());
            item.put("data", m.getData());
            resultado.add(item);
        }
        return resultado;
    }

    public void registrarAcumulo(Long contaId, BigDecimal valorTransacao) {
        ContaFidelidade conta = contas.computeIfAbsent(contaId, k -> novaContaFidelidade(contaId));
        int pontosGanhos = valorTransacao.multiply(BigDecimal.valueOf(PONTOS_POR_REAL))
                .setScale(0, RoundingMode.FLOOR).intValue();
        if (pontosGanhos <= 0) {
            return;
        }
        conta.setPontosDisponiveis(conta.getPontosDisponiveis() + pontosGanhos);
        conta.setTotalAcumulado(conta.getTotalAcumulado() + pontosGanhos);

        MovimentacaoPontos movimentacao = new MovimentacaoPontos();
        movimentacao.setId(ID_COUNTER.incrementAndGet());
        movimentacao.setContaId(contaId);
        movimentacao.setTipo(TipoMovimentacao.ACUMULO);
        movimentacao.setPontos(pontosGanhos);
        movimentacao.setData(LocalDateTime.now());
        movimentacoes.computeIfAbsent(contaId, k -> new ArrayList<>()).add(0, movimentacao);

        log.info("Pontos acumulados: contaId={}, pontos={}, valor={}", contaId, pontosGanhos, valorTransacao);
    }

    private void expirarPontos(ContaFidelidade conta) {
        List<MovimentacaoPontos> lista = movimentacoes.getOrDefault(conta.getId(), Collections.emptyList());
        LocalDateTime corte = LocalDateTime.now().minusMonths(MESES_VALIDADE_PONTOS);
        int pontosExpirados = 0;
        for (MovimentacaoPontos m : lista) {
            if (m.getTipo() == TipoMovimentacao.ACUMULO && m.getData().isBefore(corte) && !m.isExpirado()) {
                m.setExpirado(true);
                pontosExpirados += m.getPontos();
            }
        }
        if (pontosExpirados > 0) {
            int novoSaldo = Math.max(0, conta.getPontosDisponiveis() - pontosExpirados);
            conta.setPontosDisponiveis(novoSaldo);
            conta.setPontosExpiradosMes(conta.getPontosExpiradosMes() + pontosExpirados);
            log.info("Pontos expirados: contaId={}, pontos={}", conta.getId(), pontosExpirados);
        }
    }

    private ContaFidelidade novaContaFidelidade(Long contaId) {
        ContaFidelidade conta = new ContaFidelidade();
        conta.setId(contaId);
        conta.setPontosDisponiveis(0);
        conta.setPontosExpiradosMes(0);
        conta.setTotalAcumulado(0);
        conta.setTotalResgatado(0);
        return conta;
    }

    public enum TipoMovimentacao {
        ACUMULO, RESGATE, EXPIRACAO, BONUS
    }

    public static class ContaFidelidade {
        private Long id;
        private Integer pontosDisponiveis;
        private Integer pontosExpiradosMes;
        private Integer totalAcumulado;
        private Integer totalResgatado;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Integer getPontosDisponiveis() { return pontosDisponiveis; }
        public void setPontosDisponiveis(Integer pontosDisponiveis) { this.pontosDisponiveis = pontosDisponiveis; }
        public Integer getPontosExpiradosMes() { return pontosExpiradosMes; }
        public void setPontosExpiradosMes(Integer pontosExpiradosMes) { this.pontosExpiradosMes = pontosExpiradosMes; }
        public Integer getTotalAcumulado() { return totalAcumulado; }
        public void setTotalAcumulado(Integer totalAcumulado) { this.totalAcumulado = totalAcumulado; }
        public Integer getTotalResgatado() { return totalResgatado; }
        public void setTotalResgatado(Integer totalResgatado) { this.totalResgatado = totalResgatado; }
    }

    public static class MovimentacaoPontos {
        private Long id;
        private Long contaId;
        private TipoMovimentacao tipo;
        private Integer pontos;
        private String tipoResgate;
        private String parceiro;
        private BigDecimal valorEstimado;
        private LocalDateTime data;
        private boolean expirado;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getContaId() { return contaId; }
        public void setContaId(Long contaId) { this.contaId = contaId; }
        public TipoMovimentacao getTipo() { return tipo; }
        public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }
        public Integer getPontos() { return pontos; }
        public void setPontos(Integer pontos) { this.pontos = pontos; }
        public String getTipoResgate() { return tipoResgate; }
        public void setTipoResgate(String tipoResgate) { this.tipoResgate = tipoResgate; }
        public String getParceiro() { return parceiro; }
        public void setParceiro(String parceiro) { this.parceiro = parceiro; }
        public BigDecimal getValorEstimado() { return valorEstimado; }
        public void setValorEstimado(BigDecimal valorEstimado) { this.valorEstimado = valorEstimado; }
        public LocalDateTime getData() { return data; }
        public void setData(LocalDateTime data) { this.data = data; }
        public boolean isExpirado() { return expirado; }
        public void setExpirado(boolean expirado) { this.expirado = expirado; }
    }

    private static class AtomicLong {
        private long value;
        AtomicLong(long init) { this.value = init; }
        synchronized long incrementAndGet() { return ++value; }
    }
}
