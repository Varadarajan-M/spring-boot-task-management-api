package com.learning.taskmanager.task_manager_rest_api.dto;

import java.time.Instant;

import com.learning.taskmanager.task_manager_rest_api.enums.ProjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectDto {
    private String name;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private ProjectStatus status;

}
