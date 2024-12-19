package com.springboot.registration_login_system.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.registration_login_system.dto.UserDto;
import com.springboot.registration_login_system.entity.Role;
import com.springboot.registration_login_system.entity.User;
import com.springboot.registration_login_system.repository.RoleRepository;
import com.springboot.registration_login_system.repository.UserRepository;
import com.springboot.registration_login_system.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());

		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Role role = roleRepository.findByName("ROLE_ADMIN");
		if (role == null) {
			role = checkRoleExists();
		}

		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}

	private Role checkRoleExists() {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		return roleRepository.save(role);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
	}

	public UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto();
		String[] arr = user.getName().split(" ");
		userDto.setFirstName(arr[0]);
		userDto.setLastName(arr[1]);
		userDto.setEmail(user.getEmail());
		return userDto;
	}

}
