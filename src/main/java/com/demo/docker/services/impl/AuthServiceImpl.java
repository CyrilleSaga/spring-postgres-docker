package com.demo.docker.services.impl;

import com.demo.docker.dto.UserDTO;
import com.demo.docker.dto.request.LoginRequest;
import com.demo.docker.dto.request.RegisterRequest;
import com.demo.docker.entity.UserEntity;
import com.demo.docker.exceptions.ResourceNotFoundException;
import com.demo.docker.repository.UserRepository;
import com.demo.docker.services.AuthService;
import com.demo.docker.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public UserDTO register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserEntity authenticate(LoginRequest input) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                input.getEmail(),
                                input.getPassword()
                        )
                );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return userRepository.findByEmail(input.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("The user with email " + input.getEmail() + " was not found"));
        } catch (Exception e) {
            throw new BadCredentialsException("The username or password is incorrect");
        }
    }

    @Override
    public boolean isEmailAlreadyInUse(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
