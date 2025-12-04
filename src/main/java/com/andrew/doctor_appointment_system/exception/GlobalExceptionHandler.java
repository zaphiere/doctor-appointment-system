package com.andrew.doctor_appointment_system.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * Handle validation errors from @Valid DTOs
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		ApiResponse response = ApiResponseUtil.validationFailed(errors);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle Illegal Arguments Exceptions
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
	
		ApiResponse response = ApiResponseUtil.badRequest(ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Handle Not Found Exceptions
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
		
		ApiResponse response = ApiResponseUtil.notFount(ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Catch All Handler
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleAllException(Exception ex, WebRequest request) {
		
		ex.printStackTrace();

		Map<String, Object> details = Map.of(
	        "path", request.getDescription(false).replace("uri=", ""),
	        "errorMessage", ex.getMessage()
	    );

	    ApiResponse response = ApiResponseUtil.internalError(details);

	    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
