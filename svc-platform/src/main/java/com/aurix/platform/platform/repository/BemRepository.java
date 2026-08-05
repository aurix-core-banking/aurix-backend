package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.Bem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BemRepository extends JpaRepository<Bem, Long> {
    List<Bem> findByTipo(String tipo);
}
