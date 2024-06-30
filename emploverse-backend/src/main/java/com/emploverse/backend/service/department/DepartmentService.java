package com.emploverse.backend.service.department;

import com.emploverse.backend.model.department.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Optional<Department> getDepartmentById(Long id);

    Department saveDepartment(Department department);

    void deleteDepartment(Long id);
}
