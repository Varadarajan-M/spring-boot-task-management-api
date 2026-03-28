package com.learning.taskmanager.task_manager_rest_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskDto {
    private String title;
    private String description;
}
