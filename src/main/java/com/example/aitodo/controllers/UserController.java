package com.example.aitodo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitodo.models.User;
import com.example.aitodo.services.WebService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final WebService webService;

	public UserController(WebService webService) {
		this.webService = webService;
	}

	@GetMapping("")
	public List<User> getUsers() {
		return this.webService.getAllUsers();
	}

}
