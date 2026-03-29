package com.learning.taskmanager.task_manager_rest_api.dto;

import java.time.Instant;
import java.util.UUID;

import com.learning.taskmanager.task_manager_rest_api.entity.Task;
import com.learning.taskmanager.task_manager_rest_api.enums.TaskStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponseDto {

    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    private UserSummaryDto assignedTo;
    private ProjectSummaryDto project;

    public static TaskResponseDto from(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());

        if (task.getAssignedTo() != null) {
            dto.setAssignedTo(
                    UserSummaryDto.from(task.getAssignedTo()));

        }

        if (task.getProject() != null) {
            dto.setProject(
                    ProjectSummaryDto.from(task.getProject()));
        }
        return dto;
    }

}
