package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> getAllTasks();

    Optional<TaskDTO> getTaskById(Long id);

    TaskDTO saveTask(TaskDTO taskDTO);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    void deleteTask(Long id);
}
