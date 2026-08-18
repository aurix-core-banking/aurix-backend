package com.aurix.platform.payments.boleto.repository;

import com.aurix.platform.shared.entity.PagamentoBoleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoBoletoRepository extends JpaRepository<PagamentoBoleto, Long> {

    Optional<PagamentoBoleto> findByCodigoBoleto(String codigoBoleto);

    Optional<PagamentoBoleto> findByNossoNumero(String nossoNumero);

    List<PagamentoBoleto> findByContaCedenteId(Long contaCedenteId);

    List<PagamentoBoleto> findByStatus(PagamentoBoleto.StatusBoleto status);

    @Query("SELECT b FROM PagamentoBoleto b WHERE b.contaCedente.id = :contaId AND b.dataCriacao BETWEEN :inicio AND :fim ORDER BY b.dataCriacao DESC")
    List<PagamentoBoleto> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT b FROM PagamentoBoleto b WHERE b.status = 'REGISTRADO' AND b.dataVencimento <= :vencimento")
    List<PagamentoBoleto> findVencidosAte(@Param("vencimento") LocalDateTime vencimento);

    @Query("SELECT b FROM PagamentoBoleto b WHERE b.status = 'REGISTRADO' AND b.dataVencimento BETWEEN :inicio AND :fim")
    List<PagamentoBoleto> findVencidosNoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    boolean existsByCodigoBoleto(String codigoBoleto);

    boolean existsByNossoNumero(String nossoNumero);
}
