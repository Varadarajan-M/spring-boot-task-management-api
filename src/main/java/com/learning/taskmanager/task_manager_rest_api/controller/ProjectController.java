package com.learning.taskmanager.task_manager_rest_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.taskmanager.task_manager_rest_api.dto.CreateProjectDto;
import com.learning.taskmanager.task_manager_rest_api.dto.ProjectResponseDto;
import com.learning.taskmanager.task_manager_rest_api.dto.UpdateProjectDto;
import com.learning.taskmanager.task_manager_rest_api.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(
            ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> projects = projectService.getAllProjects();

        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody CreateProjectDto projectDto) {
        ProjectResponseDto project = projectService.createProject(projectDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable UUID id) {
        ProjectResponseDto project = projectService.getProjectById(id);

        return ResponseEntity.ok(project);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable UUID id,
            @Valid @RequestBody UpdateProjectDto projectDto) {

        ProjectResponseDto updatedProject = projectService.updateProject(id, projectDto);

        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }

}
