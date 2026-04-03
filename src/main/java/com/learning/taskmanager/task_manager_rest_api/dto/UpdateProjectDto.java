package com.learning.taskmanager.task_manager_rest_api.dto;

import java.time.Instant;

import com.learning.taskmanager.task_manager_rest_api.enums.ProjectStatus;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectDto {

    @Size(min = 1, max = 255, message = "Project name must be between 1 and 255 characters")
    private String name;

    private String description;
    private Instant startDate;
    private Instant endDate;
    private ProjectStatus status;

}
