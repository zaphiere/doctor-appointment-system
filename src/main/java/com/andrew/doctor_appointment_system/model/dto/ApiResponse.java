package com.andrew.doctor_appointment_system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	
	private int status;
	private String error;
	private String message;
	private Object data;

	public ApiResponse(int status, String error, String message, Object data) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}


	public String getError() {
		return error;
	}


	public String getMessage() {
		return message;
	}


	public Object getData() {
		return data;
	}
}
