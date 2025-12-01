package com.andrew.doctor_appointment_system.util;

import org.springframework.http.HttpStatus;

import com.andrew.doctor_appointment_system.model.dto.ApiResponse;

public class ApiResponseUtil {
	
	// 200 OK
	public static ApiResponse ok(String message, Object data) {
		return new ApiResponse(
				HttpStatus.OK.value(),
				null,
				message,
				data
			);
	}
	
	// 201 CREATED
	public static ApiResponse created(String message, Object data) {
		return new ApiResponse(
				HttpStatus.CREATED.value(),
				null,
				message,
				data
			);
	}
	
	// 401 UNAUTHORIZED
	public static ApiResponse unauthorized(String message) {
		return new ApiResponse(
				HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase(),
				message,
				null
			);
	}
	
	// 403 FORBIDDEN
	public static ApiResponse forbidden(String message) {
		return new ApiResponse(
				HttpStatus.FORBIDDEN.value(),
				HttpStatus.FORBIDDEN.getReasonPhrase(),
				message,
				null
			);
	}
	
	// 404 NOT FOUND
	public static ApiResponse notFount(String message) {
		return new ApiResponse(
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(),
				message,
				null
			);
	}
	
	// 400 BAD REQUEST
	public static ApiResponse badRequest(String message) {
		return new ApiResponse(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				message,
				null
			);
	}
	
	// 500 INTERNAL SERVER ERROR
	public static ApiResponse internalError(String message) {
		return new ApiResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				message,
				null
			);
	}
}
