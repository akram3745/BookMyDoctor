package com.te.bookmydoctor.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		try {
			response.setHeader("error", exception.getMessage());
			response.setStatus(HttpStatus.FORBIDDEN.value());
			HashMap<String, String> error = new HashMap<>();
			error.put("statusCode", HttpStatus.FORBIDDEN.toString());
			error.put("timestamp", LocalDateTime.now().toString());
			error.put("error", exception.getMessage());
			error.put("message", "Access Denied");
			log.error(exception.getMessage());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		} catch (JsonGenerationException exception2) {
			exception2.printStackTrace();
		}
	}


}
