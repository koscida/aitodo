package com.example.aitodo.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.aitodo.models.Item;
import com.example.aitodo.models.ToDoList;
import com.example.aitodo.models.User;
import com.example.aitodo.services.WebService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

	final WebService webService;

	ItemController(WebService webService) {
		this.webService = webService;
	}

	// ////
	// GET

//	@GetMapping("")
	// remove get all, no use case


	@GetMapping("/{id}")
	public ResponseEntity<Item> getItem(@PathVariable("id") long itemId) {
		try {
			Item item = this.webService.getItemById(itemId);
			if (item == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				return new ResponseEntity<>(item, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/lastUpdate")
	public ResponseEntity<ZonedDateTime> getItemLastUpdated(@PathVariable("id") long itemId) {
		try {
			Item item = this.webService.getItemById(itemId);
			if (item == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				return new ResponseEntity<>(item.getLastUpdate(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// POST

	//	@PostMapping("")
	// remove post mapping for single item, can only post an item to a list

	// ////
	// PUT

	@PutMapping("/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable("id") long itemId, @RequestBody Item itemEdits) {
		try {
			Item itemUpdating = this.webService.getItemById(itemId);
			if (itemUpdating == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else {
				Item item = this.webService.updateItem(itemUpdating, itemEdits);
				return new ResponseEntity<>(item, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ////
	// DELETE

	// @DeleteMapping("/{id}")
	// remove delete single item, must delete from a list

}
