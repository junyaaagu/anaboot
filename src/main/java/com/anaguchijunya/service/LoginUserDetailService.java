package com.anaguchijunya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anaguchijunya.domain.User;
import com.anaguchijunya.repository.UserRepository;

@Service
public class LoginUserDetailService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOne(username);
		if (user == null) {
			throw new UsernameNotFoundException("The requested user is no found.");
		}
		
		return new LoginUserDetails(user);
	}

}
