package com.emploverse.backend.service;

import com.emploverse.backend.model.Role;
import com.emploverse.backend.model.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);

    Optional<Role> findRoleById(Long id);

    Optional<Role> findRoleByName(RoleName name);

    List<Role> findAllRoles();

    Role updateRole(Long id, Role role);

    void deleteRole(Long id);
}
