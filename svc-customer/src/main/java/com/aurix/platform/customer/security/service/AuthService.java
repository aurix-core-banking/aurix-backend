package com.aurix.platform.customer.security.service;

import com.aurix.platform.customer.security.entity.PasswordResetToken;
import com.aurix.platform.customer.security.entity.RefreshToken;
import com.aurix.platform.customer.security.repository.PasswordResetTokenRepository;
import com.aurix.platform.customer.security.repository.RefreshTokenRepository;
import com.aurix.platform.customer.security.repository.UsuarioRepository;
import com.aurix.platform.shared.dto.LoginRequestDTO;
import com.aurix.platform.shared.dto.LoginResponseDTO;
import com.aurix.platform.shared.dto.UsuarioDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Usuario;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço de autenticação
 */
@Service
@Transactional
public class AuthService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthService.class);
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Environment environment;

    /**
     * Autentica um usuário
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Tentativa de login para email: {}", loginRequest.getEmail());
        // Buscar usuário por email
        Usuario usuario = usuarioRepository.findByEmailAndAtivoTrue(loginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));
        // Verificar se a conta está bloqueada com auto-desbloqueio após 5 minutos
        if (usuario.getContaBloqueada()) {
            if (usuario.getUltimoLogin() != null && usuario.getUltimoLogin().plusMinutes(5).isAfter(LocalDateTime.now())) {
                throw new IllegalStateException("Conta bloqueada. Tente novamente em 5 minutos.");
            }
            usuario.resetarTentativasLogin();
            usuario.setContaBloqueada(false);
            usuarioRepository.save(usuario);
        }
        // Verificar se a senha expirou
        if (usuario.isSenhaExpirada()) {
            throw new IllegalStateException("Senha expirada. Solicite uma nova senha.");
        }
        // Verificar senha
        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
            usuario.incrementarTentativasLogin();
            usuarioRepository.save(usuario);
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        // Resetar tentativas de login
        usuario.resetarTentativasLogin();
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);
        // Gerar token JWT
        Set<String> roles = usuario.getRoles().stream().map(role -> role.getNome()).collect(Collectors.toSet());
        Set<String> permissions = usuario.getRoles().stream().flatMap(role -> role.getPermissions().stream()).map(permission -> permission.getNome()).collect(Collectors.toSet());
        String token = jwtService.generateToken(usuario.getEmail(), usuario.getId(), usuario.getNome(), roles, permissions);
        log.info("Login realizado com sucesso para usuário: {}", usuario.getEmail());
        return LoginResponseDTO.builder().token(token).tipoToken("Bearer").usuarioId(usuario.getId()).nome(usuario.getNome()).email(usuario.getEmail()).roles(roles).permissions(permissions).dataExpiracao(LocalDateTime.now().plusHours(24)).ultimoLogin(usuario.getUltimoLogin()).build();
    }

    /**
     * Valida um token JWT
     */
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {
        try {
            String email = jwtService.extractUsername(token);
            return jwtService.validateToken(token, email);
        } catch (Exception e) {
            log.error("Erro ao validar token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Obtém informações do usuário pelo token
     */
    @Transactional(readOnly = true)
    public UsuarioDTO getUserFromToken(String token) {
        String email = jwtService.extractUsername(token);
        Usuario usuario = usuarioRepository.findByEmailAndAtivoTrue(email).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return converterParaDTO(usuario);
    }

    /**
     * Cria um novo usuário
     */
    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        log.info("Criando usuário para email: {}", usuarioDTO.getEmail());
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setDataExpiracaoSenha(LocalDateTime.now().plusDays(90)); // 90 dias
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        log.info("Usuário criado com ID: {}", usuarioSalvo.getId());
        return converterParaDTO(usuarioSalvo);
    }

    /**
     * Atualiza senha do usuário
     */
    public void atualizarSenha(Long usuarioId, String novaSenha) {
        log.info("Atualizando senha do usuário ID: {}", usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuario.setDataExpiracaoSenha(LocalDateTime.now().plusDays(90));
        usuario.resetarTentativasLogin();
        usuarioRepository.save(usuario);
        log.info("Senha atualizada com sucesso");
    }

    /**
     * Bloqueia/desbloqueia usuário
     */
    public void toggleBloqueioUsuario(Long usuarioId) {
        log.info("Alterando status de bloqueio do usuário ID: {}", usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuario.setContaBloqueada(!usuario.getContaBloqueada());
        usuarioRepository.save(usuario);
        log.info("Status de bloqueio alterado para: {}", usuario.getContaBloqueada());
    }

    /**
     * Solicita reset de senha
     */
    public void forgotPassword(String email) {
        log.info("Solicitação de reset de senha para email: {}", email);
        Usuario usuario = usuarioRepository.findByEmailAndAtivoTrue(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        passwordResetTokenRepository.findByUsuarioId(usuario.getId())
            .forEach(passwordResetTokenRepository::delete);

        String tokenValue = UUID.randomUUID().toString();
        PasswordResetToken token = new PasswordResetToken(
            usuario.getId(),
            tokenValue,
            LocalDateTime.now().plusMinutes(15)
        );
        passwordResetTokenRepository.save(token);

        if (isDevProfile()) {
            log.info("Token de reset de senha para {}: {}", email, tokenValue);
        }
    }

    /**
     * Redefine a senha usando um token de reset
     */
    public void resetPassword(String tokenValue, String novaSenha) {
        PasswordResetToken token = passwordResetTokenRepository.findByToken(tokenValue)
            .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (token.getUtilizado()) {
            throw new IllegalStateException("Token já utilizado");
        }

        if (token.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new Illegal                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          