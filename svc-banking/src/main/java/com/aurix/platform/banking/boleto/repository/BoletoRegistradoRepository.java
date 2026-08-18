package com.aurix.platform.banking.boleto.repository;

import com.aurix.platform.banking.boleto.entity.BoletoRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoletoRegistradoRepository extends JpaRepository<BoletoRegistrado, Long> {
    Optional<BoletoRegistrado> findByCodigoBarras(String codigoBarras);
    Optional<BoletoRegistrado> findByLinhaDigitavel(String linhaDigitavel);
    List<BoletoRegistrado> findByContaId(Long contaId);
    List<BoletoRegistrado> findByTenantId(String tenantId);
    List<BoletoRegistrado> findByStatus(BoletoRegistrado.StatusBoleto status);
    Optional<BoletoRegistrado> findByTenantIdAndId(String tenantId, Long id);

    @Query("SELECT b FROM BoletoRegistrado b WHERE b.status = 'PENDENTE' AND b.dataVencimento < :dataReferencia")
    List<BoletoRegistrado> findVencidos(@Param("dataReferencia") LocalDate dataReferencia);

    @Query("SELECT b FROM BoletoRegistrado b WHERE b.tenantId = :tenantId AND b.status = 'PENDENTE' AND b.dataVencimento < :dataReferencia")
    List<BoletoRegistrado> findVencidosByTenant(@Param("tenantId") String tenantId, @Param("dataReferencia") LocalDate dataReferencia);

    @Query("SELECT b FROM BoletoRegistrado b WHERE b.contaId = :contaId AND b.status IN ('PENDENTE', 'VENCIDO')")
    List<BoletoRegistrado> findPendentesPorConta(@Param("contaId") Long contaId);
}
