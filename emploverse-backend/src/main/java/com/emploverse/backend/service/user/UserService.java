package com.emploverse.backend.service.user;

import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.dto.user.UserDTO;
import com.emploverse.backend.model.user.RoleName;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Optional<UserDTO> findUserById(Long id);

    Optional<UserDTO> findUserByEmail(String email);

    UserDTO saveUser(SignupDTO signupDTO);

    UserDTO updateUser(Long id, UserDTO updatedUserDTO);

    void deleteUser(Long id);

    List<UserDTO> findAllUsers();

    void changeUserPassword(Long userId, String newPassword);

    boolean existsByEmail(String email);

    void assignRolesToUser(Long userId, Set<RoleName> roleNames);
}
