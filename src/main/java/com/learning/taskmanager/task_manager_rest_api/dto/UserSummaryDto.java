package com.learning.taskmanager.task_manager_rest_api.dto;

import java.util.UUID;

import com.learning.taskmanager.task_manager_rest_api.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummaryDto {
    private UUID id;
    private String name;
    private String email;

    public static UserSummaryDto from(User user) {
        UserSummaryDto dto = new UserSummaryDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
