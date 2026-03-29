package com.learning.taskmanager.task_manager_rest_api.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectDto {
    private String name;
    private String description;
    private Instant startDate;
    private Instant endDate;

}
