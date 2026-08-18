package com.aurix.platform.compliance.coaf.repository;

import com.aurix.platform.compliance.coaf.entity.CoafNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoafNotificacaoRepository extends JpaRepository<CoafNotificacao, Long> {

    Optional<CoafNotificacao> findByCodigoNotificacao(String codigoNotificacao);

    List<CoafNotificacao> findByClienteId(Long clienteId);

    List<CoafNotificacao> findByCpfCnpj(String cpfCnpj);

    List<CoafNotificacao> findByStatus(CoafNotificacao.StatusNotificacaoCoaf status);

    List<CoafNotificacao> findByTipoNotificacao(CoafNotificacao.TipoNotificacaoCoaf tipo);

    @Query("SELECT n FROM CoafNotificacao n WHERE n.prazoNotificacao < :agora AND n.status = 'PENDENTE'")
    List<CoafNotificacao> findNotificacoesAtrasadas(@Param("agora") LocalDateTime agora);

    @Query("SELECT n FROM CoafNotificacao n WHERE n.dataOcorrencia BETWEEN :inicio AND :fim")
    List<CoafNotificacao> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    long countByStatus(CoafNotificacao.StatusNotificacaoCoaf status);

    @Query("SELECT COUNT(n) FROM CoafNotificacao n WHERE n.status IN ('PENDENTE', 'EM_PROCESSAMENTO')")
    long countPendentes();
}
