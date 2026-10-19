package com.aurix.platform.investimentos.indice.repository;

import com.aurix.platform.investimentos.indice.entity.IndiceMercado;
import com.aurix.platform.investimentos.indice.entity.TipoIndice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndiceMercadoRepository extends JpaRepository<IndiceMercado, Long> {

    Optional<IndiceMercado> findTopByTipoIndiceOrderByDataReferenciaDesc(TipoIndice tipoIndice);

    List<IndiceMercado> findByTipoIndiceOrderByDataReferenciaDesc(TipoIndice tipoIndice);

    @Query("SELECT i FROM IndiceMercado i WHERE i.tipoIndice = :tipoIndice AND i.dataReferencia <= :dataAtual "
            + "ORDER BY i.dataReferencia DESC")
    Optional<IndiceMercado> findIndiceAtual(@Param("tipoIndice") TipoIndice tipoIndice,
                                            @Param("dataAtual") java.time.LocalDate dataAtual);
}