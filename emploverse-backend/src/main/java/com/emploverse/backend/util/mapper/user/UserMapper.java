package com.emploverse.backend.util.mapper.user;

import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.dto.user.UserDTO;
import com.emploverse.backend.model.user.User;
import com.emploverse.backend.model.user.Role;
import com.emploverse.backend.model.user.RoleName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

        @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRoleNames")
        @Mapping(source = "employee.id", target = "employeeId")
        UserDTO userToUserDTO(User user);

        @Mapping(source = "roles", target = "roles", qualifiedByName = "roleNamesToRoles")
        @Mapping(source = "employeeId", target = "employee.id")
        @Mapping(target = "password", ignore = true)
        @Mapping(target = "lastLogin", ignore = true)
        User userDTOToUser(UserDTO userDTO);

        @Mapping(target = "roles", ignore = true)
        @Mapping(target = "employee", ignore = true)
        @Mapping(target = "enabled", constant = "true")
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "lastLogin", ignore = true)
        User signupDTOToUser(SignupDTO signupDTO);

        @Named("rolesToRoleNames")
        default Set<RoleName> mapRolesToRoleNames(Set<Role> roles) {
                return roles.stream().map(Role::getName).collect(Collectors.toSet());
        }

        @Named("roleNamesToRoles")
        default Set<Role> mapRoleNamesToRoles(Set<RoleName> roleNames) {
                return roleNames.stream().map(roleName -> {
                        Role role = new Role();
                        role.setName(roleName);
                        return role;
                }).collect(Collectors.toSet());
        }
}
