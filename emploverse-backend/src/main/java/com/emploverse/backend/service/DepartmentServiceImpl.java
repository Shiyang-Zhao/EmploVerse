package com.emploverse.backend.service;

import com.emploverse.backend.model.Department;
import com.emploverse.backend.model.DepartmentName;
import com.emploverse.backend.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new RuntimeException("Department with name " + department.getName() + " already exists.");
        }
        return departmentRepository.save(department);
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Optional<Department> getDepartmentByName(DepartmentName name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        return departmentRepository.findById(id)
                .map(existingDepartment -> {
                    existingDepartment.setName(department.getName());
                    // Update other fields as necessary
                    return departmentRepository.save(existingDepartment);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
        departmentRepository.delete(department);
    }
}
