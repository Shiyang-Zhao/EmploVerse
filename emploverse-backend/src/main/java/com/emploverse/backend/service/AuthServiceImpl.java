package com.emploverse.backend.service;

import com.emploverse.backend.dto.EmployeeDTO;
import com.emploverse.backend.dto.LoginRequest;
import com.emploverse.backend.dto.LoginResponse;
import com.emploverse.backend.dto.UserDTO;
import com.emploverse.backend.model.Employee;
import com.emploverse.backend.model.Role;
import com.emploverse.backend.model.RoleName;
import com.emploverse.backend.model.User;
import com.emploverse.backend.repository.EmployeeRepository;
import com.emploverse.backend.repository.RoleRepository;
import com.emploverse.backend.repository.UserRepository;
import com.emploverse.backend.dto.SignupRequest;
import com.emploverse.backend.dto.SignupResponse;
import com.emploverse.backend.util.EmployeeMapper;
import com.emploverse.backend.util.JwtUtil;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder,
            UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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
        final String jwt = jwtUtil.generateToken(userDetails);

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
}