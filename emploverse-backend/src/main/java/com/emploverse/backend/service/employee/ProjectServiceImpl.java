package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.ProjectDTO;
import com.emploverse.backend.model.employee.Project;
import com.emploverse.backend.model.employee.ProjectStatus;
import com.emploverse.backend.repository.employee.EmployeeRepository;
import com.emploverse.backend.repository.employee.ProjectRepository;
import com.emploverse.backend.util.mapper.employee.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
            EmployeeRepository employeeRepository,
            ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> getProjectById(Long id) {
        return projectRepository.findById(id).map(projectMapper::toDto);
    }

    @Override
    @Transactional
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        // Convert status from String to ProjectStatus enum
        project.setStatus(ProjectStatus.valueOf(projectDTO.getStatus()));
        project.setManager(employeeRepository.findById(projectDTO.getManagerId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Manager not found with ID: " + projectDTO.getManagerId())));
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    @Override
    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        return projectRepository.findById(id).map(project -> {
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            // Convert status from String to ProjectStatus enum
            project.setStatus(ProjectStatus.valueOf(projectDTO.getStatus()));
            project.setStartDate(projectDTO.getStartDate());
            project.setEndDate(projectDTO.getEndDate());
            project.setManager(employeeRepository.findById(projectDTO.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Manager not found with ID: " + projectDTO.getManagerId())));
            Project updatedProject = projectRepository.save(project);
            return projectMapper.toDto(updatedProject);
        }).orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
