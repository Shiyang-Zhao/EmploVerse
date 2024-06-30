package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.SalaryDTO;
import com.emploverse.backend.util.mapper.employee.SalaryMapper;
import com.emploverse.backend.model.employee.Employee;
import com.emploverse.backend.model.employee.Salary;
import com.emploverse.backend.repository.employee.EmployeeRepository;
import com.emploverse.backend.repository.employee.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final SalaryMapper salaryMapper;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.salaryMapper = SalaryMapper.INSTANCE;
    }

    @Override
    public SalaryDTO getSalaryById(Long id) {
        Salary salary = salaryRepository.findById(id).orElseThrow(() -> new RuntimeException("Salary not found"));
        return salaryMapper.toDto(salary);
    }

    @Override
    public SalaryDTO saveSalary(SalaryDTO salaryDTO) {
        Salary salary = salaryMapper.toEntity(salaryDTO);
        Salary savedSalary = salaryRepository.save(salary);
        return salaryMapper.toDto(savedSalary);
    }

    @Override
    public SalaryDTO updateSalary(Long id, SalaryDTO salaryDTO) {
        Salary existingSalary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));

        existingSalary.setAmount(salaryDTO.getAmount());
        existingSalary.setPaymentDate(salaryDTO.getPaymentDate());
        existingSalary.setRemarks(salaryDTO.getRemarks());
        existingSalary.setCustomAttributes(salaryDTO.getCustomAttributes());

        if (salaryDTO.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(salaryDTO.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            existingSalary.setEmployee(employee);
        }

        Salary updatedSalary = salaryRepository.save(existingSalary);
        return salaryMapper.toDto(updatedSalary);
    }

    @Override
    public void deleteSalary(Long id) {
        Salary salary = salaryRepository.findById(id).orElseThrow(() -> new RuntimeException("Salary not found"));
        salaryRepository.delete(salary);
    }

    @Override
    public List<SalaryDTO> getAllSalaries() {
        List<Salary> salaries = salaryRepository.findAll();
        return salaries.stream().map(salaryMapper::toDto).collect(Collectors.toList());
    }
}
