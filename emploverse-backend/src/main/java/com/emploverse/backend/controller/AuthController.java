package com.emploverse.backend.controller;

import com.emploverse.backend.dto.CompletePasswordResetRequest;
import com.emploverse.backend.dto.LoginRequest;
import com.emploverse.backend.dto.LoginResponse;
import com.emploverse.backend.dto.RequestPasswordResetRequest;
import com.emploverse.backend.dto.SignupRequest;
import com.emploverse.backend.dto.SignupResponse;
import com.emploverse.backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = authService.login(loginRequest);
        System.out.println("login request: " + loginRequest.isRememberMe());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) throws Exception {
        SignupResponse signupResponse = authService.signup(signupRequest);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<Void> requestPasswordReset(
            @RequestBody RequestPasswordResetRequest requestPasswordResetRequest) throws Exception {
        authService.requestPasswordReset(requestPasswordResetRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete-password-reset")
    public ResponseEntity<Void> completePasswordReset(
            @RequestBody CompletePasswordResetRequest completePasswordResetRequest) throws Exception {
        authService.completePasswordReset(completePasswordResetRequest);
        return ResponseEntity.ok().build();
    }
}