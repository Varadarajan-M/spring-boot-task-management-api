package com.learning.taskmanager.task_manager_rest_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {

    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @Email(message = "Invalid email format")
    private String email;
}
