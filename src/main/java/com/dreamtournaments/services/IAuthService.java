package com.dreamtournaments.services;

import com.dreamtournaments.payload.JwtResponse;
import com.dreamtournaments.payload.LoginRequest;
import com.dreamtournaments.payload.SignupRequest;

public interface IAuthService {
	
	void signUp(SignupRequest signupRequest);
	JwtResponse signIn(LoginRequest loginRequest);

}
