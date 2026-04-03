package com.learning.taskmanager.task_manager_rest_api.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.learning.taskmanager.task_manager_rest_api.dto.CreateTaskDto;
import com.learning.taskmanager.task_manager_rest_api.dto.TaskFiltersDto;
import com.learning.taskmanager.task_manager_rest_api.dto.TaskResponseDto;
import com.learning.taskmanager.task_manager_rest_api.dto.UpdateTaskDto;
import com.learning.taskmanager.task_manager_rest_api.entity.Task;
import com.learning.taskmanager.task_manager_rest_api.enums.TaskStatus;
import com.learning.taskmanager.task_manager_rest_api.exception.NotFoundException;
import com.learning.taskmanager.task_manager_rest_api.repository.TaskRepository;
import com.learning.taskmanager.task_manager_rest_api.specification.TaskSpecification;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public TaskService(
            TaskRepository taskRepository, UserService userService, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    public TaskResponseDto saveTask(CreateTaskDto taskRequestDto) {
        Task newTask = new Task();
        TaskStatus defaultStatus = TaskStatus.TODO;

        String title = taskRequestDto.getTitle();

        newTask.setTitle(title);
        newTask.setDescription(taskRequestDto.getDescription());
        newTask.setStatus(defaultStatus);

        if (taskRequestDto.getAssignedToId() != null) {
            newTask.setAssignedTo(userService.getUserEntityById(taskRequestDto.getAssignedToId()));
        }

        if (taskRequestDto.getProjectId() != null) {
            newTask.setProject(projectService.getProjectEntityById(taskRequestDto.getProjectId()));
        }

        return TaskResponseDto.from(this.taskRepository.save(newTask));
    }

    public Page<TaskResponseDto> getAllTasks(TaskFiltersDto filters, Pageable pageable) {

        TaskStatus statusFilter = filters.getStatus();

        UUID projectIdFilter = filters.getProjectId();
        UUID assignedToIdFilter = filters.getAssignedToId();

        Specification<Task> spec = Specification.unrestricted();

        if (assignedToIdFilter != null) {
            spec = spec.and(TaskSpecification.hasAssignedToId(assignedToIdFilter));
        }

        if (statusFilter != null) {
            spec = spec.and(TaskSpecification.hasStatus(statusFilter));
        }

        if (projectIdFilter != null) {
            spec = spec.and(TaskSpecification.hasProjectId(projectIdFilter));
        }

        return this.taskRepository.findAll(spec, pageable).map(TaskResponseDto::from);
    }

    public TaskResponseDto getTaskById(UUID taskId) {
        Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with ID " + taskId + " not found"));
        return TaskResponseDto.from(task);
    }

    public TaskResponseDto updateTaskById(UUID taskId, UpdateTaskDto taskDto) {

        Task existingTask = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with ID " + taskId + " not found"));

        String taskTitle = taskDto.getTitle();

        if (taskTitle != null && !taskTitle.isEmpty()) {
            existingTask.setTitle(taskTitle);
        }

        String taskDescription = taskDto.getDescription();

        if (taskDescription != null) {
            existingTask.setDescription(taskDescription);
        }

        TaskStatus taskStatus = taskDto.getStatus();

        if (taskStatus != null) {
            existingTask.setStatus(taskStatus);
        }

        if (taskDto.getAssignedToId() != null) {
            existingTask.setAssignedTo(userService.getUserEntityById(taskDto.getAssignedToId()));
        }

        if (taskDto.getProjectId() != null) {
            existingTask.setProject(projectService.getProjectEntityById(taskDto.getProjectId()));
        }

        return TaskResponseDto.from(this.taskRepository.save(existingTask));

    }

    public void deleteTaskById(UUID taskId) {

        boolean isTaskExisting = this.taskRepository.existsById(taskId);

        if (!isTaskExisting) {
            throw new NotFoundException("Task with ID " + taskId + " not found");
        }

        this.taskRepository.deleteById(taskId);

    }
}
