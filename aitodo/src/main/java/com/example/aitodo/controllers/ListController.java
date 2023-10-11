package com.example.aitodo.controllers;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.aitodo.models.Item;
import com.example.aitodo.models.ToDoList;
import com.example.aitodo.models.User;
import com.example.aitodo.services.ListItem;
import com.example.aitodo.services.WebService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lists")
public class ListController {

	private final WebService webService;

	public ListController(WebService webService) {
		this.webService = webService;
	}

	// ////
	// GET

	@GetMapping("")
	public ResponseEntity<List<ToDoList>> getLists() {
		try {
			List<ToDoList> toDoList = this.webService.getAllLists();
			return new ResponseEntity<>(toDoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ToDoList> getList(@PathVariable("id") long listId) {
		try {
			ToDoList toDoList = this.webService.getListById(listId);
			if (toDoList == null)
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(toDoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/lastUpdate")
	public ResponseEntity<Timestamp> getListUpdateLast(@PathVariable("id") long listId) {
		try {
			ToDoList toDoList = this.webService.getListById(listId);
			if (toDoList == null)
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(toDoList.getLastUpdate(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/items")
	public ResponseEntity<List<Item>> getListItems(@PathVariable("id") long listId) {
		try {
			ToDoList toDoList = this.webService.getListById(listId);
			if (toDoList == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<>(toDoList.getItems(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// POST

	@PostMapping("")
	public ResponseEntity<ToDoList> createList(@RequestBody ToDoList toDoList) {
		try {
			User defaultUser = this.webService.getUserById((long) 1);
			ToDoList newList = this.webService.createNewList(defaultUser, toDoList);
			return new ResponseEntity<>(newList, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// PUT

	@PutMapping("/{id}")
	public ResponseEntity<ToDoList> updateList(@PathVariable("id") long listId, @RequestBody ToDoList toDoListEdits) {
		try {
			ToDoList toDoListUpdating = this.webService.getListById(listId);
			if (toDoListUpdating == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				return new ResponseEntity<>(this.webService.updateList(toDoListUpdating, toDoListEdits), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// DELETE

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteList(@PathVariable("id") long listId) {
		try {
			ToDoList toDoList = this.webService.getListById(listId);
			if (toDoList == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				try {
					this.webService.deleteList(toDoList);
					return new ResponseEntity<>(HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// no delete all, there is pre-populated data in the database

}
