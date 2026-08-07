package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndAtivoTrue(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.cliente.id = :clienteId")
    Optional<Usuario> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT u FROM Usuario u WHERE u.contaBloqueada = true")
    List<Usuario> findUsuariosComContaBloqueada();

    @Query("SELECT u FROM Usuario u WHERE u.dataExpiracaoSenha < :dataAtual")
    List<Usuario> findUsuariosComSenhaExpirada(@Param("dataAtual") LocalDateTime dataAtual);

    @Query("SELECT u FROM Usuario u WHERE u.ultimoLogin < :dataLimite")
    List<Usuario> findUsuariosInativos(@Param("dataLimite") LocalDateTime dataLimite);

    long countByAtivoTrue();

    long countByContaBloqueadaTrue();
}
