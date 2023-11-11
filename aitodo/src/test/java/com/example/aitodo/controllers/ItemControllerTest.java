package com.example.aitodo.controllers;
import com.example.aitodo.models.Item;
import com.example.aitodo.models.ItemRepository;
import com.example.aitodo.services.WebService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;

@RestController
class ItemControllerTest {



	@Test
	void getItem() {
//		ItemController itemController = new ItemController(null);
//
//		ResponseEntity<Item> actual = new ResponseEntity<Item>();
//
//		Item expectedItem = itemController.getItem(1);
//		ResponseEntity expected = new ResponseEntity<>(expectedItem, HttpStatus.OK);
//
//		assertSame(expected, actual);
	}

	@Test
	void getItemLastUpdated() {
	}

	@Test
	void updateItem() {
	}
}