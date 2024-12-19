package com.springboot.registration_login_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.registration_login_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
