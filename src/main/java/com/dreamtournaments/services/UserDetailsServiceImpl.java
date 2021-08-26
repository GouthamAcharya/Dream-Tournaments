package com.dreamtournaments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dreamtournaments.models.User;
import com.dreamtournaments.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

		return UserDetailsImpl.build(user);
	}

}
