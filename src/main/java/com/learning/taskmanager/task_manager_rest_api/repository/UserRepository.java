package com.learning.taskmanager.task_manager_rest_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.taskmanager.task_manager_rest_api.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
