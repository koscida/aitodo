package com.example.aitodo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitodo.models.User;
import com.example.aitodo.services.WebService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final WebService webService;

	public UserController(WebService webService) {
		this.webService = webService;
	}

	@GetMapping("")
	public ResponseEntity<List<User>> getUsers(@RequestParam(name = "email", required = false) String email) {
		try {
			List<User> users = new ArrayList<>();

			if (email == null)
				this.webService.getAllUsers().forEach(users::add);
			else
				users.add(this.webService.getUserByEmail(email));

			if (users.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
		User user = this.webService.getUserById(userId);

		if (user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		System.out.println("--User--");
		System.out.println(user);
		try {
			User _user = this.webService.createNewUser(
					new User(user.getDisplayName(), user.getEmail(), user.getGoogleId()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
