package com.aurix.platform.payments.dda.repository;

import com.aurix.platform.shared.entity.DdaAutorizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DdaAutorizacaoRepository extends JpaRepository<DdaAutorizacao, Long> {

    Optional<DdaAutorizacao> findByCodigoAutorizacao(String codigoAutorizacao);

    List<DdaAutorizacao> findByContaDebitadaId(Long contaDebitadaId);

    List<DdaAutorizacao> findByStatus(DdaAutorizacao.StatusDda status);

    List<DdaAutorizacao> findByCnpjBeneficiario(String cnpjBeneficiario);

    @Query("SELECT a FROM DdaAutorizacao a WHERE a.status = 'ATIVA' AND a.cnpjBeneficiario = :cnpj AND a.contaDebitada.id = :contaId")
    Optional<DdaAutorizacao> findAutorizacaoAtiva(@Param("cnpj") String cnpj, @Param("contaId") Long contaId);

    boolean existsByCodigoAutorizacao(String codigoAutorizacao);
}
