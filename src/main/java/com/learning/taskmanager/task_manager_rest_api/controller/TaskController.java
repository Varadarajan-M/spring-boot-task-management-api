package com.learning.taskmanager.task_manager_rest_api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.taskmanager.task_manager_rest_api.dto.CreateTaskDto;
import com.learning.taskmanager.task_manager_rest_api.dto.TaskFiltersDto;
import com.learning.taskmanager.task_manager_rest_api.dto.TaskResponseDto;
import com.learning.taskmanager.task_manager_rest_api.dto.UpdateTaskDto;
import com.learning.taskmanager.task_manager_rest_api.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(
            TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getAllTasks(TaskFiltersDto filters,
            @PageableDefault(size = 20, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TaskResponseDto> tasks = this.taskService.getAllTasks(filters, pageable);

        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody CreateTaskDto task) {
        TaskResponseDto savedTask = this.taskService.saveTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID id) {
        TaskResponseDto task = this.taskService.getTaskById(id);

        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTaskById(@PathVariable UUID id,
            @Valid @RequestBody UpdateTaskDto taskDto) {
        TaskResponseDto updatedTask = this.taskService.updateTaskById(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable UUID id) {
        this.taskService.deleteTaskById(id);

        return ResponseEntity.noContent().build();

    }

}
