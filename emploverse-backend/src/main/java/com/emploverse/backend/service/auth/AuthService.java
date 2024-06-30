package com.emploverse.backend.service.auth;

import com.emploverse.backend.dto.jwt.JwtRequest;
import com.emploverse.backend.dto.jwt.JwtResponse;
import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.dto.user.UserDTO;

public interface AuthService {
    JwtResponse login(JwtRequest jwtRequest) throws Exception;

    UserDTO signup(SignupDTO signupDTO) throws Exception;

    void logout();
}
