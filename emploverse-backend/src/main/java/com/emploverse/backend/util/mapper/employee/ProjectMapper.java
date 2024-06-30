package com.emploverse.backend.util.mapper.employee;

import com.emploverse.backend.dto.employee.ProjectDTO;
import com.emploverse.backend.model.employee.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface ProjectMapper {

    @Mapping(source = "manager.id", target = "managerId")
    ProjectDTO toDto(Project project);

    @Mapping(source = "managerId", target = "manager.id")
    Project toEntity(ProjectDTO projectDTO);
}
