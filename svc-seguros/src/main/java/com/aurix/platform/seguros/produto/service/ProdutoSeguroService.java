package com.aurix.platform.seguros.produto.service;

import com.aurix.platform.seguros.produto.dto.ProdutoSeguroRequest;
import com.aurix.platform.seguros.produto.dto.ProdutoSeguroResponse;
import com.aurix.platform.seguros.produto.entity.ProdutoSeguro;
import com.aurix.platform.seguros.produto.entity.TipoSeguro;
import com.aurix.platform.seguros.produto.repository.ProdutoSeguroRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoSeguroService {

    private static final BigDecimal FATOR_IDADE_BASE = BigDecimal.valueOf(0.001);
    private static final BigDecimal FATOR_UF = BigDecimal.valueOf(0.05);
    private static final BigDecimal FATOR_SEXO_M = BigDecimal.valueOf(0.02);
    private static final BigDecimal FATOR_PROFISSAO_ALTO_RISCO = BigDecimal.valueOf(0.15);

    private final ProdutoSeguroRepository repository;

    public ProdutoSeguroService(ProdutoSeguroRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProdutoSeguroResponse criar(ProdutoSeguroRequest request) {
        var entity = new ProdutoSeguro(
            request.tenantId(), request.nome(), request.descricao(),
            request.tipo(), request.coberturaPadrao(), request.taxaBase(),
            request.premioMinimo(), request.carenciaMeses(),
            request.prazoAnaliseDias(), request.prazoPagamentoSinistroDias()
        );
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ProdutoSeguroResponse> listar() {
        return repository.findByAtivoTrue().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoSeguroResponse buscarPorId(Long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto de seguro não encontrado: " + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ProdutoSeguroResponse> listarPorTipo(TipoSeguro tipo) {
        return repository.findByTipo(tipo).stream()
            .map(this::toResponse)
            .toList();
    }

    public BigDecimal calcularPremio(BigDecimal valorSegurado, BigDecimal taxaBase,
                                     Integer idade, String uf, String sexo, String profissao) {
        var fatorIdade = FATOR_IDADE_BASE.multiply(BigDecimal.valueOf(idade));
        var fatorUf = "SP".equals(uf) || "RJ".equals(uf) ? FATOR_UF : BigDecimal.ZERO;
        var fatorSexo = "M".equals(sexo) ? FATOR_SEXO_M : BigDecimal.ZERO;
        var fatorProfissao = profissao != null && profissao.contains("ALTORISCO")
            ? FATOR_PROFISSAO_ALTO_RISCO : BigDecimal.ZERO;

        var fatorTotal = BigDecimal.ONE
            .add(fatorIdade)
            .add(fatorUf)
            .add(fatorSexo)
            .add(fatorProfissao);

        return valorSegurado.multiply(taxaBase).multiply(fatorTotal).setScale(2, RoundingMode.HALF_UP);
    }

    private ProdutoSeguroResponse toResponse(ProdutoSeguro e) {
        return new ProdutoSeguroResponse(
            e.getId(), e.getTenantId(), e.getNome(), e.getDescricao(),
            e.getTipo().name(), e.getCoberturaPadrao().name(),
            e.getTaxaBase(), e.getPremioMinimo(), e.getCarenciaMeses(),
            e.getPrazoAnaliseDias(), e.getPrazoPagamentoSinistroDias(),
            e.getAtivo(), e.getDataCriacao()
        );
    }
}
