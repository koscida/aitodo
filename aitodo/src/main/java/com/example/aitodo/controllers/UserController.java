package com.example.aitodo.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitodo.models.Item;
import com.example.aitodo.models.ToDoList;
import com.example.aitodo.models.User;
import com.example.aitodo.services.WebService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final WebService webService;

	public UserController(WebService webService) {
		this.webService = webService;
	}

	// ////
	// GET

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
		try {
			User user = this.webService.getUserById(userId);

			if (user == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/lastUpdate")
	public ResponseEntity<ZonedDateTime> getUserLastUpdate(@PathVariable("id") long userId) {
		try {
			User user = this.webService.getUserById(userId);
			if (user == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<>(user.getLastUpdate(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/lists")
	public ResponseEntity<List<ToDoList>> getUserListsById(@PathVariable("id") long userId) {
		try {
			User user = this.webService.getUserById(userId);
			if (user == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<>(user.getToDoLists(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// POST

	@PostMapping("")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			User newUser = this.webService.createNewUser(user);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{id}/lists")
	public ResponseEntity<ToDoList> createList(@PathVariable("id") long userId, @RequestBody ToDoList toDoList) {
		try {
			User user = this.webService.getUserById(userId);
			if (user == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				ToDoList newList = this.webService.createNewList(user, toDoList);
				return new ResponseEntity<>(newList, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// PUT

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long userId, @RequestBody User userEdits) {
		try {
			User userUpdating = this.webService.getUserById(userId);

			if (userUpdating == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(this.webService.updateUser(userUpdating, userEdits), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{userId}/lists/{listId}")
	public ResponseEntity<User> updateListByUser(@PathVariable("userId") long userId,
			@PathVariable("listId") long listId, @RequestBody ToDoList listEdits) {
		try {
			User user = this.webService.getUserById(userId);
			ToDoList toDoList = this.webService.getListById(listId);
			if (user == null || toDoList == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				this.webService.updateList(toDoList, listEdits);
				return new ResponseEntity<>(this.webService.getUserById(userId), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{userId}/lists/{listId}/items/{itemId}")
	public ResponseEntity<User> updateItemByUser(@PathVariable("userId") long userId, @PathVariable long listId,
			@PathVariable long itemId, @RequestBody Item itemEdits) {
		try {
			User user = this.webService.getUserById(userId);
			ToDoList toDoList = this.webService.getListById(listId);
			Item item = this.webService.getItemById(itemId);
			if (user == null || toDoList == null || item == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				this.webService.updateItem(item, itemEdits);
				return new ResponseEntity<>(this.webService.getUserById(userId), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// DELETE

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long userId) {
		try {
			User user = this.webService.getUserById(userId);

			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				this.webService.deleteUser(user);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
