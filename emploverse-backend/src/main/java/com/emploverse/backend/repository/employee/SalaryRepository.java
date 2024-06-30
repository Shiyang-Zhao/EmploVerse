package com.emploverse.backend.repository.employee;

import com.emploverse.backend.model.employee.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
