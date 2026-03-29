package com.learning.taskmanager.task_manager_rest_api.dto;

import java.time.Instant;
import java.util.UUID;

import com.learning.taskmanager.task_manager_rest_api.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private UUID id;

    private String name;

    private String email;

    private Instant createdAt;

    private Instant updatedAt;

    public static UserResponseDto from(
            User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
