package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.PasswordResetToken;
import com.aurix.platform.platform.entity.RefreshToken;
import com.aurix.platform.platform.repository.PasswordResetTokenRepository;
import com.aurix.platform.platform.repository.RefreshTokenRepository;
import com.aurix.platform.shared.repository.UsuarioRepository;
import com.aurix.platform.shared.dto.LoginRequestDTO;
import com.aurix.platform.shared.dto.LoginResponseDTO;
import com.aurix.platform.shared.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private Environment environment;

    private AuthService authService;

    @Captor
    private ArgumentCaptor<PasswordResetToken> passwordResetTokenCaptor;

    @Captor
    private ArgumentCaptor<RefreshToken> refreshTokenCaptor;

    @BeforeEach
    void setUp() {
        authService = new AuthService(usuarioRepository, jwtService, passwordEncoder,
            passwordResetTokenRepository, refreshTokenRepository, environment);
    }

    @Test
    void forgotPassword_shouldCreateToken() {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getId()).thenReturn(1L);
        when(usuarioRepository.findByEmailAndAtivoTrue("user@test.com"))
            .thenReturn(Optional.of(usuario));
        when(passwordResetTokenRepository.findByUsuarioId(1L)).thenReturn(List.of());
        when(environment.getActiveProfiles()).thenReturn(new String[]{"test"});

        authService.forgotPassword("user@test.com");

        verify(passwordResetTokenRepository).save(passwordResetTokenCaptor.capture());
        PasswordResetToken savedToken = passwordResetTokenCaptor.getValue();
        assertThat(savedToken.getUsuarioId()).isEqualTo(1L);
        assertThat(savedToken.getToken()).isNotNull();
        assertThat(savedToken.getExpiraEm()).isAfter(LocalDateTime.now());
        assertThat(savedToken.getUtilizado()).isFalse();
    }

    @Test
    void forgotPassword_shouldThrowWhenEmailNotFound() {
        when(usuarioRepository.findByEmailAndAtivoTrue("unknown@test.com"))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.forgotPassword("unknown@test.com"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Usuário não encontrado");
    }

    @Test
    void resetPassword_shouldSucceedWithValidToken() {
        Usuario usuario = mock(Usuario.class);
        PasswordResetToken token = mock(PasswordResetToken.class);
        when(token.getUtilizado()).thenReturn(false);
        when(token.getExpiraEm()).thenReturn(LocalDateTime.now().plusMinutes(10));
        when(token.getUsuarioId()).thenReturn(1L);
        when(passwordResetTokenRepository.findByToken("valid-token"))
            .thenReturn(Optional.of(token));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("novaSenha123")).thenReturn("encoded-senha");

        authService.resetPassword("valid-token", "novaSenha123");

        verify(passwordEncoder).encode("novaSenha123");
        verify(usuario).setSenha("encoded-senha");
        verify(usuario).setDataExpiracaoSenha(any(LocalDateTime.class));
        verify(usuario).resetarTentativasLogin();
        verify(usuarioRepository).save(usuario);
        verify(token).setUtilizado(true);
        verify(passwordResetTokenRepository).save(token);
    }

    @Test
    void resetPassword_shouldThrowWhenTokenExpired() {
        PasswordResetToken token = mock(PasswordResetToken.class);
        when(token.getUtilizado()).thenReturn(false);
        when(token.getExpiraEm()).thenReturn(LocalDateTime.now().minusMinutes(5));
        when(passwordResetTokenRepository.findByToken("expired-token"))
            .thenReturn(Optional.of(token));

        assertThatThrownBy(() -> authService.resetPassword("expired-token", "novaSenha123"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Token expirado");
    }

    @Test
    void resetPassword_shouldThrowWhenTokenAlreadyUsed() {
        PasswordResetToken token = mock(PasswordResetToken.class);
        when(token.getUtilizado()).thenReturn(true);
        when(passwordResetTokenRepository.findByToken("used-token"))
            .thenReturn(Optional.of(token));

        assertThatThrownBy(() -> authService.resetPassword("used-token", "novaSenha123"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Token já utilizado");
    }

    @Test
    void refreshToken_shouldGenerateNewJwt() {
        RefreshToken oldToken = mock(RefreshToken.class);
        when(oldToken.getRevogado()).thenReturn(false);
        when(oldToken.getExpiraEm()).thenReturn(LocalDateTime.now().plusDays(1));
        when(oldToken.getUsuarioId()).thenReturn(1L);
        when(refreshTokenRepository.findByToken("old-refresh-token"))
            .thenReturn(Optional.of(oldToken));

        Usuario usuario = mock(Usuario.class);
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getEmail()).thenReturn("user@test.com");
        when(usuario.getNome()).thenReturn("User");
        when(usuario.getRoles()).thenReturn(Set.of());
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        when(jwtService.generateToken(eq("user@test.com"), eq(1L), eq("User"), any(), any()))
            .thenReturn("new-jwt-token");

        LoginResponseDTO response = authService.refreshToken("old-refresh-token");

        verify(oldToken).setRevogado(true);
        verify(refreshTokenRepository, times(2)).save(refreshTokenCaptor.capture());
        List<RefreshToken> savedTokens = refreshTokenCaptor.getAllValues();
        assertThat(savedTokens.get(0)).isEqualTo(oldToken);
        RefreshToken newToken = savedTokens.get(1);
        assertThat(newToken.getUsuarioId()).isEqualTo(1L);
        assertThat(newToken.getToken()).isNotNull();
        assertThat(newToken.getExpiraEm()).isAfter(LocalDateTime.now());
        assertThat(newToken.getRevogado()).isFalse();
        assertThat(response.getToken()).isEqualTo("new-jwt-token");
        assertThat(response.getTipoToken()).isEqualTo("Bearer");
    }

    @Test
    void login_shouldAutoUnlockAfterTimeout() {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getContaBloqueada()).thenReturn(true);
        when(usuario.getUltimoLogin()).thenReturn(LocalDateTime.now().minusMinutes(6));
        when(usuario.isSenhaExpirada()).thenReturn(false);
        when(usuario.getSenha()).thenReturn("encoded-senha");
        when(usuario.getEmail()).thenReturn("user@test.com");
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getNome()).thenReturn("User");
        when(usuario.getRoles()).thenReturn(Set.of());

        when(usuarioRepository.findByEmailAndAtivoTrue("user@test.com"))
            .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("correct-senha", "encoded-senha")).thenReturn(true);
        when(jwtService.generateToken(anyString(), any(), anyString(), any(), any()))
            .thenReturn("jwt-token");

        LoginRequestDTO loginRequest = new LoginRequestDTO("user@test.com", "correct-senha");
        LoginResponseDTO response = authService.login(loginRequest);

        verify(usuario, times(2)).resetarTentativasLogin();
        verify(usuarioRepository, atLeastOnce()).save(usuario);
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");
    }
}
