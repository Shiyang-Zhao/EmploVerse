package com.emploverse.backend.service.auth;

import com.emploverse.backend.dto.jwt.JwtRequest;
import com.emploverse.backend.dto.jwt.JwtResponse;
import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.dto.user.UserDTO;
import com.emploverse.backend.service.user.UserService;
import com.emploverse.backend.util.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new JwtResponse(jwt);
    }

    @Override
    public UserDTO signup(SignupDTO signupDTO) throws Exception {
        if (!signupDTO.getPassword().equals(signupDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        return userService.saveUser(signupDTO);
    }

    @Override
    public void logout() {
        // Add any additional logout logic if needed
    }
}
