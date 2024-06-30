package com.emploverse.backend.util.mapper.user;

import com.emploverse.backend.model.user.Role;
import com.emploverse.backend.model.user.RoleName;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    default Set<RoleName> mapRolesToRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<Role> mapRoleNamesToRoles(Set<RoleName> roleNames) {
        return roleNames.stream()
                .map(Role::new)
                .collect(Collectors.toSet());
    }
}
