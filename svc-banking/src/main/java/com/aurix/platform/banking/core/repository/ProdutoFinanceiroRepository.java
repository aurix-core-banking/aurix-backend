package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.ProdutoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoFinanceiroRepository extends JpaRepository<ProdutoFinanceiro, Long> {
    
    Optional<ProdutoFinanceiro> findByCodigoProduto(String codigoProduto);
    
    List<ProdutoFinanceiro> findByTipoProduto(ProdutoFinanceiro.TipoProduto tipoProduto);
    
    List<ProdutoFinanceiro> findByCategoriaProduto(ProdutoFinanceiro.CategoriaProduto categoriaProduto);
    
    List<ProdutoFinanceiro> findByTipoRemuneracao(ProdutoFinanceiro.TipoRemuneracao tipoRemuneracao);
    
    List<ProdutoFinanceiro> findByPeriodicidadeRemuneracao(ProdutoFinanceiro.PeriodicidadeRemuneracao periodicidadeRemuneracao);
    
    List<ProdutoFinanceiro> findByAtivo(Boolean ativo);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.tipoProduto = :tipoProduto AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosAtivosPorTipo(@Param("tipoProduto") ProdutoFinanceiro.TipoProduto tipoProduto);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.categoriaProduto = :categoriaProduto AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosAtivosPorCategoria(@Param("categoriaProduto") ProdutoFinanceiro.CategoriaProduto categoriaProduto);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.tipoRemuneracao = :tipoRemuneracao AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosAtivosPorTipoRemuneracao(@Param("tipoRemuneracao") ProdutoFinanceiro.TipoRemuneracao tipoRemuneracao);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.periodicidadeRemuneracao = :periodicidadeRemuneracao AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosAtivosPorPeriodicidade(@Param("periodicidadeRemuneracao") ProdutoFinanceiro.PeriodicidadeRemuneracao periodicidadeRemuneracao);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.requerAprovacao = :requerAprovacao AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosQueRequeremAprovacao(@Param("requerAprovacao") Boolean requerAprovacao);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.requerDocumentacao = :requerDocumentacao AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosQueRequeremDocumentacao(@Param("requerDocumentacao") Boolean requerDocumentacao);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.disponivelPublico = :disponivelPublico AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosDisponiveisPublico(@Param("disponivelPublico") Boolean disponivelPublico);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.dataInicioVigencia <= :dataAtual AND (p.dataFimVigencia IS NULL OR p.dataFimVigencia >= :dataAtual) AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosVigentes(@Param("dataAtual") LocalDateTime dataAtual);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.valorMinimoAplicacao <= :valor AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosComValorMinimoSuficiente(@Param("valor") Double valor);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.valorMaximoAplicacao >= :valor AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosComValorMaximoSuficiente(@Param("valor") Double valor);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.taxaRemuneracao >= :taxaMinima AND p.taxaRemuneracao <= :taxaMaxima AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosPorFaixaTaxa(@Param("taxaMinima") Double taxaMinima, @Param("taxaMaxima") Double taxaMaxima);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.prazoMinimoDias <= :prazo AND p.prazoMaximoDias >= :prazo AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosPorPrazo(@Param("prazo") Integer prazo);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.nivelRisco = :nivelRisco AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosPorNivelRisco(@Param("nivelRisco") Integer nivelRisco);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.permiteResgateAntecipado = :permiteResgateAntecipado AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosQuePermitemResgateAntecipado(@Param("permiteResgateAntecipado") Boolean permiteResgateAntecipado);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.permiteAplicacaoParcial = :permiteAplicacaoParcial AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosQuePermitemAplicacaoParcial(@Param("permiteAplicacaoParcial") Boolean permiteAplicacaoParcial);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.permiteReaplicacao = :permiteReaplicacao AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosQuePermitemReaplicacao(@Param("permiteReaplicacao") Boolean permiteReaplicacao);
    
    @Query("SELECT p FROM ProdutoFinanceiro p WHERE p.permiteRenovacao = :permiteRenovacao AND p.ativo = true")
    List<ProdutoFinanceiro> findProdutosQuePermitemRenovacao(@Param("permiteRenovacao") Boolean permiteRenovacao);
}
