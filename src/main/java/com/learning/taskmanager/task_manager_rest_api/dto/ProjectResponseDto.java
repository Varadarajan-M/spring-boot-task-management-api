package com.learning.taskmanager.task_manager_rest_api.dto;

import java.time.Instant;
import java.util.UUID;

import com.learning.taskmanager.task_manager_rest_api.entity.Project;
import com.learning.taskmanager.task_manager_rest_api.enums.ProjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDto {

    private UUID id;

    private String name;

    private String description;

    private ProjectStatus status;

    private Instant startDate;

    private Instant endDate;

    private Instant createdAt;

    private Instant updatedAt;

    public static ProjectResponseDto from(Project project) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStatus(project.getStatus());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        return dto;
    }

}
