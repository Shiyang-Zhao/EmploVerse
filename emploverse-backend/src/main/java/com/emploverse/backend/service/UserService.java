package com.emploverse.backend.service;

import com.emploverse.backend.dto.UserDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Optional<UserDTO> findUserById(Long id);

    Optional<UserDTO> findUserByUsername(String email);

    Optional<UserDTO> findUserByEmail(String email);

    Page<UserDTO> findUsersBySortPage(int page, int size, String sortBy, String sortDir);

    List<UserDTO> findAllUsers();

    UserDTO updateUserById(Long id, UserDTO updatedUserDTO);

    void deleteUserById(Long id);
}