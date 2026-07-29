package com.aurix.platform.platform.repository;

import com.aurix.platform.shared.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca usuário por email
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Busca usuário por email e ativo
     */
    Optional<Usuario> findByEmailAndAtivoTrue(String email);
    
    /**
     * Verifica se existe usuário com email
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca usuários por cliente
     */
    @Query("SELECT u FROM Usuario u WHERE u.cliente.id = :clienteId")
    Optional<Usuario> findByClienteId(@Param("clienteId") Long clienteId);
    
    /**
     * Busca usuários com conta bloqueada
     */
    @Query("SELECT u FROM Usuario u WHERE u.contaBloqueada = true")
    List<Usuario> findUsuariosComContaBloqueada();
    
    /**
     * Busca usuários com senha expirada
     */
    @Query("SELECT u FROM Usuario u WHERE u.dataExpiracaoSenha < :dataAtual")
    List<Usuario> findUsuariosComSenhaExpirada(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca usuários inativos há mais de X dias
     */
    @Query("SELECT u FROM Usuario u WHERE u.ultimoLogin < :dataLimite")
    List<Usuario> findUsuariosInativos(@Param("dataLimite") LocalDateTime dataLimite);
    
    /**
     * Conta usuários ativos
     */
    long countByAtivoTrue();
    
    /**
     * Conta usuários com conta bloqueada
     */
    long countByContaBloqueadaTrue();
}
