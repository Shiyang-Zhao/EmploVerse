package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.ProjectDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<ProjectDTO> getAllProjects();

    Optional<ProjectDTO> getProjectById(Long id);

    ProjectDTO saveProject(ProjectDTO projectDTO);

    ProjectDTO updateProject(Long id, ProjectDTO projectDTO);

    void deleteProject(Long id);
}
