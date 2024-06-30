package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.TaskDTO;
import com.emploverse.backend.model.employee.Task;
import com.emploverse.backend.model.employee.TaskStatus;
import com.emploverse.backend.repository.employee.TaskRepository;
import com.emploverse.backend.repository.employee.EmployeeRepository;
import com.emploverse.backend.repository.employee.ProjectRepository;
import com.emploverse.backend.util.mapper.employee.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(taskMapper::toDto);
    }

    @Override
    @Transactional
    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        // Assuming you have logic to set the employee and project in the entity
        task.setAssignedEmployee(employeeRepository.findById(taskDTO.getAssignedEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + taskDTO.getAssignedEmployeeId())));
        if (taskDTO.getProjectId() != null) {
            task.setProject(projectRepository.findById(taskDTO.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Project not found with ID: " + taskDTO.getProjectId())));
        }
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
            task.setStartDate(taskDTO.getStartDate());
            task.setEndDate(taskDTO.getEndDate());
            task.setDeadline(taskDTO.getDeadline());
            task.setAssignedEmployee(employeeRepository.findById(taskDTO.getAssignedEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Employee not found with ID: " + taskDTO.getAssignedEmployeeId())));
            if (taskDTO.getProjectId() != null) {
                task.setProject(projectRepository.findById(taskDTO.getProjectId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Project not found with ID: " + taskDTO.getProjectId())));
            } else {
                task.setProject(null);
            }
            Task updatedTask = taskRepository.save(task);
            return taskMapper.toDto(updatedTask);
        }).orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
