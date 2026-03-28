package com.learning.taskmanager.task_manager_rest_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learning.taskmanager.task_manager_rest_api.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle 400 exceptions globally
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgsException(IllegalArgumentException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                errorResponse);
    }

    // Handle 404 exceptions globally
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                errorResponse);
    }

    // Handle 405 exceptions globally
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                errorResponse);
    }

    // Handle 400 exceptions globally for invalid JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                errorResponse);
    }
}
