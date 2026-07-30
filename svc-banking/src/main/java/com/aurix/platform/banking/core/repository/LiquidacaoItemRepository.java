package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.LiquidacaoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LiquidacaoItemRepository extends JpaRepository<LiquidacaoItem, Long> {
    
    List<LiquidacaoItem> findByLiquidacaoId(Long liquidacaoId);
    
    List<LiquidacaoItem> findByContaId(Long contaId);
    
    List<LiquidacaoItem> findByTipoMovimento(LiquidacaoItem.TipoMovimento tipoMovimento);
    
    @Query("SELECT li FROM LiquidacaoItem li WHERE li.conta.id = :contaId AND li.dataMovimento BETWEEN :dataInicio AND :dataFim")
    List<LiquidacaoItem> findMovimentosPorContaEPeriodo(@Param("contaId") Long contaId,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT li FROM LiquidacaoItem li WHERE li.liquidacao.id = :liquidacaoId AND li.processado = :processado")
    List<LiquidacaoItem> findItensPorLiquidacaoEProcessado(@Param("liquidacaoId") Long liquidacaoId,
                                                           @Param("processado") Boolean processado);
    
    @Query("SELECT SUM(li.valorMovimento) FROM LiquidacaoItem li WHERE li.conta.id = :contaId AND li.tipoMovimento = :tipoMovimento AND li.dataMovimento BETWEEN :dataInicio AND :dataFim")
    Double sumValorMovimentoPorContaETipo(@Param("contaId") Long contaId,
                                         @Param("tipoMovimento") LiquidacaoItem.TipoMovimento tipoMovimento,
                                         @Param("dataInicio") LocalDateTime dataInicio,
                                         @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT li FROM LiquidacaoItem li WHERE li.codigoMovimento = :codigoMovimento")
    Optional<LiquidacaoItem> findByCodigoMovimento(@Param("codigoMovimento") String codigoMovimento);
    
    @Query("SELECT li FROM LiquidacaoItem li WHERE li.codigoContraparte = :codigoContraparte")
    List<LiquidacaoItem> findByCodigoContraparte(@Param("codigoContraparte") String codigoContraparte);
}
