package com.aurix.platform.investimentos.produto.service;

import com.aurix.platform.investimentos.produto.dto.ProdutoInvestimentoRequest;
import com.aurix.platform.investimentos.produto.dto.ProdutoInvestimentoResponse;
import com.aurix.platform.investimentos.produto.entity.ProdutoInvestimento;
import com.aurix.platform.investimentos.produto.entity.TipoProdutoInvestimento;
import com.aurix.platform.investimentos.produto.repository.ProdutoInvestimentoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoInvestimentoService {

    private final ProdutoInvestimentoRepository repository;

    public ProdutoInvestimentoService(ProdutoInvestimentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProdutoInvestimentoResponse criar(ProdutoInvestimentoRequest request) {
        var entity = new ProdutoInvestimento(
            request.tenantId(), request.nome(), request.descricao(),
            request.tipo(), request.tipoRenda(), request.taxaRendimento(),
            request.taxaAdm(), request.valorMinimo(), request.prazoMinimoDias(),
            request.dataVencimento(), request.carenciaDias()
        );
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ProdutoInvestimentoResponse> listar() {
        return repository.findByAtivoTrue().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoInvestimentoResponse buscarPorId(Long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto de investimento não encontrado: " + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ProdutoInvestimentoResponse> listarPorTipo(TipoProdutoInvestimento tipo) {
        return repository.findByTipo(tipo).stream()
            .map(this::toResponse)
            .toList();
    }

    private ProdutoInvestimentoResponse toResponse(ProdutoInvestimento e) {
        return new ProdutoInvestimentoResponse(
            e.getId(), e.getTenantId(), e.getNome(), e.getDescricao(),
            e.getTipo().name(), e.getTipoRenda().name(),
            e.getTaxaRendimento(), e.getTaxaAdm(),
            e.getValorMinimo(), e.getPrazoMinimoDias(),
            e.getDataVencimento(), e.getCarenciaDias(),
            e.getAtivo(), e.getDataCriacao()
        );
    }
}
