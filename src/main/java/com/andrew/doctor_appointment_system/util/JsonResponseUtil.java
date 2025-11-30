package com.andrew.doctor_appointment_system.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class JsonResponseUtil {
	
	   private static final ObjectMapper mapper = new ObjectMapper();
	
	   public static void writeResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
	        response.setStatus(status.value());
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	
	        Map<String, Object> body = new HashMap<>();
	        body.put("status", status.value());
		    body.put("error", status.getReasonPhrase());
		    body.put("message", message);
	
	    response.getWriter().write(mapper.writeValueAsString(body));
	}
}
