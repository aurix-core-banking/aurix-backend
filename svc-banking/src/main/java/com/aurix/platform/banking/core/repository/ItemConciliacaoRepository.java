package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.ItemConciliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemConciliacaoRepository extends JpaRepository<ItemConciliacao, Long> {
    
    List<ItemConciliacao> findByConciliacaoId(Long conciliacaoId);
    
    List<ItemConciliacao> findByMovimentoId(Long movimentoId);
    
    List<ItemConciliacao> findByTransacaoId(Long transacaoId);
    
    List<ItemConciliacao> findByLiquidacaoId(Long liquidacaoId);
    
    List<ItemConciliacao> findByOrigemItem(ItemConciliacao.OrigemItem origemItem);
    
    List<ItemConciliacao> findByStatus(ItemConciliacao.StatusConciliacao status);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.conciliacao.id = :conciliacaoId AND i.status = :status")
    List<ItemConciliacao> findItensPorConciliacaoEStatus(@Param("conciliacaoId") Long conciliacaoId,
                                                         @Param("status") ItemConciliacao.StatusConciliacao status);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.conciliacao.id = :conciliacaoId AND i.origemItem = :origemItem")
    List<ItemConciliacao> findItensPorConciliacaoEOrigem(@Param("conciliacaoId") Long conciliacaoId,
                                                         @Param("origemItem") ItemConciliacao.OrigemItem origemItem);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.conciliacao.id = :conciliacaoId AND i.dataItem BETWEEN :dataInicio AND :dataFim")
    List<ItemConciliacao> findItensPorConciliacaoEPeriodo(@Param("conciliacaoId") Long conciliacaoId,
                                                          @Param("dataInicio") LocalDateTime dataInicio,
                                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.conciliacao.id = :conciliacaoId AND i.processado = :processado")
    List<ItemConciliacao> findItensPorConciliacaoEProcessado(@Param("conciliacaoId") Long conciliacaoId,
                                                             @Param("processado") Boolean processado);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.codigoReferencia = :codigoReferencia")
    Optional<ItemConciliacao> findByCodigoReferencia(@Param("codigoReferencia") String codigoReferencia);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.codigoBacen = :codigoBacen")
    Optional<ItemConciliacao> findByCodigoBacen(@Param("codigoBacen") String codigoBacen);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.codigoSPI = :codigoSPI")
    Optional<ItemConciliacao> findByCodigoSPI(@Param("codigoSPI") String codigoSPI);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.codigoSTR = :codigoSTR")
    Optional<ItemConciliacao> findByCodigoSTR(@Param("codigoSTR") String codigoSTR);
    
    @Query("SELECT i FROM ItemConciliacao i WHERE i.codigoContraparte = :codigoContraparte")
    List<ItemConciliacao> findByCodigoContraparte(@Param("codigoContraparte") String codigoContraparte);
    
    @Query("SELECT COUNT(i) FROM ItemConciliacao i WHERE i.conciliacao.id = :conciliacaoId AND i.status = :status")
    Long countItensPorConciliacaoEStatus(@Param("conciliacaoId") Long conciliacaoId,
                                         @Param("status") ItemConciliacao.StatusConciliacao status);
    
    @Query("SELECT SUM(i.valorItem) FROM ItemConciliacao i WHERE i.conciliacao.id = :conciliacaoId AND i.status = :status")
    Double sumValorItensPorConciliacaoEStatus(@Param("conciliacaoId") Long conciliacaoId,
                                              @Param("status") ItemConciliacao.StatusConciliacao status);
}
