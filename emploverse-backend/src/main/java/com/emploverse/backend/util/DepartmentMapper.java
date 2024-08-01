package com.emploverse.backend.util;

import com.emploverse.backend.model.Department;
import com.emploverse.backend.model.DepartmentName;
import com.emploverse.backend.repository.DepartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DepartmentMapper {

    @Autowired
    protected DepartmentRepository departmentRepository;

    public static final DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    @Named("departmentToDepartmentName")
    public DepartmentName departmentToDepartmentName(Department department) {
        return department != null ? department.getName() : null;
    }

    @Named("departmentNameToDepartment")
    public Department departmentNameToDepartment(DepartmentName departmentName) {
        if (departmentName == null) {
            return null;
        }
        return departmentRepository.findByName(departmentName).orElse(null);
    }
}
