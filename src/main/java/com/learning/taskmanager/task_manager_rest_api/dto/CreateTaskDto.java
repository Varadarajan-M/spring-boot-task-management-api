package com.learning.taskmanager.task_manager_rest_api.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskDto {

    @NotBlank(message = "Task title cannot be blank")
    @Size(max = 255, message = "Task title must be between 1 and 255 characters")
    private String title;

    private String description;
    private UUID projectId;
    private UUID assignedToId;
}
