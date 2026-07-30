package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.ForgotPasswordRequestDTO;
import com.aurix.platform.platform.dto.RefreshTokenRequestDTO;
import com.aurix.platform.platform.dto.ResetPasswordRequestDTO;
import com.aurix.platform.platform.service.AuthService;
import com.aurix.platform.shared.dto.LoginRequestDTO;
import com.aurix.platform.shared.dto.LoginResponseDTO;
import com.aurix.platform.shared.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para autenticação
 */
@RestController
@RequestMapping("/api/platform/auth")
@Tag(name = "Autenticação", description = "API para autenticação e autorização")
public class AuthController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    /**
     * Realiza login
     */
    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna token JWT")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("Recebida solicitação de login para email: {}", loginRequest.getEmail());
        LoginResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Valida token
     */
    @PostMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida um token JWT")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        log.info("Recebida solicitação para validar token");
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }

    /**
     * Obtém informações do usuário pelo token
     */
    @GetMapping("/me")
    @Operation(summary = "Obter informações do usuário", description = "Retorna informações do usuário autenticado")
    public ResponseEntity<UsuarioDTO> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        log.info("Recebida solicitação para obter informações do usuário");
        String token = authorization.replace("Bearer ", "");
        UsuarioDTO usuario = authService.getUserFromToken(token);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Cria novo usuário
     */
    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.info("Recebida solicitação para criar usuário: {}", usuarioDTO.getEmail());
        UsuarioDTO usuarioCriado = authService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    /**
     * Atualiza senha
     */
    @PutMapping("/password")
    @Operation(summary = "Atualizar senha", description = "Atualiza a senha do usuário")
    public ResponseEntity<Void> updatePassword(@RequestHeader("Authorization") String authorization, @RequestParam String novaSenha) {
        log.info("Recebida solicitação para atualizar senha");
        String token = authorization.replace("Bearer ", "");
        Long usuarioId = authService.getUserFromToken(token).getId();
        authService.atualizarSenha(usuarioId, novaSenha);
        return ResponseEntity.noContent().build();
    }

    /**
     * Bloqueia/desbloqueia usuário
     */
    @PutMapping("/toggle-block/{usuarioId}")
    @Operation(summary = "Bloquear/desbloquear usuário", description = "Altera o status de bloqueio do usuário")
    public ResponseEntity<Void> toggleBlockUser(@PathVariable Long usuarioId) {
        log.info("Recebida solicitação para alterar bloqueio do usuário ID: {}", usuarioId);
        authService.toggleBloqueioUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar redefinição de senha", description = "Envia email com link para redefinição de senha")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
        log.info("Recebida solicitação de forgot-password para email: {}", request.getEmail());
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Redefinir senha", description = "Redefine a senha usando token de recuperação")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        log.info("Recebida solicitação de reset-password");
        authService.resetPassword(request.getToken(), request.getNovaSenha());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Renova o token JWT usando refresh token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO request) {
        log.info("Recebida solicitação de refresh token");
        LoginResponseDTO response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @java.lang.SuppressWarnings("all")
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }
}
