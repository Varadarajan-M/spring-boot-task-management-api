package com.learning.taskmanager.task_manager_rest_api.dto;

import java.util.UUID;

import com.learning.taskmanager.task_manager_rest_api.enums.TaskStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskDto {
    private String title;
    private String description;
    private TaskStatus status;
    private UUID assignedToId;
    private UUID projectId;
}