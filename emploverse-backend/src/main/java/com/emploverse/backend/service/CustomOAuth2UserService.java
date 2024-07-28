package com.emploverse.backend.service;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

import com.emploverse.backend.dto.SignupRequest;
import com.emploverse.backend.model.User;
import com.emploverse.backend.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Value("${password.unusable-password}")
    private String unusablePassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("Loading user from OAuth2 provider with request: {}", userRequest);

        // First call the loadUser from DefaultOAuth2UserService to retrieve the user
        OAuth2User oauth2User = super.loadUser(userRequest);

        logger.info("Retrieved user details: {}", oauth2User.getAttributes());
        return processOAuth2User(oauth2User, userRequest);
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User, OAuth2UserRequest userRequest) {
        String email = oauth2User.getAttribute("email");
        logger.info("processOAuth2User");

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            SignupRequest signupRequest = SignupRequest.builder()
                    .email(email)
                    .username(oauth2User.getAttribute("name"))
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

        return oauth2User;
    }

}