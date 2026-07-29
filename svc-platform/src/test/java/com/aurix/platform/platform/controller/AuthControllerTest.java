package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.service.AuthService;
import com.aurix.platform.shared.dto.LoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService)).build();
    }

    @Test
    void forgotPasswordShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/platform/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\"}"))
            .andExpect(status().isOk());
        verify(authService).forgotPassword("test@test.com");
    }

    @Test
    void resetPasswordShouldReturnNoContent() throws Exception {
        mockMvc.perform(post("/api/platform/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\":\"abc\",\"novaSenha\":\"New@1234\"}"))
            .andExpect(status().isNoContent());
        verify(authService).resetPassword("abc", "New@1234");
    }

    @Test
    void refreshTokenShouldReturnLoginResponse() throws Exception {
        when(authService.refreshToken("valid-refresh"))
            .thenReturn(LoginResponseDTO.builder().token("new-jwt").tipoToken("Bearer").build());

        mockMvc.perform(post("/api/platform/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"refreshToken\":\"valid-refresh\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("new-jwt"));
    }
}
