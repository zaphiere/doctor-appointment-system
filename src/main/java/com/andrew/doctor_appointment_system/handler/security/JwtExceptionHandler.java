package com.andrew.doctor_appointment_system.handler.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class JwtExceptionHandler{
	
	@ExceptionHandler(ExpiredJwtException.class)
	public void handleExpiredJwtException(HttpServletResponse response, ExpiredJwtException ex) throws JsonProcessingException, IOException {
		
		handleJwtException(response, HttpStatus.UNAUTHORIZED, "token expired");
	}
	
	@ExceptionHandler(JwtException.class)
	public void handleJwtException(HttpServletResponse response, JwtException ex) throws JsonProcessingException, IOException {
		
		handleJwtException(response, HttpStatus.UNAUTHORIZED, "Invalid token");
	}


	private void handleJwtException(HttpServletResponse response, HttpStatus status, String message) 
			throws JsonProcessingException, IOException {
		
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		Map<String, Object> body = new HashMap<>();
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		
		response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(body));
	}
	
}
