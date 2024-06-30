package com.emploverse.backend.util.mapper.employee;

import com.emploverse.backend.dto.employee.TaskDTO;
import com.emploverse.backend.model.employee.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignedEmployee.id", target = "assignedEmployeeId")
    TaskDTO toDto(Task task);

    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "assignedEmployeeId", target = "assignedEmployee.id")
    Task toEntity(TaskDTO taskDTO);
}
