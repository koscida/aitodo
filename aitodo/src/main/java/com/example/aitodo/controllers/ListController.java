package com.example.aitodo.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

//	@GetMapping("")
	// remove get all, no use case

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
	public ResponseEntity<ZonedDateTime> getListUpdateLast(@PathVariable("id") long listId) {
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

//	@PostMapping("")
	// remove post mapping for single list, only add list to user

	// create a new list item
	@PostMapping("/{id}/items")
	public ResponseEntity<ToDoList> createItem(@PathVariable("id") long listId, @RequestBody Item item) {
		try {
			ToDoList toDoList = this.webService.getRawListById(listId);
			if (toDoList == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				// create item
				Item newItem = this.webService.createNewItem(toDoList, item);

				// manually add to list
				 List<Item> items = toDoList.getItems();
				 items.add(newItem);
				 toDoList.setItems(items);

				// return updated list
				return new ResponseEntity<>(toDoList, HttpStatus.CREATED);

			}
		} catch (Exception e) {
			System.out.println(e);
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

	@PutMapping("/{listId}/items/{itemId}")
	public ResponseEntity<ToDoList> updateItemThroughList(@PathVariable("listId") long listId,
			@PathVariable("itemId") long itemId,
			@RequestBody Item itemEdits) {
		try {
			ToDoList toDoList = this.webService.getListById(listId);
			Item itemUpdating = this.webService.getItemById(itemId);
			if (toDoList == null || itemUpdating == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				this.webService.updateItem(itemUpdating, itemEdits);

				ToDoList toDoListUpdated = this.webService.getListById(listId);
				return new ResponseEntity<>(toDoListUpdated, HttpStatus.OK);
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

	@DeleteMapping("/{listId}/items/{itemId}")
	public ResponseEntity<ToDoList> deleteListItem(@PathVariable long listId, @PathVariable long itemId) {
		try {
			ToDoList toDoList = this.webService.getRawListById(listId);
			Item item = this.webService.getItemById(itemId);

			if (toDoList == null || item == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				this.webService.deleteItem(item);

				ToDoList toDoListUpdated = this.webService.getListById(listId);
				return new ResponseEntity<>(toDoListUpdated, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// no delete all, there is pre-populated data in the database
//	@DeleteMapping("")

}
