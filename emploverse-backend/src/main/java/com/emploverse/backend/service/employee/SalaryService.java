package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.SalaryDTO;
import java.util.List;

public interface SalaryService {
    SalaryDTO getSalaryById(Long id);

    SalaryDTO saveSalary(SalaryDTO salaryDTO);

    SalaryDTO updateSalary(Long id, SalaryDTO salaryDTO);

    void deleteSalary(Long id);

    List<SalaryDTO> getAllSalaries();
}
