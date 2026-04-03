package com.learning.taskmanager.task_manager_rest_api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.learning.taskmanager.task_manager_rest_api.dto.CreateUserDto;
import com.learning.taskmanager.task_manager_rest_api.dto.UpdateUserDto;
import com.learning.taskmanager.task_manager_rest_api.dto.UserResponseDto;
import com.learning.taskmanager.task_manager_rest_api.entity.User;
import com.learning.taskmanager.task_manager_rest_api.exception.NotFoundException;
import com.learning.taskmanager.task_manager_rest_api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(CreateUserDto userDto) {
        User user = new User();

        String email = userDto.getEmail();

        user.setName(userDto.getName());
        user.setEmail(email);

        return UserResponseDto.from(userRepository.save(user));
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::from).toList();
    }

    public User getUserEntityById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
    }

    public UserResponseDto getUserById(UUID userId) {
        User user = getUserEntityById(userId);
        return UserResponseDto.from(user);
    }

    // Use UpdateUserDto instead of CreateUserDto when introducing new fields that
    // are not required during user creation.
    public UserResponseDto updateUserById(UUID userId, UpdateUserDto userDto) {
        User existingUser = getUserEntityById(userId);

        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

        return UserResponseDto.from(userRepository.save(existingUser));
    }

    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

}
