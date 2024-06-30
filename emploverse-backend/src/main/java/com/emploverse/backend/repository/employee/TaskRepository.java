package com.emploverse.backend.repository.employee;

import com.emploverse.backend.model.employee.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
