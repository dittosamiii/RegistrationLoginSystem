package com.springboot.registration_login_system.service;

import java.util.List;

import com.springboot.registration_login_system.dto.UserDto;
import com.springboot.registration_login_system.entity.User;

public interface UserService {
	void saveUser(UserDto userDto);

	User findByEmail(String email);
	
	List<UserDto> findAllUsers();
}
