package com.demo.docker.services;

import com.demo.docker.dto.request.LoginRequest;
import com.demo.docker.dto.request.RegisterRequest;
import com.demo.docker.entity.UserEntity;

public interface AuthService {

    /**
     * Register a new user
     *
     * @param request the request object
     * @return the registered user
     */
    UserEntity register(RegisterRequest request);

    /**
     * Authenticate a user
     *
     * @param input the login request
     * @return the authenticated user
     */
    UserEntity authenticate(LoginRequest input);

    /**
     * Check if the email is already in use
     *
     * @param email the email to check
     * @return true if the email is already in use, false otherwise
     */
    boolean isEmailAlreadyInUse(String email);
}
