package com.emploverse.backend.repository.employee;

import com.emploverse.backend.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);

    // Find employees by department
    List<Employee> findByDepartmentId(Long departmentId);

    // Find employees by position
    List<Employee> findByPosition(String position);

    // Paginate and sort the list of employees by department
    Page<Employee> findAllByDepartment(String department, Pageable pageable);
}
