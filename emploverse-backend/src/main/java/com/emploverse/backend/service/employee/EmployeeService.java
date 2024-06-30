package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDTO> findEmployeeById(Long id);

    Optional<EmployeeDTO> findEmployeeByEmail(String email);

    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

    List<EmployeeDTO> findEmployeesByDepartmentId(Long departmentId);

    List<EmployeeDTO> findEmployeesByPosition(String position);
}
