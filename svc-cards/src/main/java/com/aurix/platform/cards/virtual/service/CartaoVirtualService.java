package com.aurix.platform.cards.virtual.service;

import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CartaoVirtualService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CartaoVirtualService.class);
    private final CartaoRepository cartaoRepository;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @java.lang.SuppressWarnings("all")
    public CartaoVirtualService(final CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Map<String, Object> criar(Long cartaoFisicoId, BigDecimal limite, Integer validadeMeses) {
        Cartao cartaoFisico = cartaoRepository.findById(cartaoFisicoId)
                .orElseThrow(() -> new RuntimeException("Cartão físico não encontrado: " + cartaoFisicoId));
        if (cartaoFisico.getStatus() != Cartao.StatusCartao.ATIVO) {
            throw new RuntimeException("Cartão físico não está ativo");
        }

        Cartao cartaoVirtual = new Cartao();
        String numeroPan = gerarPanVirtual(cartaoFisico.getBandeira());
        cartaoVirtual.setNumeroCartao(numeroPan);
        cartaoVirtual.setNumeroCartaoMascarado(mascararNumeroCartao(numeroPan));
        cartaoVirtual.setCvv(gerarCVVVirtual());
        cartaoVirtual.setDataValidade(LocalDate.now().plusMonths(Math.min(validadeMeses, 12)));
        cartaoVirtual.setNomePortador(cartaoFisico.getNomePortador() + " (VIRTUAL)");
        cartaoVirtual.setContaId(cartaoFisico.getContaId());
        cartaoVirtual.setTipoCartao(Cartao.TipoCartao.CREDITO);
        cartaoVirtual.setBandeira(cartaoFisico.getBandeira());
        cartaoVirtual.setStatus(Cartao.StatusCartao.ATIVO);
        cartaoVirtual.setLimiteCredito(limite);
        cartaoVirtual.setLimiteUtilizado(BigDecimal.ZERO);
        cartaoVirtual.setLimiteDisponivel(limite);
        cartaoVirtual.setDataEmissao(LocalDateTime.now());
        cartaoVirtual.setDataAtivacao(LocalDateTime.now());
        cartaoVirtual.setDiaVencimentoFatura(cartaoFisico.getDiaVencimentoFatura());
        cartaoVirtual.setPermiteComprasNacionais(true);
        cartaoVirtual.setPermiteComprasInternacionais(false);
        cartaoVirtual.setPermiteSaque(false);
        cartaoVirtual.setPermiteParcelamento(false);
        cartaoVirtual.setProdutoId(cartaoFisico.getProdutoId());
        cartaoVirtual.setBandeiraParceiroId(cartaoFisico.getBandeiraParceiroId());
        cartaoVirtual.setTenantId(cartaoFisico.getTenantId());
        cartaoRepository.save(cartaoVirtual);

        log.info("Cartão virtual criado: id={}, cartaoFisicoId={}, numeroMascarado={}",
                cartaoVirtual.getId(), cartaoFisicoId, cartaoVirtual.getNumeroCartaoMascarado());

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("id", cartaoVirtual.getId());
        resultado.put("numeroMascarado", cartaoVirtual.getNumeroCartaoMascarado());
        resultado.put("cvv", cartaoVirtual.getCvv());
        resultado.put("validade", cartaoVirtual.getDataValidade());
        resultado.put("bandeira", cartaoVirtual.getBandeira());
        resultado.put("limite", cartaoVirtual.getLimiteCredito());
        resultado.put("status", cartaoVirtual.getStatus());
        resultado.put("cartaoFisicoId", cartaoFisicoId);
        return resultado;
    }

    public Map<String, Object> consultar(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão virtual não encontrado: " + id));
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("id", cartao.getId());
        resultado.put("numeroMascarado", cartao.getNumeroCartaoMascarado());
        resultado.put("validade", cartao.getDataValidade());
        resultado.put("bandeira", cartao.getBandeira());
        resultado.put("tipo", cartao.getTipoCartao());
        resultado.put("status", cartao.getStatus());
        resultado.put("limiteCredito", cartao.getLimiteCredito());
        resultado.put("limiteDisponivel", cartao.getLimiteDisponivel());
        resultado.put("limiteUtilizado", cartao.getLimiteUtilizado());
        resultado.put("dataEmissao", cartao.getDataEmissao());
        resultado.put("dataBloqueio", cartao.getDataBloqueio());
        resultado.put("contaId", cartao.getContaId());
        resultado.put("nomePortador", cartao.getNomePortador());
        return resultado;
    }

    public Map<String, Object> bloquear(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão virtual não encontrado: " + id));
        if (cartao.getStatus() == Cartao.StatusCartao.CANCELADO) {
            throw new RuntimeException("Não é possível bloquear cartão cancelado");
        }
        cartao.setStatus(Cartao.StatusCartao.BLOQUEADO);
        cartao.setDataBloqueio(LocalDateTime.now());
        cartao.setMotivoBloqueio("Bloqueio manual pelo cliente (virtual)");
        cartaoRepository.save(cartao);

        log.info("Cartão virtual bloqueado: id={}", id);
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("id", cartao.getId());
        resultado.put("status", cartao.getStatus());
        resultado.put("dataBloqueio", cartao.getDataBloqueio());
        return resultado;
    }

    public Map<String, Object> desbloquear(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão virtual não encontrado: " + id));
        if (cartao.getStatus() != Cartao.StatusCartao.BLOQUEADO) {
            throw new RuntimeException("Cartão não está bloqueado");
        }
        cartao.setStatus(Cartao.StatusCartao.ATIVO);
        cartao.setMotivoBloqueio(null);
        cartao.setDataBloqueio(null);
        cartaoRepository.save(cartao);

        log.info("Cartão virtual desbloqueado: id={}", id);
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("id", cartao.getId());
        resultado.put("status", cartao.getStatus());
        return resultado;
    }

    public Map<String, Object> cancelar(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão virtual não encontrado: " + id));
        if (cartao.getStatus() == Cartao.StatusCartao.CANCELADO) {
            throw new RuntimeException("Cartão já está cancelado");
        }
        cartao.setStatus(Cartao.StatusCartao.CANCELADO);
        cartao.setDataCancelamento(LocalDateTime.now());
        cartao.setMotivoBloqueio("Cancelamento pelo cliente (virtual)");
        cartaoRepository.save(cartao);

        log.info("Cartão virtual cancelado: id={}", id);
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("id", cartao.getId());
        resultado.put("status", cartao.getStatus());
        resultado.put("dataCancelamento", cartao.getDataCancelamento());
        return resultado;
    }

    private String gerarPanVirtual(Cartao.BandeiraCartao bandeira) {
        String prefixo = switch (bandeira) {
            case VISA -> "4";
            case MASTERCARD -> "5";
            case ELO -> "6";
            case AMEX -> "37";
            case HIPERCARD -> "606282";
            case DINERS -> "36";
        };
        StringBuilder pan = new StringBuilder(prefixo);
        int[] digitos = new int[16];
        for (int i = 0; i < prefixo.length(); i++) {
            digitos[i] = Character.getNumericValue(prefixo.charAt(i));
        }
        for (int i = prefixo.length(); i < 15; i++) {
            digitos[i] = SECURE_RANDOM.nextInt(10);
        }
        digitos[15] = calcularDigitoLuhn(digitos);
        StringBuilder resultado = new StringBuilder();
        for (int d : digitos) {
            resultado.append(d);
        }
        return resultado.toString();
    }

    private int calcularDigitoLuhn(int[] digitos) {
        int soma = 0;
        boolean alternar = false;
        for (int i = digitos.length - 2; i >= 0; i--) {
            int d = digitos[i];
            if (alternar) {
                d *= 2;
                if (d > 9) {
                    d -= 9;
                }
            }
            soma += d;
            alternar = !alternar;
        }
        int resto = soma % 10;
        return resto == 0 ? 0 : (10 - resto);
    }

    private String mascararNumeroCartao(String numero) {
        return "**** **** **** " + numero.substring(numero.length() - 4);
    }

    private String gerarCVVVirtual() {
        return String.format("%03d", SECURE_RANDOM.nextInt(1000));
    }
}
