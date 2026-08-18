package com.aurix.platform.cards.service;

import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.Fatura;
import com.aurix.platform.cards.entity.TransacaoCartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.FaturaRepository;
import com.aurix.platform.cards.repository.TransacaoCartaoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@SuppressWarnings({"PMD.CollapsibleIfStatements"})
public class CartaoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CartaoService.class);
    private final CartaoRepository cartaoRepository;
    private final FaturaRepository faturaRepository;
    private final TransacaoCartaoRepository transacaoRepository;
    private final int diaVencimentoFatura;
    private final int validadeAnos;
    private final BigDecimal percentualPagamentoMinimo;

    public CartaoService(CartaoRepository cartaoRepository, FaturaRepository faturaRepository,
                         TransacaoCartaoRepository transacaoRepository,
                         @org.springframework.beans.factory.annotation.Value("${aurix.cartoes.dia-vencimento-fatura:10}") int diaVencimentoFatura,
                         @org.springframework.beans.factory.annotation.Value("${aurix.cartoes.validade-anos:5}") int validadeAnos,
                         @org.springframework.beans.factory.annotation.Value("${aurix.cartoes.percentual-pagamento-minimo:0.10}") BigDecimal percentualPagamentoMinimo) {
        this.cartaoRepository = cartaoRepository;
        this.faturaRepository = faturaRepository;
        this.transacaoRepository = transacaoRepository;
        this.diaVencimentoFatura = diaVencimentoFatura;
        this.validadeAnos = validadeAnos;
        this.percentualPagamentoMinimo = percentualPagamentoMinimo;
    }

    public Cartao emitirCartao(Long contaId, Cartao.TipoCartao tipoCartao, Cartao.BandeiraCartao bandeira, String nomePortador, BigDecimal limiteCredito) {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(gerarNumeroCartao(bandeira));
        cartao.setNumeroCartaoMascarado(mascararNumeroCartao(cartao.getNumeroCartao()));
        cartao.setCvv(gerarCVV());
        cartao.setDataValidade(LocalDate.now().plusYears(validadeAnos));
        cartao.setNomePortador(nomePortador);
        cartao.setContaId(contaId);
        cartao.setTipoCartao(tipoCartao);
        cartao.setBandeira(bandeira);
        cartao.setStatus(Cartao.StatusCartao.PENDENTE_ATIVACAO);
        cartao.setLimiteCredito(limiteCredito);
        cartao.setLimiteUtilizado(BigDecimal.ZERO);
        cartao.setLimiteDisponivel(limiteCredito);
        cartao.setDataEmissao(LocalDateTime.now());
        cartao.setDiaVencimentoFatura(diaVencimentoFatura);
        return cartaoRepository.save(cartao);
    }

    public Cartao ativarCartao(Long cartaoId, String cvv) {
        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        if (!cartao.getCvv().equals(cvv)) {
            throw new RuntimeException("CVV inválido");
        }
        cartao.setStatus(Cartao.StatusCartao.ATIVO);
        cartao.setDataAtivacao(LocalDateTime.now());
        return cartaoRepository.save(cartao);
    }

    public Cartao bloquearCartao(Long cartaoId, String motivo) {
        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        cartao.setStatus(Cartao.StatusCartao.BLOQUEADO);
        cartao.setDataBloqueio(LocalDateTime.now());
        cartao.setMotivoBloqueio(motivo);
        return cartaoRepository.save(cartao);
    }

    public Cartao desbloquearCartao(Long cartaoId) {
        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        if (cartao.getStatus() != Cartao.StatusCartao.BLOQUEADO) {
            throw new RuntimeException("Cartão não está bloqueado");
        }
        cartao.setStatus(Cartao.StatusCartao.ATIVO);
        cartao.setMotivoBloqueio(null);
        return cartaoRepository.save(cartao);
    }

    public Cartao alterarLimite(Long cartaoId, BigDecimal novoLimite) {
        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        if (novoLimite.compareTo(cartao.getLimiteUtilizado()) < 0) {
            throw new RuntimeException("Novo limite não pode ser menor que o limite utilizado");
        }
        cartao.setLimiteCredito(novoLimite);
        cartao.setLimiteDisponivel(novoLimite.subtract(cartao.getLimiteUtilizado()));
        return cartaoRepository.save(cartao);
    }

    public TransacaoCartao processarTransacao(Long cartaoId, BigDecimal valor, String estabelecimento, TransacaoCartao.TipoTransacao tipo) {
        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        if (cartao.getStatus() != Cartao.StatusCartao.ATIVO) {
            throw new RuntimeException("Cartão não está ativo");
        }
        if (tipo == TransacaoCartao.TipoTransacao.COMPRA_CREDITO || tipo == TransacaoCartao.TipoTransacao.SAQUE) {
            if (cartao.getLimiteDisponivel().compareTo(valor) < 0) {
                throw new RuntimeException("Limite insuficiente");
            }
        }
        TransacaoCartao transacao = new TransacaoCartao();
        transacao.setCodigoTransacao("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        transacao.setCartaoId(cartaoId);
        transacao.setTipoTransacao(tipo);
        transacao.setStatus(TransacaoCartao.StatusTransacao.AUTORIZADA);
        transacao.setValor(valor);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setEstabelecimento(estabelecimento);
        transacao.setNsu(gerarNSU());
        transacao.setAutorizacao(gerarAutorizacao());
        transacao.setNumeroParcelas(1);
        transacao.setParcelaAtual(1);
        transacao = transacaoRepository.save(transacao);
        if (tipo == TransacaoCartao.TipoTransacao.COMPRA_CREDITO || tipo == TransacaoCartao.TipoTransacao.SAQUE) {
            cartao.setLimiteUtilizado(cartao.getLimiteUtilizado().add(valor));
            cartao.setLimiteDisponivel(cartao.getLimiteCredito().subtract(cartao.getLimiteUtilizado()));
            cartaoRepository.save(cartao);
            atualizarFatura(cartao, transacao);
        }
        return transacao;
    }

    public Fatura gerarFatura(Long cartaoId, Integer mes, Integer ano) {
        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        Optional<Fatura> faturaExistente = faturaRepository.findByCartaoMesAno(cartaoId, mes, ano);
        if (faturaExistente.isPresent()) {
            return faturaExistente.get();
        }
        LocalDateTime inicioMes = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fimMes = inicioMes.plusMonths(1).minusSeconds(1);
        List<TransacaoCartao> transacoes = transacaoRepository.findByCartaoEPeriodo(cartaoId, inicioMes, fimMes);
        BigDecimal valorTotal = transacoes.stream().filter(t -> t.getStatus() == TransacaoCartao.StatusTransacao.AUTORIZADA || t.getStatus() == TransacaoCartao.StatusTransacao.CONFIRMADA).map(TransacaoCartao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        Fatura fatura = new Fatura();
        fatura.setCodigoFatura("FAT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        fatura.setCartaoId(cartaoId);
        fatura.setMesReferencia(mes);
        fatura.setAnoReferencia(ano);
        fatura.setStatus(Fatura.StatusFatura.ABERTA);
        fatura.setValorTotal(valorTotal);
        fatura.setValorPago(BigDecimal.ZERO);
        fatura.setValorPendente(valorTotal);
        fatura.setValorMinimo(valorTotal.multiply(percentualPagamentoMinimo));
        fatura.setDataVencimento(LocalDate.of(ano, mes, cartao.getDiaVencimentoFatura()).plusMonths(1));
        fatura.setDataGeracao(LocalDateTime.now());
        return faturaRepository.save(fatura);
    }

    public Fatura pagarFatura(Long faturaId, BigDecimal valorPagamento) {
        Fatura fatura = faturaRepository.findById(faturaId).orElseThrow(() -> new RuntimeException("Fatura não encontrada"));
        if (fatura.getStatus() == Fatura.StatusFatura.PAGA) {
            throw new RuntimeException("Fatura já está paga");
        }
        BigDecimal novoValorPago = fatura.getValorPago().add(valorPagamento);
        fatura.setValorPago(novoValorPago);
        fatura.setValorPendente(fatura.getValorTotal().subtract(novoValorPago));
        if (fatura.getValorPendente().compareTo(BigDecimal.ZERO) <= 0) {
            fatura.setStatus(Fatura.StatusFatura.PAGA);
            fatura.setDataPagamento(LocalDate.now());
            Cartao cartao = cartaoRepository.findById(fatura.getCartaoId()).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
            cartao.setLimiteUtilizado(cartao.getLimiteUtilizado().subtract(fatura.getValorTotal()));
            cartao.setLimiteDisponivel(cartao.getLimiteCredito().subtract(cartao.getLimiteUtilizado()));
            cartaoRepository.save(cartao);
        }
        return faturaRepository.save(fatura);
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void fecharFaturasMensais() {
        LocalDate hoje = LocalDate.now();
        int mesAnterior = hoje.minusMonths(1).getMonthValue();
        int anoAnterior = hoje.minusMonths(1).getYear();
        List<Cartao> cartoes = cartaoRepository.findAll();
        for (Cartao cartao : cartoes) {
            try {
                gerarFatura(cartao.getId(), mesAnterior, anoAnterior);
            } catch (Exception e) {
                log.error("Erro ao gerar fatura para cartão {}: {}", cartao.getId(), e.getMessage());
            }
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void atualizarFaturasVencidas() {
        List<Fatura> vencidas = faturaRepository.findFaturasVencidas(LocalDate.now());
        for (Fatura fatura : vencidas) {
            if (fatura.getStatus() == Fatura.StatusFatura.ABERTA || fatura.getStatus() == Fatura.StatusFatura.FECHADA) {
                fatura.setStatus(Fatura.StatusFatura.VENCIDA);
                faturaRepository.save(fatura);
            }
        }
    }

    private void atualizarFatura(Cartao cartao, TransacaoCartao transacao) {
        LocalDate hoje = LocalDate.now();
        Optional<Fatura> faturaOpt = faturaRepository.findByCartaoMesAno(cartao.getId(), hoje.getMonthValue(), hoje.getYear());
        if (faturaOpt.isPresent()) {
            Fatura fatura = faturaOpt.get();
            fatura.setValorTotal(fatura.getValorTotal().add(transacao.getValor()));
            fatura.setValorPendente(fatura.getValorTotal().subtract(fatura.getValorPago()));
            fatura.setValorMinimo(fatura.getValorTotal().multiply(percentualPagamentoMinimo));
            faturaRepository.save(fatura);
        } else {
            gerarFatura(cartao.getId(), hoje.getMonthValue(), hoje.getYear());
        }
    }

    private String gerarNumeroCartao(Cartao.BandeiraCartao bandeira) {
        String prefixo = switch (bandeira) {
            case VISA -> "4";
            case MASTERCARD -> "5";
            case ELO -> "6";
            case AMEX -> "37";
            case HIPERCARD -> "606282";
            case DINERS -> "36";
        };
        StringBuilder numero = new StringBuilder(prefixo);
        while (numero.length() < 16) {
            numero.append((int) (Math.random() * 10));
        }
        return numero.toString();
    }

    private String mascararNumeroCartao(String numero) {
        return "**** **** **** " + numero.substring(numero.length() - 4);
    }

    private String gerarCVV() {
        return String.format("%03d", (int) (Math.random() * 1000));
    }

    private String gerarNSU() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    private String gerarAutorizacao() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    public List<Cartao> listarCartoesPorConta(Long contaId) {
        return cartaoRepository.findByContaId(contaId);
    }

}
