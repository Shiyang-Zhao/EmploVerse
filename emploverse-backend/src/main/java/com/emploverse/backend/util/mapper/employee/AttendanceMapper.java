package com.emploverse.backend.util.mapper.employee;

import com.emploverse.backend.dto.employee.AttendanceDTO;
import com.emploverse.backend.model.employee.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    AttendanceDTO toDto(Attendance attendance);

    @Mapping(source = "employeeId", target = "employee.id")
    Attendance toEntity(AttendanceDTO attendanceDTO);
}
