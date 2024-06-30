package com.emploverse.backend.util.mapper.department;

import com.emploverse.backend.dto.department.DepartmentDTO;
import com.emploverse.backend.model.department.Department;
import com.emploverse.backend.model.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    @Mapping(source = "employees", target = "employeeIds", qualifiedByName = "employeesToEmployeeIds")
    DepartmentDTO departmentToDepartmentDTO(Department department);

    @Mapping(source = "employeeIds", target = "employees", qualifiedByName = "employeeIdsToEmployees")
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);

    @Named("employeesToEmployeeIds")
    default Set<Long> mapEmployeesToEmployeeIds(Set<Employee> employees) {
        return employees.stream().map(Employee::getId).collect(Collectors.toSet());
    }

    @Named("employeeIdsToEmployees")
    default Set<Employee> mapEmployeeIdsToEmployees(Set<Long> employeeIds) {
        return employeeIds.stream().map(id -> {
            Employee employee = new Employee();
            employee.setId(id);
            return employee;
        }).collect(Collectors.toSet());
    }
}
