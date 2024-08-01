package com.emploverse.backend.service;

import com.emploverse.backend.model.Department;
import com.emploverse.backend.model.DepartmentName;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department createDepartment(Department department);

    Optional<Department> getDepartmentById(Long id);

    Optional<Department> getDepartmentByName(DepartmentName name);

    List<Department> getAllDepartments();

    Department updateDepartment(Long id, Department department);

    void deleteDepartment(Long id);
}
