package com.learning.taskmanager.task_manager_rest_api.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectDto {

    @NotBlank(message = "Project name cannot be blank")
    @Size(max = 255, message = "Project name must be between 1 and 255 characters")
    private String name;
    private String description;
    private Instant startDate;
    private Instant endDate;

}