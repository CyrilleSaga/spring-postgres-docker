package com.demo.docker.controllers;

import com.demo.docker.dto.UserDTO;
import com.demo.docker.dto.request.LoginRequest;
import com.demo.docker.dto.request.RegisterRequest;
import com.demo.docker.dto.response.LoginResponse;
import com.demo.docker.entity.UserEntity;
import com.demo.docker.exceptions.EmailAlreadyUserException;
import com.demo.docker.services.AuthService;
import com.demo.docker.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest registerDTO) {
        log.info("Rest API to register a new user");

        // Check if the email is already in use
        if (authService.isEmailAlreadyInUse(registerDTO.getEmail())) {
            throw new EmailAlreadyUserException("The email is already in use");
        }

        UserDTO registeredUser = authService.register(registerDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        UserEntity authenticatedUser = authService.authenticate(loginRequest);

        String jwtToken = jwtUtils.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(jwtToken)
                .expiresIn(jwtUtils.getExpirationTime())
                .type("Bearer")
                .build();

        return ResponseEntity.ok(loginResponse);
    }

}
