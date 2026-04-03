package com.learning.taskmanager.task_manager_rest_api.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.learning.taskmanager.task_manager_rest_api.entity.Task;
import com.learning.taskmanager.task_manager_rest_api.enums.TaskStatus;

public class TaskSpecification {

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, cb) -> {
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Task> hasProjectId(UUID projectId) {
        return (root, query, cb) -> {
            return cb.equal(root.get("project").get("id"), projectId);
        };
    }

    public static Specification<Task> hasAssignedToId(UUID assignedToId) {
        return (root, query, cb) -> {
            return cb.equal(root.get("assignedTo").get("id"), assignedToId);
        };
    }
}
