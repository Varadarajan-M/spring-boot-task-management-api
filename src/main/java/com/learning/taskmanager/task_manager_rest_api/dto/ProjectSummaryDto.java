package com.learning.taskmanager.task_manager_rest_api.dto;

import java.util.UUID;

import com.learning.taskmanager.task_manager_rest_api.entity.Project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSummaryDto {

    private UUID id;
    private String name;

    public static ProjectSummaryDto from(Project project) {
        ProjectSummaryDto dto = new ProjectSummaryDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        return dto;
    }
}
