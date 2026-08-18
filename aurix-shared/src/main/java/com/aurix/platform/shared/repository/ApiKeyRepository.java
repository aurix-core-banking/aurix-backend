package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    Optional<ApiKey> findByPrefixoAndAtivoTrue(String prefixo);

    Optional<ApiKey> findByKeyHash(String keyHash);

    boolean existsByKeyHash(String keyHash);

    long countByAtivoTrue();
}
