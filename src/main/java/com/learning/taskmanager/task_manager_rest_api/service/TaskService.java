package com.learning.taskmanager.task_manager_rest_api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.learning.taskmanager.task_manager_rest_api.dto.CreateTaskDto;
import com.learning.taskmanager.task_manager_rest_api.dto.UpdateTaskDto;
import com.learning.taskmanager.task_manager_rest_api.entity.Task;
import com.learning.taskmanager.task_manager_rest_api.enums.TaskStatus;
import com.learning.taskmanager.task_manager_rest_api.exception.NotFoundException;
import com.learning.taskmanager.task_manager_rest_api.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(
            TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private void validateEmptyValueAndThrow(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    public Task saveTask(CreateTaskDto taskRequestDto) {
        Task newTask = new Task();
        TaskStatus defaultStatus = TaskStatus.TODO;

        String title = taskRequestDto.getTitle();

        validateEmptyValueAndThrow(title, "Task title");

        newTask.setTitle(title);
        newTask.setDescription(taskRequestDto.getDescription());
        newTask.setStatus(defaultStatus);

        return this.taskRepository.save(newTask);
    }

    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    public Task getTaskById(UUID taskId) {
        return this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with ID " + taskId + " not found"));
    }

    public Task updateTaskById(UUID taskId, UpdateTaskDto taskDto) {

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

        return this.taskRepository.save(existingTask);

    }

    public void deleteTaskById(UUID taskId) {

        boolean isTaskExisting = this.taskRepository.existsById(taskId);

        if (!isTaskExisting) {
            throw new NotFoundException("Task with ID " + taskId + " not found");
        }

        this.taskRepository.deleteById(taskId);

    }
}
