package com.emploverse.backend.service.auth;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.model.user.User;
import com.emploverse.backend.repository.user.UserRepository;
import com.emploverse.backend.service.user.UserService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Value("${UNUSABLE_PASSWORD}")
    private String unusablePassword;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            SignupDTO signupDTO = SignupDTO.builder()
                    .email(email)
                    .username(oauth2User.getAttribute("name"))
                    .password(unusablePassword)
                    .confirmPassword(unusablePassword)
                    .build();
            userService.saveUser(signupDTO);
            logger.info("processOAuth2User");
            
        }

        return oauth2User;
    }

}
