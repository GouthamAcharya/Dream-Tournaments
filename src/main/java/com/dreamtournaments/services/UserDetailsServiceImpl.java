package com.dreamtournaments.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dreamtournaments.models.User;
import com.dreamtournaments.repository.IUserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private static final Logger logger=LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	IUserRepository iUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.debug("in UserDetailsServiceImpl loadUserByUsername(String username)");
		User user=iUserRepository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("User not found with username: "+username));
		
		return UserDetailsImpl.build(user);
	}

}
