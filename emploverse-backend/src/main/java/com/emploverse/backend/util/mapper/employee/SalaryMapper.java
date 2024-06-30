package com.emploverse.backend.util.mapper.employee;

import com.emploverse.backend.dto.employee.SalaryDTO;
import com.emploverse.backend.model.employee.Salary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SalaryMapper {
    SalaryMapper INSTANCE = Mappers.getMapper(SalaryMapper.class);

    @Mapping(source = "employee.id", target = "employeeId")
    SalaryDTO toDto(Salary salary);

    @Mapping(source = "employeeId", target = "employee.id")
    Salary toEntity(SalaryDTO salaryDTO);
}
