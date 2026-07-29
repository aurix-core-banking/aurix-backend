package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.RegistroGarantia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroGarantiaRepository extends JpaRepository<RegistroGarantia, Long> {
    List<RegistroGarantia> findByGarantiaId(Long garantiaId);
}
