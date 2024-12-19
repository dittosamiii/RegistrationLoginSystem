package com.springboot.registration_login_system.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.registration_login_system.dto.UserDto;
import com.springboot.registration_login_system.entity.User;
import com.springboot.registration_login_system.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	private UserService userService;

	public AuthController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/index")
	public String home() {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "register";
	}

	@PostMapping("/register/save")
	public String submitRequest(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

		User user = userService.findByEmail(userDto.getEmail());
		if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
			result.rejectValue("email", null, "User with same email already exists");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "register";
		}
		userService.saveUser(userDto);
		return "redirect:/register?success";
	}

	@GetMapping("/users")
	public String users(Model model) {
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}
}
