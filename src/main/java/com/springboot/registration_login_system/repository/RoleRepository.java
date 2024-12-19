package com.springboot.registration_login_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.registration_login_system.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
