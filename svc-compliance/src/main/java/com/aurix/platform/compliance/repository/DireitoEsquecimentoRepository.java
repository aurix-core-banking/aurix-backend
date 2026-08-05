package com.aurix.platform.compliance.repository;

import com.aurix.platform.compliance.entity.DireitoEsquecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireitoEsquecimentoRepository extends JpaRepository<DireitoEsquecimento, Long> {

    Optional<DireitoEsquecimento> findByCodigoSolicitacao(String codigoSolicitacao);

    List<DireitoEsquecimento> findByClienteId(Long clienteId);

    List<DireitoEsquecimento> findByCpfCnpj(String cpfCnpj);

    List<DireitoEsquecimento> findByStatus(DireitoEsquecimento.StatusProcessamento status);

    @Query("SELECT d FROM DireitoEsquecimento d WHERE d.status IN ('PENDENTE', 'PROCESSANDO') ORDER BY d.dataSolicitacao")
    List<DireitoEsquecimento> findSolicitacoesPendentes();
}
