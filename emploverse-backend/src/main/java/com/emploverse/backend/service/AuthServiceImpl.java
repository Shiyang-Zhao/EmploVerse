package com.emploverse.backend.service;

import com.emploverse.backend.dto.CompletePasswordResetRequest;
import com.emploverse.backend.dto.EmployeeDTO;
import com.emploverse.backend.dto.LoginRequest;
import com.emploverse.backend.dto.LoginResponse;
import com.emploverse.backend.dto.RequestPasswordResetRequest;
import com.emploverse.backend.dto.UserDTO;
import com.emploverse.backend.model.Employee;
import com.emploverse.backend.model.PasswordResetToken;
import com.emploverse.backend.model.Role;
import com.emploverse.backend.model.RoleName;
import com.emploverse.backend.model.User;
import com.emploverse.backend.repository.EmployeeRepository;
import com.emploverse.backend.repository.PasswordResetTokenRepository;
import com.emploverse.backend.repository.RoleRepository;
import com.emploverse.backend.repository.UserRepository;
import com.emploverse.backend.dto.SignupRequest;
import com.emploverse.backend.dto.SignupResponse;
import com.emploverse.backend.util.EmployeeMapper;
import com.emploverse.backend.util.JwtUtil;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${client.base-url}")
    private String baseUrl;

    @Value("${password.reset-token-expiration-time}")
    private Long passwordResetTokenExpirationTime;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmployeeMapper employeeMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            EmployeeRepository employeeRepository, PasswordResetTokenRepository passwordResetTokenRepository,
            EmployeeMapper employeeMapper, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder,
            UserService userService, EmailService emailService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.employeeMapper = employeeMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public SignupResponse signup(SignupRequest signupRequest) throws Exception {
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEnabled(true);

        Optional<Role> userRoleOptional = roleRepository.findByName(RoleName.ROLE_USER);
        Optional<Role> adminRoleOptional = roleRepository.findByName(RoleName.ROLE_ADMIN);
        if (userRoleOptional.isEmpty() || adminRoleOptional.isEmpty()) {
            throw new IllegalStateException("Required roles not found");
        }
        Role userRole = userRoleOptional.get();
        Role adminRole = adminRoleOptional.get();

        user.setRoles(Set.of(userRole, adminRole));
        User savedUser = userRepository.save(user);
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .userId(savedUser.getId())
                .build();
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employee.setUser(savedUser);
        employeeRepository.save(employee);
        Set<RoleName> allRoles = savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return new SignupResponse(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.isEnabled(),
                allRoles);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        long expirationTime = loginRequest.isRememberMe() ? jwtUtil.getRememberMeExpirationTime()
                : jwtUtil.getDefaultExpirationTime();
        final String jwt = jwtUtil.generateToken(userDetails, expirationTime);
        System.out.println("login request: " + loginRequest.isRememberMe());
        System.out.println("jwt expiration time: " + expirationTime);
        UserDTO userDTO = userService.findUserByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

        userDTO.setLastLogin(new Date());
        userService.updateUserById(userDTO.getId(), userDTO);

        return new LoginResponse(userDTO.getId(), jwt, userDTO.getUsername(), userDTO.getEmail(), userDTO.isEnabled(),
                userDTO.getLastLogin(), userDTO.getEmployeeId(), userDTO.getRoles());
    }

    @Override
    public void logout() {
        // Add any additional logout logic if needed
    }

    @Override
    public void requestPasswordReset(RequestPasswordResetRequest requestPasswordResetRequest) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(requestPasswordResetRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new Exception("User with email " + requestPasswordResetRequest.getEmail() + " not found.");
        }
        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(new Date(System.currentTimeMillis() + passwordResetTokenExpirationTime)) // 1 hour
                                                                                                     // expiration
                .build();
        passwordResetTokenRepository.save(passwordResetToken);

        String resetUrl = baseUrl + "/password-reset?token=" + token;
        String emailContent = String.format(
                "Dear %s,%n%n" +
                        "We have received a request to reset the password for your account associated with this email address. "
                        +
                        "If you did not request this change, please ignore this email. Otherwise, please click the link below to reset your password:%n%n"
                        +
                        "%s%n%n" +
                        "This link will expire in 10 minutes.%n%n" +
                        "Best regards,%n" +
                        "EmploVerse Team",
                user.getUsername(), resetUrl);

        emailService.sendSimpleMessage(requestPasswordResetRequest.getEmail(), "Password Reset Request", emailContent);
    }

    @Override
    public void completePasswordReset(CompletePasswordResetRequest completePasswordResetRequest) throws Exception {
        Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenRepository
                .findByToken(completePasswordResetRequest.getToken());
        if (resetTokenOptional.isEmpty() || resetTokenOptional.get().getExpiryDate().before(new Date())) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        User user = resetTokenOptional.get().getUser();

        if (!completePasswordResetRequest.getNewPassword().equals(completePasswordResetRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(completePasswordResetRequest.getNewPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetTokenOptional.get());
    }
}