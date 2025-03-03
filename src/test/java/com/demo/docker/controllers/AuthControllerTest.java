package com.demo.docker.controllers;

import com.demo.docker.dto.UserDTO;
import com.demo.docker.dto.request.LoginRequest;
import com.demo.docker.dto.request.RegisterRequest;
import com.demo.docker.entity.UserEntity;
import com.demo.docker.services.AuthService;
import com.demo.docker.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private UserDTO userDTO;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password123");

        userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");

        userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPassword("encodedPassword");    }

    @Test
    void registerUserSuccessfully() throws Exception {
        when(authService.isEmailAlreadyInUse(registerRequest.getEmail())).thenReturn(false);
        when(authService.register(registerRequest)).thenReturn(userDTO);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        verify(authService, times(1)).isEmailAlreadyInUse(registerRequest.getEmail());
        verify(authService, times(1)).register(registerRequest);
    }

    @Test
    void authenticateUserSuccessfully() throws Exception {
        when(authService.authenticate(loginRequest)).thenReturn(userEntity);
        when(jwtUtils.generateToken(userEntity)).thenReturn("jwtToken");

        mockMvc.perform(post("/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk());

        verify(authService, times(1)).authenticate(loginRequest);
        verify(jwtUtils, times(1)).generateToken(userEntity);
    }

}
