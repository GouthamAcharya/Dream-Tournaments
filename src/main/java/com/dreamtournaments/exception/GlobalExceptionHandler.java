package com.dreamtournaments.exception;

import java.time.LocalDateTime;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dreamtournaments.models.ApiErrors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String message = ex.getMessage();
		headers.add("description", "Http method not supported");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		String message = ex.getMessage();
		headers.add("description", "Media type not supported");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		headers.add("description", "Path variable is missing");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		headers.add("description", "Invalid data type");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@ExceptionHandler(TournamentNotFoundException.class)
	public ResponseEntity<Object> handleTournamentNotFound(TournamentNotFoundException ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "Tournament not found");
		String message = ex.getMessage();
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errors);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "Something went wrong.. please try again");
		String message = ex.getMessage();
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, HttpStatus.EXPECTATION_FAILED);
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).headers(headers).body(errors);

	}
}
