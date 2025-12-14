package com.csp.blogapp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.csp.blogapp.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<>(ApiResponse.builder().message(ex.getMessage()).success(false)
				.status(HttpStatus.NOT_FOUND.value()).build(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException ex) {
		ApiResponse response = ApiResponse.builder().message("Invalid username or password").success(false)
				.status(HttpStatus.UNAUTHORIZED.value()).build();
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiResponse> handleUserNotFound(UsernameNotFoundException ex) {
		ApiResponse response = ApiResponse.builder().message(ex.getMessage()).success(false)
				.status(HttpStatus.NOT_FOUND.value()).build();
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> handleBadRequest(HttpMessageNotReadableException ex) {
		return new ResponseEntity<>(ApiResponse.builder().message("Invalid JSON input: " + ex.getMessage())
				.success(false).status(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
		return new ResponseEntity<>(ApiResponse.builder().message("Something went wrong: " + ex.getMessage())
				.success(false).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
