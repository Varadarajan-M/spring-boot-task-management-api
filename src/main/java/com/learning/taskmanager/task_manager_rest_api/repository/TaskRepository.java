package com.learning.taskmanager.task_manager_rest_api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.learning.taskmanager.task_manager_rest_api.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {

    @EntityGraph(attributePaths = { "assignedTo", "project" })
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);
}
