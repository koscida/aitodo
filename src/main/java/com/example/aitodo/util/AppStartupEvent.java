package com.example.aitodo.util;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.aitodo.models.Item;
import com.example.aitodo.models.ItemRepository;
import com.example.aitodo.models.ToDoList;
import com.example.aitodo.models.ToDoListRepository;
import com.example.aitodo.models.User;
import com.example.aitodo.models.UserRepository;
import com.example.aitodo.services.WebService;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

	private final WebService webService;

	public AppStartupEvent(WebService webService) {
		this.webService = webService;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		// users
		List<User> users = this.webService.getAllUsers();
		System.out.println("--Users--");
		users.forEach(System.out::println);

		// lists
		List<ToDoList> toDoLists = this.webService.getAllLists();
		System.out.println("--Lists--");
		toDoLists.forEach(System.out::println);

		// items
		List<Item> items = this.webService.getAllItems();
		System.out.println("--Items--");
		items.forEach(System.out::println);

	}

}
