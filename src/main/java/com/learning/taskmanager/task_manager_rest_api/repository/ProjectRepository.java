package com.learning.taskmanager.task_manager_rest_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.taskmanager.task_manager_rest_api.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

}
