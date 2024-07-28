package com.emploverse.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emploverse.backend.dto.SignupRequest;
import com.emploverse.backend.model.User;
import com.emploverse.backend.repository.UserRepository;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Value("${password.unusable-password}")
    private String unusablePassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("Loading user from OAuth2 provider with request: {}", userRequest);
        OidcUser oidcUser = super.loadUser(userRequest);
        logger.info("Retrieved user details: {}", oidcUser.getAttributes());

        return processOidcUser(oidcUser, userRequest);
    }

    private OidcUser processOidcUser(OidcUser oidcUser, OAuth2UserRequest userRequest) {
        String email = oidcUser.getEmail();
        logger.info("processOidcUser");

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            SignupRequest signupRequest = SignupRequest.builder()
                    .email(email)
                    .username(oidcUser.getAttribute("name"))
                    .password(unusablePassword)
                    .confirmPassword(unusablePassword)
                    .build();

            try {
                authService.signup(signupRequest);
                logger.info("Created new user with email: {}", email);
            } catch (Exception e) {
                logger.error("Error creating user with email: {}", email, e);
                throw new RuntimeException("Error creating user", e);
            }
        } else {
            logger.info("User with email: {} already exists", email);
        }
        return oidcUser;
    }
}