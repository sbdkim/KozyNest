package com.ezen.view.api;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ezen.view.api")
public class ApiExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex, HttpServletRequest request) {
		return new ResponseEntity<Map<String, Object>>(errorBody("INVALID_REQUEST", ex.getMessage(), request),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleUnexpected(Exception ex, HttpServletRequest request) {
		return new ResponseEntity<Map<String, Object>>(
				errorBody("UNEXPECTED_ERROR", "Unexpected server error", request), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Map<String, Object> errorBody(String code, String message, HttpServletRequest request) {
		Map<String, Object> error = new LinkedHashMap<String, Object>();
		error.put("code", code);
		error.put("message", message);
		error.put("path", request.getRequestURI());
		error.put("requestId", MDC.get("requestId"));
		return error;
	}
}
