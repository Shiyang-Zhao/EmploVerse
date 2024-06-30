package com.emploverse.backend.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.model.user.User;
import com.emploverse.backend.repository.user.UserRepository;
import com.emploverse.backend.service.user.UserService;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Value("${UNUSABLE_PASSWORD}")
    private String unusablePassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            SignupDTO signupDTO = SignupDTO.builder()
                    .email(email)
                    .username(oidcUser.getAttribute("name"))
                    .password(unusablePassword)
                    .confirmPassword(unusablePassword)
                    .build();
            userService.saveUser(signupDTO);
        }
        return oidcUser;
    }
}
