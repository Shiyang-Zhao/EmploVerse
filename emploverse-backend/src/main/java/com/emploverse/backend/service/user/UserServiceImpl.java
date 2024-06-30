package com.emploverse.backend.service.user;

import com.emploverse.backend.model.user.Role;
import com.emploverse.backend.model.user.RoleName;
import com.emploverse.backend.model.user.User;
import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.dto.user.UserDTO;
import com.emploverse.backend.repository.user.RoleRepository;
import com.emploverse.backend.repository.user.UserRepository;
import com.emploverse.backend.util.mapper.user.RoleMapper;
import com.emploverse.backend.util.mapper.user.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, UserMapper userMapper,
            RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        if (user.getPassword() == null) {
            // Handle logic for users who registered via OAuth and do not have a password
            throw new UsernameNotFoundException("Account not found");
        }

        Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                grantedAuthorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id).map(userMapper::userToUserDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).map(userMapper::userToUserDTO);
    }

    @Override
    @Transactional
    public UserDTO saveUser(SignupDTO signupDTO) {
        User user = userMapper.signupDTOToUser(signupDTO);
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setEnabled(true);
        Set<Role> roles = roleMapper.mapRoleNamesToRoles(Set.of(RoleName.ROLE_USER));
        roleRepository.saveAll(roles);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUserDTO.getUsername());
            user.setEmail(updatedUserDTO.getEmail());
            user.setEnabled(updatedUserDTO.isEnabled());
            user.setRoles(roleMapper.mapRoleNamesToRoles(updatedUserDTO.getRoles()));
            User updatedUser = userRepository.save(user);
            return userMapper.userToUserDTO(updatedUser);
        }).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeUserPassword(Long userId, String newPassword) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Transactional
    public void assignRolesToUser(Long userId, Set<RoleName> roleNames) {
        userRepository.findById(userId).ifPresent(user -> {
            Set<Role> roles = roleNames.stream().map(Role::new).collect(Collectors.toSet());
            user.setRoles(roles);
            userRepository.save(user);
        });
    }
}
