package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.AvaliacaoRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRiscoRepository extends JpaRepository<AvaliacaoRisco, Long> {
    
    Optional<AvaliacaoRisco> findByCodigoAvaliacao(String codigoAvaliacao);
    
    List<AvaliacaoRisco> findByContaId(Long contaId);
    
    List<AvaliacaoRisco> findByTransacaoId(Long transacaoId);
    
    List<AvaliacaoRisco> findByPerfilRiscoId(Long perfilRiscoId);
    
    List<AvaliacaoRisco> findByTipoAvaliacao(AvaliacaoRisco.TipoAvaliacao tipoAvaliacao);
    
    List<AvaliacaoRisco> findByStatus(AvaliacaoRisco.StatusAvaliacao status);
    
    List<AvaliacaoRisco> findByNivelRisco(AvaliacaoRisco.NivelRisco nivelRisco);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.conta.id = :contaId AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesPorContaEPeriodo(@Param("contaId") Long contaId,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.tipoAvaliacao = :tipoAvaliacao AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesPorTipoEPeriodo(@Param("tipoAvaliacao") AvaliacaoRisco.TipoAvaliacao tipoAvaliacao,
                                                       @Param("dataInicio") LocalDateTime dataInicio,
                                                       @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.status = :status AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesPorStatusEPeriodo(@Param("status") AvaliacaoRisco.StatusAvaliacao status,
                                                         @Param("dataInicio") LocalDateTime dataInicio,
                                                         @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.nivelRisco = :nivelRisco AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesPorNivelRiscoEPeriodo(@Param("nivelRisco") AvaliacaoRisco.NivelRisco nivelRisco,
                                                             @Param("dataInicio") LocalDateTime dataInicio,
                                                             @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.aprovada = :aprovada AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesAprovadas(@Param("aprovada") Boolean aprovada,
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.rejeitada = :rejeitada AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesRejeitadas(@Param("rejeitada") Boolean rejeitada,
                                                  @Param("dataInicio") LocalDateTime dataInicio,
                                                  @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.requerAprovacao = :requerAprovacao AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesQueRequeremAprovacao(@Param("requerAprovacao") Boolean requerAprovacao,
                                                            @Param("dataInicio") LocalDateTime dataInicio,
                                                            @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.requerDocumentacao = :requerDocumentacao AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesQueRequeremDocumentacao(@Param("requerDocumentacao") Boolean requerDocumentacao,
                                                               @Param("dataInicio") LocalDateTime dataInicio,
                                                               @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.requerBiometria = :requerBiometria AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesQueRequeremBiometria(@Param("requerBiometria") Boolean requerBiometria,
                                                            @Param("dataInicio") LocalDateTime dataInicio,
                                                            @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.requerToken = :requerToken AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesQueRequeremToken(@Param("requerToken") Boolean requerToken,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.requerAssinaturaDigital = :requerAssinaturaDigital AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesQueRequeremAssinaturaDigital(@Param("requerAssinaturaDigital") Boolean requerAssinaturaDigital,
                                                                    @Param("dataInicio") LocalDateTime dataInicio,
                                                                    @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.scoreRisco >= :scoreMinimo AND a.scoreRisco <= :scoreMaximo AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesPorScore(@Param("scoreMinimo") Integer scoreMinimo,
                                                @Param("scoreMaximo") Integer scoreMaximo,
                                                @Param("dataInicio") LocalDateTime dataInicio,
                                                @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.valorTransacao >= :valorMinimo AND a.valorTransacao <= :valorMaximo AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesPorValor(@Param("valorMinimo") Double valorMinimo,
                                                @Param("valorMaximo") Double valorMaximo,
                                                @Param("dataInicio") LocalDateTime dataInicio,
                                                @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.percentualRisco >= :percentualMinimo AND a.percentualRisco <= :percentualMaximo AND a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<AvaliacaoRisco> findAvaliacoesPorPercentualRisco(@Param("percentualMinimo") Double percentualMinimo,
                                                          @Param("percentualMaximo") Double percentualMaximo,
                                                          @Param("dataInicio") LocalDateTime dataInicio,
                                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.codigoTransacao = :codigoTransacao")
    Optional<AvaliacaoRisco> findByCodigoTransacao(@Param("codigoTransacao") String codigoTransacao);
    
    @Query("SELECT a FROM AvaliacaoRisco a WHERE a.codigoLiquidacao = :codigoLiquidacao")
    Optional<AvaliacaoRisco> findByCodigoLiquidacao(@Param("codigoLiquidacao") String codigoLiquidacao);
}
