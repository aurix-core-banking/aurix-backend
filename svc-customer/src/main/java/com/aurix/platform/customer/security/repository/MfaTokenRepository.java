package com.aurix.platform.customer.security.repository;

import com.aurix.platform.customer.security.entity.MfaToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MfaTokenRepository extends JpaRepository<MfaToken, Long> {

    Optional<MfaToken> findByCodigoToken(String codigoToken);

    List<MfaToken> findByUsuarioId(Long usuarioId);

    List<MfaToken> findBySessaoId(String sessaoId);

    @Query("SELECT t FROM MfaToken t WHERE t.codigoToken = :codigoToken AND t.status = 'PENDENTE' AND t.dataExpiracao > :agora")
    Optional<MfaToken> findTokenValido(@Param("codigoToken") String codigoToken, @Param("agora") LocalDateTime agora);
}
