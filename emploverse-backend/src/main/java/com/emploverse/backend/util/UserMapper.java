package com.emploverse.backend.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.emploverse.backend.dto.UserDTO;
import com.emploverse.backend.model.User;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRoleNames")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "employeeId", target = "employee.id")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleNamesToRoles")
    @Mapping(target = "password", ignore = true)
    User userDTOToUser(UserDTO userDTO);
}
