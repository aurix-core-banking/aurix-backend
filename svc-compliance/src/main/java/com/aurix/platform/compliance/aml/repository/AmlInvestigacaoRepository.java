package com.aurix.platform.compliance.aml.repository;

import com.aurix.platform.compliance.aml.entity.AmlInvestigacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AmlInvestigacaoRepository extends JpaRepository<AmlInvestigacao, Long> {

    Optional<AmlInvestigacao> findByCodigoInvestigacao(String codigoInvestigacao);

    List<AmlInvestigacao> findByAlertaId(Long alertaId);

    List<AmlInvestigacao> findByClienteId(Long clienteId);

    List<AmlInvestigacao> findByStatus(AmlInvestigacao.StatusInvestigacao status);

    @Query("SELECT i FROM AmlInvestigacao i WHERE i.investigadorResponsavel = :investigador AND i.status IN ('INICIADA', 'EM_ANDAMENTO')")
    List<AmlInvestigacao> findByInvestigadorAtivo(@Param("investigador") String investigador);

    @Query("SELECT i FROM AmlInvestigacao i WHERE i.dataPrazo < :agora AND i.status IN ('INICIADA', 'EM_ANDAMENTO')")
    List<AmlInvestigacao> findInvestigacoesAtrasadas(@Param("agora") LocalDateTime agora);

    @Query("SELECT COUNT(i) FROM AmlInvestigacao i WHERE i.status IN ('INICIADA', 'EM_ANDAMENTO')")
    long countInvestigacoesAbertas();
}
