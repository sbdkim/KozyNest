package com.ezen.view;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleNotFound(NoHandlerFoundException ex, HttpServletRequest request, Model model) {
		logger.warn("No handler found. uri={}, method={}", request.getRequestURI(), request.getMethod());
		return buildErrorView(model, request, "PAGE_NOT_FOUND",
				"The requested page does not exist. Please check the URL and try again.");
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, IllegalArgumentException.class })
	public String handleBadRequest(Exception ex, HttpServletRequest request, Model model) {
		logger.warn("Bad request. uri={}, method={}, error={}", request.getRequestURI(), request.getMethod(),
				ex.getMessage());
		return buildErrorView(model, request, "INVALID_REQUEST",
				"Some required request data is missing or invalid. Please check and retry.");
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleUploadException(MaxUploadSizeExceededException ex, HttpServletRequest request, Model model) {
		logger.warn("Upload size exceeded. uri={}, method={}", request.getRequestURI(), request.getMethod());
		return buildErrorView(model, request, "UPLOAD_LIMIT_EXCEEDED",
				"The uploaded file is too large. Please upload a smaller file.");
	}

	@ExceptionHandler(Exception.class)
	public String handleUnexpectedException(Exception ex, HttpServletRequest request, Model model) {
		logger.error("Unhandled exception. uri={}, method={}", request.getRequestURI(), request.getMethod(), ex);
		return buildErrorView(model, request, "UNEXPECTED_ERROR",
				"An unexpected error occurred. Please try again in a moment.");
	}

	private String buildErrorView(Model model, HttpServletRequest request, String code, String message) {
		model.addAttribute("errorCode", code);
		model.addAttribute("errorMessage", message);
		model.addAttribute("requestPath", request.getRequestURI());
		return "error/error";
	}
}
