package com.emploverse.backend.util;

import com.emploverse.backend.model.Role;
import com.emploverse.backend.model.RoleName;
import com.emploverse.backend.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {
    @Autowired
    protected RoleRepository roleRepository;

    public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Named("rolesToRoleNames")
    public Set<RoleName> rolesToRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @Named("roleNamesToRoles")
    public Set<Role> roleNamesToRoles(Set<RoleName> roleNames) {
        return roleNames.stream()
                .map(this::roleNameToRole)
                .collect(Collectors.toSet());
    }

    public RoleName roleToRoleName(Role role) {
        return role != null ? role.getName() : null;
    }

    public Role roleNameToRole(RoleName roleName) {
        if (roleName == null) {
            return null;
        }
        return roleRepository.findByName(roleName).orElse(null);
    }
}
