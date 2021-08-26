package com.dreamtournaments.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dreamtournaments.exception.AuthException;
import com.dreamtournaments.models.ERole;
import com.dreamtournaments.models.Role;
import com.dreamtournaments.models.User;
import com.dreamtournaments.payload.JwtResponse;
import com.dreamtournaments.payload.LoginRequest;
import com.dreamtournaments.payload.SignupRequest;
import com.dreamtournaments.repository.IRoleRepository;
import com.dreamtournaments.repository.IUserRepository;
import com.dreamtournaments.security.jwt.JwtTokenUtils;

@Service
public class IAuthServiceImpl implements IAuthService {

	@Autowired
	JwtTokenUtils jwtTokenUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	IRoleRepository roleRepository;

	@Override
	public void signUp(SignupRequest signupRequest) {

		if (userRepository.existsByEmail(signupRequest.getEmail())) {

			throw new AuthException("Error: Email is already in use!");
		}

		// Create new user's account
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()));

		Set<String> strRoles = signupRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {

			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(userRole);

		} else {

			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(adminRole);
					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(userRole);
				}

			});

		}

		user.setRoles(roles);
		userRepository.save(user);

	}

	@Override
	public JwtResponse signIn(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);

	}

}
