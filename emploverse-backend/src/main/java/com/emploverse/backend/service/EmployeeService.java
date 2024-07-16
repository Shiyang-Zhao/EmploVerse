package com.emploverse.backend.service;

import com.emploverse.backend.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<EmployeeDTO> findEmployeeById(Long id);

    Optional<EmployeeDTO> findEmployeeByUserId(Long userId);

    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployeeById(Long id, EmployeeDTO updatedEmployeeDTO);

    void deleteEmployeeById(Long id);

}
