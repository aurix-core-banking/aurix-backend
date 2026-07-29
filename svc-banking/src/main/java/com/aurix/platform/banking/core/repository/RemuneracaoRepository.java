package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.Remuneracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RemuneracaoRepository extends JpaRepository<Remuneracao, Long> {
    
    Optional<Remuneracao> findByCodigoRemuneracao(String codigoRemuneracao);
    
    List<Remuneracao> findByAplicacaoFinanceiraId(Long aplicacaoFinanceiraId);
    
    List<Remuneracao> findByContaId(Long contaId);
    
    List<Remuneracao> findByTipoRemuneracao(Remuneracao.TipoRemuneracao tipoRemuneracao);
    
    List<Remuneracao> findByStatus(Remuneracao.StatusRemuneracao status);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.conta.id = :contaId AND r.dataRemuneracao BETWEEN :dataInicio AND :dataFim")
    List<Remuneracao> findRemuneracoesPorContaEPeriodo(@Param("contaId") Long contaId,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.tipoRemuneracao = :tipoRemuneracao AND r.dataRemuneracao BETWEEN :dataInicio AND :dataFim")
    List<Remuneracao> findRemuneracoesPorTipoEPeriodo(@Param("tipoRemuneracao") Remuneracao.TipoRemuneracao tipoRemuneracao,
                                                      @Param("dataInicio") LocalDateTime dataInicio,
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.status = :status AND r.dataRemuneracao BETWEEN :dataInicio AND :dataFim")
    List<Remuneracao> findRemuneracoesPorStatusEPeriodo(@Param("status") Remuneracao.StatusRemuneracao status,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.processamentoAutomatico = :processamentoAutomatico AND r.dataRemuneracao BETWEEN :dataInicio AND :dataFim")
    List<Remuneracao> findRemuneracoesComProcessamentoAutomatico(@Param("processamentoAutomatico") Boolean processamentoAutomatico,
                                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.reversivel = :reversivel AND r.dataRemuneracao BETWEEN :dataInicio AND :dataFim")
    List<Remuneracao> findRemuneracoesReversiveis(@Param("reversivel") Boolean reversivel,
                                                  @Param("dataInicio") LocalDateTime dataInicio,
                                                  @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.estornada = :estornada AND r.dataRemuneracao BETWEEN :dataInicio AND :dataFim")
    List<Remuneracao> findRemuneracoesEstornadas(@Param("estornada") Boolean estornada,
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.valorRemuneracao >= :valorMinimo AND r.valorRemuneracao <= :valorMaximo")
    List<Remuneracao> findRemuneracoesPorFaixaValor(@Param("valorMinimo") Double valorMinimo, @Param("valorMaximo") Double valorMaximo);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.taxaAplicada >= :taxaMinima AND r.taxaAplicada <= :taxaMaxima")
    List<Remuneracao> findRemuneracoesPorFaixaTaxa(@Param("taxaMinima") Double taxaMinima, @Param("taxaMaxima") Double taxaMaxima);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.diasRemuneracao >= :diasMinimos AND r.diasRemuneracao <= :diasMaximos")
    List<Remuneracao> findRemuneracoesPorFaixaDias(@Param("diasMinimos") Integer diasMinimos, @Param("diasMaximos") Integer diasMaximos);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.codigoTransacao = :codigoTransacao")
    Optional<Remuneracao> findByCodigoTransacao(@Param("codigoTransacao") String codigoTransacao);
    
    @Query("SELECT r FROM Remuneracao r WHERE r.codigoLiquidacao = :codigoLiquidacao")
    Optional<Remuneracao> findByCodigoLiquidacao(@Param("codigoLiquidacao") String codigoLiquidacao);
}
