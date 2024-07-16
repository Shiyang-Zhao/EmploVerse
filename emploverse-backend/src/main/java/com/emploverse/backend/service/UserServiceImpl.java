package com.emploverse.backend.service;

import com.emploverse.backend.dto.UserDTO;
import com.emploverse.backend.model.User;
import com.emploverse.backend.repository.UserRepository;
import com.emploverse.backend.util.RoleMapper;
import com.emploverse.backend.util.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        if (user.getPassword() == null) {
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
    public Optional<UserDTO> findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDTO);
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
    public UserDTO updateUserById(Long id, UserDTO updatedUserDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUserDTO.getUsername());
            user.setEmail(updatedUserDTO.getEmail());
            user.setEnabled(updatedUserDTO.isEnabled());
            user.setLastLogin(updatedUserDTO.getLastLogin());
            user.setRoles(roleMapper.roleNamesToRoles(updatedUserDTO.getRoles()));
            User updatedUser = userRepository.save(user);
            return userMapper.userToUserDTO(updatedUser);
        }).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        userRepository.delete(user);
    }

}