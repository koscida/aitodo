package com.example.aitodo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.aitodo.services.ListItem;
import com.example.aitodo.services.WebService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(name = "/lists")
public class ListController {

	private final WebService webService;

	public ListController(WebService webService) {
		this.webService = webService;
	}

	@GetMapping(value = "")
	public ListItem getListItem(@RequestParam String listId) {
		return this.webService.getListItem(Integer.valueOf(listId));
	}

}
