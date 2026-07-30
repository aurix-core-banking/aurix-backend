package com.aurix.platform.credit.credit.service;

import com.aurix.platform.credit.credit.entity.ProdutoCredito;
import com.aurix.platform.credit.credit.repository.ProdutoCreditoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoCreditoService {
    private final ProdutoCreditoRepository produtoCreditoRepository;

    @Transactional(readOnly = true)
    public List<ProdutoCredito> listarAtivos() {
        return produtoCreditoRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<ProdutoCredito> buscarPorId(Long id) {
        return produtoCreditoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ProdutoCredito> buscarPorCodigo(String codigo) {
        return produtoCreditoRepository.findByCodigo(codigo);
    }

    @Transactional(readOnly = true)
    public List<ProdutoCredito> listarPorTipo(ProdutoCredito.TipoCredito tipo) {
        return produtoCreditoRepository.findByTipoCredito(tipo);
    }

    public ProdutoCredito salvar(ProdutoCredito produto) {
        return produtoCreditoRepository.save(produto);
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoCreditoService(final ProdutoCreditoRepository produtoCreditoRepository) {
        this.produtoCreditoRepository = produtoCreditoRepository;
    }
}
