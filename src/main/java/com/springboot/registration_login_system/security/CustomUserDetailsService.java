package com.springboot.registration_login_system.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.registration_login_system.entity.User;
import com.springboot.registration_login_system.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(usernameOrEmail);

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
							.collect(Collectors.toList()));
		} else {
			throw new UsernameNotFoundException("Invalid Username or Password!");
		}

	}
}
