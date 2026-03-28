package com.learning.taskmanager.task_manager_rest_api.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDto {
    private String error;

    public ErrorResponseDto(String error) {
        this.error = error;
    }
}
