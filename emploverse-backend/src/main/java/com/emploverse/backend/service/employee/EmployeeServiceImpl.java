package com.emploverse.backend.service.employee;

import com.emploverse.backend.model.department.Department;
import com.emploverse.backend.model.employee.Employee;
import com.emploverse.backend.dto.employee.EmployeeDTO;
import com.emploverse.backend.repository.department.DepartmentRepository;
import com.emploverse.backend.repository.employee.EmployeeRepository;
import com.emploverse.backend.util.mapper.employee.EmployeeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findEmployeeById(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::employeeToEmployeeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findEmployeeByEmail(String email) {
        return Optional.ofNullable(employeeRepository.findByEmail(email)).map(employeeMapper::employeeToEmployeeDTO);
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
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeDTO(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id).map(employee -> {
            // Update simple fields
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhoneNumber(employeeDTO.getPhoneNumber());
            employee.setPosition(employeeDTO.getPosition());
            employee.setDateOfBirth(employeeDTO.getDateOfBirth());
            employee.setDateOfJoining(employeeDTO.getDateOfJoining());
            employee.setAddress(employeeDTO.getAddress());

            if (employeeDTO.getDepartmentId() != null) {
                Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Department not found with ID: " + employeeDTO.getDepartmentId()));
                employee.setDepartment(department);
            } else {
                employee.setDepartment(null);
            }

            if (employeeDTO.getManagerId() != null) {
                Employee manager = employeeRepository.findById(employeeDTO.getManagerId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Manager not found with ID: " + employeeDTO.getManagerId()));
                employee.setManager(manager);
            } else {
                employee.setManager(null);
            }

            Employee updatedEmployee = employeeRepository.save(employee);
            return employeeMapper.employeeToEmployeeDTO(updatedEmployee);
        }).orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position).stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }
}
