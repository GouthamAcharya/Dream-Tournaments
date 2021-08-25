package com.dreamtournaments.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dreamtournaments.models.ApiErrors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		headers.add("description", "invalid field");

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		ApiErrors apiError = new ApiErrors(LocalDateTime.now(), message, status, errors);
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String message = ex.getMessage();
		headers.add("description", "Http method not supported");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status, null);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String message = ex.getMessage();
		headers.add("description", "Media type not supported");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status, null);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		headers.add("description", "Path variable is missing");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status, null);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		headers.add("description", "Invalid data type");
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, status, null);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	@ExceptionHandler(TournamentNotFoundException.class)
	public ResponseEntity<Object> handleTournamentNotFound(TournamentNotFoundException ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "Tournament not found");
		String message = ex.getMessage();
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, HttpStatus.BAD_REQUEST, null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errors);

	}

	@ExceptionHandler(SportsClubNotFoundException.class)
	public ResponseEntity<Object> handleSportsClubNotFound(SportsClubNotFoundException ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "sports club not found");
		String message = ex.getMessage();
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, HttpStatus.BAD_REQUEST, null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errors);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "Something went wrong.. please try again");
		String message = ex.getMessage();
		ApiErrors errors = new ApiErrors(LocalDateTime.now(), message, HttpStatus.EXPECTATION_FAILED, null);
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).headers(headers).body(errors);

	}
}
