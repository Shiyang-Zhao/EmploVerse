package com.emploverse.backend.service;

import com.emploverse.backend.dto.LoginRequest;
import com.emploverse.backend.dto.LoginResponse;
import com.emploverse.backend.dto.SignupRequest;
import com.emploverse.backend.dto.SignupResponse;

public interface AuthService {
    SignupResponse signup(SignupRequest signupRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest) throws Exception;

    void logout();
}