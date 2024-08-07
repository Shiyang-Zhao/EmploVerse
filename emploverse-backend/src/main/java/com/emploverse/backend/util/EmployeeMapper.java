package com.emploverse.backend.util;

import com.emploverse.backend.dto.EmployeeDTO;
import com.emploverse.backend.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "departmentId", target = "department.id")
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
