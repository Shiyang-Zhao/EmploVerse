package com.emploverse.backend.service;

import com.emploverse.backend.dto.CompletePasswordResetRequest;
import com.emploverse.backend.dto.LoginRequest;
import com.emploverse.backend.dto.LoginResponse;
import com.emploverse.backend.dto.PasswordResetRequest;
import com.emploverse.backend.dto.PasswordResetResponse;
import com.emploverse.backend.dto.SignupRequest;
import com.emploverse.backend.dto.SignupResponse;

public interface AuthService {
    SignupResponse signup(SignupRequest signupRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest) throws Exception;

    void logout();

    PasswordResetResponse requestPasswordReset(PasswordResetRequest passwordResetRequest) throws Exception;

    void completePasswordReset(CompletePasswordResetRequest completePasswordResetRequest) throws Exception;
}