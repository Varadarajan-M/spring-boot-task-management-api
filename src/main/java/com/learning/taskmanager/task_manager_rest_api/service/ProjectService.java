package com.learning.taskmanager.task_manager_rest_api.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.learning.taskmanager.task_manager_rest_api.dto.CreateProjectDto;
import com.learning.taskmanager.task_manager_rest_api.dto.ProjectResponseDto;
import com.learning.taskmanager.task_manager_rest_api.dto.UpdateProjectDto;
import com.learning.taskmanager.task_manager_rest_api.entity.Project;
import com.learning.taskmanager.task_manager_rest_api.enums.ProjectStatus;
import com.learning.taskmanager.task_manager_rest_api.exception.NotFoundException;
import com.learning.taskmanager.task_manager_rest_api.repository.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(
            ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectResponseDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream().map(ProjectResponseDto::from).toList();
    }

    public ProjectResponseDto createProject(CreateProjectDto projectDto) {

        String name = projectDto.getName();
        Instant startDate = projectDto.getStartDate();
        Instant endDate = projectDto.getEndDate();

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        Project project = new Project();

        project.setName(name);
        project.setDescription(projectDto.getDescription());
        project.setStatus(ProjectStatus.PLANNING);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        return ProjectResponseDto.from(projectRepository.save(project));
    }

    // For internal use by other services - returns entity
    public Project getProjectEntityById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with ID " + projectId + " not found."));
    }

    // For controller use - returns DTO
    public ProjectResponseDto getProjectById(UUID projectId) {
        return ProjectResponseDto.from(getProjectEntityById(projectId));
    }

    public ProjectResponseDto updateProject(UUID projectId, UpdateProjectDto projectDto) {
        Project existingProject = getProjectEntityById(projectId);

        Instant startDate = projectDto.getStartDate() != null ? projectDto.getStartDate()
                : existingProject.getStartDate();
        Instant endDate = projectDto.getEndDate() != null ? projectDto.getEndDate()
                : existingProject.getEndDate();

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        String name = projectDto.getName();
        String description = projectDto.getDescription();
        ProjectStatus status = projectDto.getStatus();

        if (name != null && !name.isEmpty()) {
            existingProject.setName(name);
        }

        if (description != null) {
            existingProject.setDescription(description);
        }

        if (projectDto.getStartDate() != null) {
            existingProject.setStartDate(startDate);
        }

        if (projectDto.getEndDate() != null) {
            existingProject.setEndDate(endDate);
        }

        if (projectDto.getStatus() != null) {
            existingProject.setStatus(status);
        }

        return ProjectResponseDto.from(projectRepository.save(existingProject));
    }

    public void deleteProject(UUID projectId) {

        this.getProjectById(projectId);

        projectRepository.deleteById(projectId);
    }

}
