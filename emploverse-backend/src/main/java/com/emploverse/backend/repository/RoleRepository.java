package com.emploverse.backend.repository;

import com.emploverse.backend.model.Role;
import com.emploverse.backend.model.RoleName;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

    boolean existsByName(RoleName name);
}