package com.aurix.platform.customer.security.integration;

import com.aurix.platform.customer.AurixCustomerApplication;
import com.aurix.platform.customer.security.repository.MfaConfigRepository;
import com.aurix.platform.customer.security.repository.MfaTokenRepository;
import com.aurix.platform.customer.security.repository.PermissaoGranularRepository;
import com.aurix.platform.customer.security.repository.UsuarioRepository;
import com.aurix.platform.shared.dto.LoginRequestDTO;
import com.aurix.platform.shared.dto.LoginResponseDTO;
import com.aurix.platform.shared.dto.UsuarioDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AurixCustomerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SecurityFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MfaConfigRepository mfaConfigRepository;

    @Autowired
    private MfaTokenRepository mfaTokenRepository;

    @Autowired
    private PermissaoGranularRepository permissaoGranularRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        mfaTokenRepository.deleteAll();
        mfaConfigRepository.deleteAll();
        permissaoGranularRepository.deleteAll();
        usuarioRepository.deleteAll();

        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void testRegistrarUsuario() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Jackson Wendel");
        dto.setEmail("jackson@aurix.com");
        dto.setSenha("senha123");

        ResponseEntity<UsuarioDTO> response = rest.postForEntity(url("/auth/register"), dto, UsuarioDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("jackson@aurix.com");
    }

    @Test
    void testLogin() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Jackson Wendel");
        dto.setEmail("jackson@aurix.com");
        dto.setSenha("senha123");
        rest.postForEntity(url("/auth/register"), dto, UsuarioDTO.class);

        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("jackson@aurix.com");
        login.setSenha("senha123");

        ResponseEntity<LoginResponseDTO> response = rest.postForEntity(url("/auth/login"), login, LoginResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotNull();
        assertThat(response.getBody().getTipoToken()).isEqualTo("Bearer");
    }

    @Test
    void testValidarToken() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Jackson Wendel");
        dto.setEmail("jackson@aurix.com");
        dto.setSenha("senha123");
        rest.postForEntity(url("/auth/register"), dto, UsuarioDTO.class);

        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("jackson@aurix.com");
        login.setSenha("senha123");
        ResponseEntity<LoginResponseDTO> loginResponse = rest.postForEntity(
            url("/auth/login"), login, LoginResponseDTO.class);
        String token = loginResponse.getBody().getToken();

        ResponseEntity<Boolean> response = rest.postForEntity(
            url("/auth/validate?token=" + token), null, Boolean.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
    }

    @Test
    void testConfigurarMfa() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Jackson Wendel");
        dto.setEmail("jackson@aurix.com");
        dto.setSenha("senha123");
        ResponseEntity<UsuarioDTO> usuarioResponse = rest.postForEntity(
            url("/auth/register"), dto, UsuarioDTO.class);
        Long usuarioId = usuarioResponse.getBody().getId();

        ResponseEntity<Map> response = rest.postForEntity(
            url("/api/mfa/configurar?usuarioId=" + usuarioId + "&tipoMfa=SMS&valorConfigurado=+5511999999999"),
            null, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("id")).isNotNull();
    }

    @Test
    void testListarConfiguracoesMfa() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Jackson Wendel");
        dto.setEmail("jackson@aurix.com");
        dto.setSenha("senha123");
        ResponseEntity<UsuarioDTO> usuarioResponse = rest.postForEntity(
            url("/auth/register"), dto, UsuarioDTO.class);
        Long usuarioId = usuarioResponse.getBody().getId();

        rest.postForEntity(
            url("/api/mfa/configurar?usuarioId=" + usuarioId + "&tipoMfa=SMS&valorConfigurado=+5511999999999"),
            null, Map.class);

        ResponseEntity<List> response = rest.getForEntity(
            url("/api/mfa/configuracoes/" + usuarioId), List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testGerarEValidarTokenMfa() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Jackson Wendel");
        dto.setEmail("jackson@aurix.com");
        dto.setSenha("senha123");
        ResponseEntity<UsuarioDTO> usuarioResponse = rest.postForEntity(
            url("/auth/register"), dto, UsuarioDTO.class);
        Long usuarioId = usuarioResponse.getBody().getId();

        rest.postForEntity(
            url("/api/mfa/configurar?usuarioId=" + usuarioId + "&tipoMfa=EMAIL&valorConfigurado=jackson@aurix.com"),
            null, Map.class);

        ResponseEntity<Map> tokenResponse = rest.postForEntity(
            url("/api/mfa/gerar-token?usuarioId=" + usuarioId + "&sessaoId=sessao001&tipoMfa=EMAIL"),
            null, Map.class);
        String codigoToken = (String) tokenResponse.getBody().get("codigoToken");
        String codigo = (String) tokenResponse.getBody().get("codigo");

        ResponseEntity<Map> validResponse = rest.postForEntity(
            url("/api/mfa/validar-token?codigoToken=" + codigoToken + "&codigoInformado=" + codigo),
            null, Map.class);
        assertThat(validResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(validResponse.getBody().get("valido")).isEqualTo(true);
    }

    @Test
    void testCriptografar() {
        Map<String, String> request = Map.of("texto", "dado_sensivel_123");

        ResponseEntity<Map> response = rest.postForEntity(
            url("/api/security/criptografia/criptografar"), request, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("valorCriptografado");
    }

    @Test
    void testDescriptografar() {
        Map<String, String> request = Map.of("texto", "dado_sensivel_123");
        ResponseEntity<Map> encrypted = rest.postForEntity(
            url("/api/security/criptografia/criptografar"), request, Map.class);
        String valorCriptografado = (String) encrypted.getBody().get("valorCriptografado");

        Map<String, String> decryptRequest = Map.of("valorCriptografado", valorCriptografado);
        ResponseEntity<Map> response = rest.postForEntity                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   