package com.aurix.platform.platform.integration;

import com.aurix.platform.platform.PlatformApplication;
import com.aurix.platform.platform.dto.RelatorioComplianceDTO;
import com.aurix.platform.platform.repository.LogAuditoriaRepository;
import com.aurix.platform.platform.repository.SessaoAuditoriaRepository;
import com.aurix.platform.shared.dto.LogAuditoriaDTO;
import com.aurix.platform.shared.entity.LogAuditoria;
import java.time.LocalDateTime;
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

@SpringBootTest(classes = PlatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuditFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private LogAuditoriaRepository logAuditoriaRepository;

    @Autowired
    private SessaoAuditoriaRepository sessaoAuditoriaRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        logAuditoriaRepository.deleteAll();
        sessaoAuditoriaRepository.deleteAll();
        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/platform/logs" + path;
    }

    @Test
    void deveCriarLogAuditoria() {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setAcao("LOGIN");
        dto.setEntidade("Usuario");
        dto.setEntidadeId(1L);
        dto.setUsuarioId(100L);
        dto.setUsuarioNome("joao.silva");
        dto.setTipoAcao(LogAuditoria.TipoAcao.LOGIN);
        dto.setCategoria(LogAuditoria.CategoriaAuditoria.SEGURANCA);
        dto.setDataAcao(LocalDateTime.now());
        dto.setResultado("SUCESSO");

        ResponseEntity<LogAuditoriaDTO> response = rest.postForEntity(
            url(""), dto, LogAuditoriaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAcao()).isEqualTo("LOGIN");
    }

    @Test
    void deveBuscarLogPorId() {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setAcao("CREATE");
        dto.setEntidade("Conta");
        dto.setEntidadeId(10L);
        dto.setUsuarioId(200L);
        dto.setTipoAcao(LogAuditoria.TipoAcao.CREATE);
        dto.setCategoria(LogAuditoria.CategoriaAuditoria.OPERACIONAL);
        dto.setDataAcao(LocalDateTime.now());
        dto.setResultado("SUCESSO");
        ResponseEntity<LogAuditoriaDTO> criado = rest.postForEntity(
            url(""), dto, LogAuditoriaDTO.class);
        Long id = criado.getBody().getId();

        ResponseEntity<LogAuditoriaDTO> response = rest.getForEntity(
            url("/" + id), LogAuditoriaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void deveListarLogsPorUsuario() {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setAcao("UPDATE");
        dto.setEntidade("Proposta");
        dto.setUsuarioId(300L);
        dto.setTipoAcao(LogAuditoria.TipoAcao.UPDATE);
        dto.setCategoria(LogAuditoria.CategoriaAuditoria.OPERACIONAL);
        dto.setDataAcao(LocalDateTime.now());
        dto.setResultado("SUCESSO");
        rest.postForEntity(url(""), dto, LogAuditoriaDTO.class);

        ResponseEntity<LogAuditoriaDTO[]> response = rest.getForEntity(
            url("/usuario/300"), LogAuditoriaDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void deveListarLogsPorPeriodo() {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setAcao("EXPORT");
        dto.setEntidade("Relatorio");
        dto.setUsuarioId(400L);
        dto.setTipoAcao(LogAuditoria.TipoAcao.EXPORT);
        dto.setCategoria(LogAuditoria.CategoriaAuditoria.COMPLIANCE);
        dto.setDataAcao(LocalDateTime.now());
        dto.setResultado("SUCESSO");
        rest.postForEntity(url(""), dto, LogAuditoriaDTO.class);

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now().plusDays(1);
        ResponseEntity<LogAuditoriaDTO[]> response = rest.getForEntity(
            url("/periodo?inicio=" + inicio + "&fim=" + fim), LogAuditoriaDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deveListarLogsCriticos() {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setAcao("DELETE");
        dto.setEntidade("Usuario");
        dto.setUsuarioId(500L);
        dto.setTipoAcao(LogAuditoria.TipoAcao.DELETE);
        dto.setCategoria(LogAuditoria.CategoriaAuditoria.SEGURANCA);
        dto.setDataAcao(LocalDateTime.now());
        dto.setNivel(LogAuditoria.NivelAuditoria.CRITICO);
        dto.setResultado("FALHA");
        rest.postForEntity(url(""), dto, LogAuditoriaDTO.class);

        ResponseEntity<LogAuditoriaDTO[]> response = rest.getForEntity(
            url("/criticos"), LogAuditoriaDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deveGerarRelatorioCompliance() {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setAcao("APPROVE");
        dto.setEntidade("Transacao");
        dto.setUsuarioId(600L);
        dto.setTipoAcao(LogAuditoria.TipoAcao.APPROVE);
        dto.setCategoria(LogAuditoria.CategoriaAuditoria.COMPLIANCE);
        dto.setDataAcao(LocalDateTime.now());
        dto.setResultado("SUCESSO");
        rest.postForEntity(url(""), dto, LogAuditoriaDTO.class);

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now().plusDays(1);
        ResponseEntity<RelatorioComplianceDTO> response = rest.getForEntity(
            url("/relatorios/compliance?inicio=" + inicio + "&fim=" + fim),
            RelatorioComplianceDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
