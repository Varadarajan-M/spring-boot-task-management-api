package com.learning.taskmanager.task_manager_rest_api.controller;

import java.util.List;
import java.util.UUID;

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
import com.learning.taskmanager.task_manager_rest_api.dto.UpdateTaskDto;
import com.learning.taskmanager.task_manager_rest_api.entity.Task;
import com.learning.taskmanager.task_manager_rest_api.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(
            TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.taskService.getAllTasks();

        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskDto task) {
        Task savedTask = this.taskService.saveTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Task task = this.taskService.getTaskById(id);

        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTaskById(@PathVariable UUID id, @RequestBody UpdateTaskDto taskDto) {
        Task updatedTask = this.taskService.updateTaskById(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable UUID id) {
        this.taskService.deleteTaskById(id);

        return ResponseEntity.noContent().build();

    }

}
