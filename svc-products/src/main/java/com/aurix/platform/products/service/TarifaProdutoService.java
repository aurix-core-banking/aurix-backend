package com.aurix.platform.products.service;

import com.aurix.platform.products.dto.TarifaProdutoRequest;
import com.aurix.platform.products.dto.TarifaProdutoResponse;
import com.aurix.platform.products.entity.Produto;
import com.aurix.platform.products.entity.TarifaProduto;
import com.aurix.platform.products.repository.TarifaProdutoRepository;import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarifaProdutoService {

    private final TarifaProdutoRepository tarifaRepository;
    private final ProdutoService produtoService;

    public TarifaProdutoService(TarifaProdutoRepository tarifaRepository,
                                ProdutoService produtoService) {
        this.tarifaRepository = tarifaRepository;
        this.produtoService = produtoService;
    }

    @Transactional
    public TarifaProduto adicionarTarifa(Long produtoId, TarifaProdutoRequest request) {
        Produto produto = produtoService.buscarEntidade(produtoId);
        boolean codigoEmUso = tarifaRepository.findByProdutoId(produtoId).stream()
            .anyMatch(t -> t.getCodigo().equalsIgnoreCase(request.codigo()));
        if (codigoEmUso) {
            throw new IllegalArgumentException("Já existe tarifa com código: " + request.codigo());
        }
        TarifaProduto tarifa = new TarifaProduto();
        tarifa.setProdutoId(produto.getId());
        tarifa.setCodigo(request.codigo());
        tarifa.setDescricao(request.descricao());
        tarifa.setTipoTarifa(request.tipoTarifa());
        tarifa.setPeriodicidade(request.periodicidade());
        tarifa.setValorFixo(request.valorFixo());
        tarifa.setPercentual(request.percentual());
        tarifa.setVigenciaInicio(request.vigenciaInicio());
        tarifa.setVigenciaFim(request.vigenciaFim());
        tarifa.setObrigatoria(request.obrigatoria() == null ? Boolean.TRUE : request.obrigatoria());
        return tarifaRepository.save(tarifa);
    }

    @Transactional
    public void removerTarifa(Long produtoId, Long tarifaId) {
        Produto produto = produtoService.buscarEntidade(produtoId);
        TarifaProduto tarifa = tarifaRepository.findById(tarifaId)
            .filter(t -> t.getProdutoId().equals(produto.getId()))
            .orElseThrow(() -> new IllegalArgumentException("Tarifa não encontrada: " + tarifaId));
        tarifaRepository.delete(tarifa);
    }

    @Transactional(readOnly = true)
    public List<TarifaProdutoResponse> listarTarifas(Long produtoId) {
        produtoService.buscarEntidade(produtoId);
        return tarifaRepository.findByProdutoId(produtoId).stream()
            .map(TarifaProdutoResponse::de)
            .toList();
    }
}
