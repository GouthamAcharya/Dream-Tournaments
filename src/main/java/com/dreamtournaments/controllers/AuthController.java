package com.dreamtournaments.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamtournaments.models.ERole;
import com.dreamtournaments.models.Role;
import com.dreamtournaments.models.User;
import com.dreamtournaments.payload.JwtResponse;
import com.dreamtournaments.payload.LoginRequest;
import com.dreamtournaments.payload.MessageResponse;
import com.dreamtournaments.payload.SignupRequest;
import com.dreamtournaments.repository.IRoleRepository;
import com.dreamtournaments.repository.IUserRepository;
import com.dreamtournaments.security.jwt.JwtTokenUtils;
import com.dreamtournaments.services.UserDetailsImpl;


@RestController
@RequestMapping("/dream-tournaments/auth")
public class AuthController {

	@Autowired
	JwtTokenUtils jwtTokenUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserRepository iUserRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	IRoleRepository iRoleRepository;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
			
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

		if (iUserRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body("Error: Username is already taken!");
		}

		if (iUserRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()));

		Set<String> strRoles = signupRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {

			Role userRole = iRoleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(userRole);

		} else {

			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = iRoleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(adminRole);
					break;

				default:
					Role userRole = iRoleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(userRole);
				}

			});

		}

		user.setRoles(roles);
		iUserRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}

}
