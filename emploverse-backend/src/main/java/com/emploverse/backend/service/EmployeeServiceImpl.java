package com.emploverse.backend.service;

import com.emploverse.backend.dto.EmployeeDTO;
import com.emploverse.backend.model.Employee;
import com.emploverse.backend.repository.EmployeeRepository;
import com.emploverse.backend.util.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::employeeToEmployeeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findEmployeeByUserId(Long userId) {
        return employeeRepository.findByUserId(userId)
                .map(employeeMapper::employeeToEmployeeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findEmployeesBySortPage(int page, int size, String sortBy, String sortDir) {
        int adjustedPage = page - 1 < 0 ? 0 : page - 1;
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(adjustedPage, size, sort);
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::employeeToEmployeeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeDTO(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO updatedEmployeeDTO) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(updatedEmployeeDTO.getFirstName());
            employee.setLastName(updatedEmployeeDTO.getLastName());
            employee.setProfileImagePath(updatedEmployeeDTO.getProfileImagePath());
            Employee updatedEmployee = employeeRepository.save(employee);
            return employeeMapper.employeeToEmployeeDTO(updatedEmployee);
        }).orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
    }

}
