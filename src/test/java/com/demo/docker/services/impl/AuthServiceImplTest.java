package com.demo.docker.services.impl;

import com.demo.docker.dto.UserDTO;
import com.demo.docker.dto.request.LoginRequest;
import com.demo.docker.dto.request.RegisterRequest;
import com.demo.docker.entity.UserEntity;
import com.demo.docker.repository.UserRepository;
import com.demo.docker.services.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private UserEntity userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password123");

        userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPassword("encodedPassword");

        userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");
    }

    @Test
    void registerUserSuccessfully() {
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        UserDTO result = authService.register(registerRequest);

        assertEquals(userDTO, result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void authenticateUserSuccessfully() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(userEntity));

        UserEntity result = authService.authenticate(loginRequest);

        assertEquals(userEntity, result);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
    }

    @Test
    void authenticateUserThrowsBadCredentialsException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("The username or password is incorrect"));

        assertThrows(BadCredentialsException.class, () -> authService.authenticate(loginRequest));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void isEmailAlreadyInUseReturnsTrue() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(userEntity));

        boolean result = authService.isEmailAlreadyInUse(registerRequest.getEmail());

        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(registerRequest.getEmail());
    }

    @Test
    void isEmailAlreadyInUseReturnsFalse() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());

        boolean result = authService.isEmailAlreadyInUse(registerRequest.getEmail());

        assertFalse(result);
        verify(userRepository, times(1)).findByEmail(registerRequest.getEmail());
    }
}
