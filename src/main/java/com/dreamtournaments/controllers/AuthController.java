package com.dreamtournaments.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamtournaments.payload.JwtResponse;
import com.dreamtournaments.payload.LoginRequest;
import com.dreamtournaments.payload.SignupRequest;
import com.dreamtournaments.services.IAuthService;

@RestController
@RequestMapping("/dream-tournaments/auth")
public class AuthController {

	@Autowired
	IAuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "login user");
		JwtResponse jwtResponse = authService.signIn(loginRequest);
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(jwtResponse);

	}

	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("description", "user registration");
		authService.signUp(signupRequest);
		String message = "Registration successfull";
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(message);
	}

}
