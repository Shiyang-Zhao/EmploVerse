package com.emploverse.backend.repository;

import com.emploverse.backend.model.Department;
import com.emploverse.backend.model.DepartmentName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(DepartmentName name);

    boolean existsByName(DepartmentName name);
}
