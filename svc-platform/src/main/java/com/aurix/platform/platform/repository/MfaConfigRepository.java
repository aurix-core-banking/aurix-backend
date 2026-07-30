package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.MfaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MfaConfigRepository extends JpaRepository<MfaConfig, Long> {

    List<MfaConfig> findByUsuarioId(Long usuarioId);

    Optional<MfaConfig> findByUsuarioIdAndTipoMfa(Long usuarioId, MfaConfig.TipoMfa tipoMfa);

    List<MfaConfig> findByUsuarioIdAndAtivoTrue(Long usuarioId);
}
