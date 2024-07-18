package com.emploverse.backend.service;

import com.emploverse.backend.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

public interface EmployeeService {

    Optional<EmployeeDTO> findEmployeeById(Long id);

    Optional<EmployeeDTO> findEmployeeByUserId(Long userId);

    Page<EmployeeDTO> findEmployeesBySortPage(int page, int size, String sortBy, String sortDir);

    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployeeById(Long id, EmployeeDTO updatedEmployeeDTO);

    void deleteEmployeeById(Long id);

}
