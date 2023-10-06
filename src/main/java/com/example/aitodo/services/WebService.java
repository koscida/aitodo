package com.example.aitodo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.aitodo.models.Item;
import com.example.aitodo.models.ItemRepository;
import com.example.aitodo.models.ToDoList;
import com.example.aitodo.models.ToDoListRepository;
import com.example.aitodo.models.User;
import com.example.aitodo.models.UserRepository;

@Service
public class WebService {

	private final UserRepository userRepository;
	private final ToDoListRepository toDoListRepository;
	private final ItemRepository itemRepository;

	public WebService(UserRepository userRepository, ToDoListRepository toDoListRepository,
			ItemRepository itemRepository) {
		this.userRepository = userRepository;
		this.toDoListRepository = toDoListRepository;
		this.itemRepository = itemRepository;
	}

	// ////
	// single entities

	// users

	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	public User getUserById(long userId) {
		Optional<User> userOptional = this.userRepository.findById(userId);
		User user = userOptional.orElse(null);
		return user;
	}

	public User getUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	public User createNewUser(User user) {
		return this.userRepository.save(user);
	}

	public User updateUser(User user) {
		return this.userRepository.save(user);
	}

	// lists

	public List<ToDoList> getAllLists() {
		return this.toDoListRepository.findAll();
	}

	public List<ToDoList> getUserLists(long userId) {
		return this.toDoListRepository.findAllByUserId(userId);
	}

	public ToDoList getList(long listId) {
		Optional<ToDoList> toDoListOptional = this.toDoListRepository.findById(listId);
		ToDoList toDoList = toDoListOptional.orElse(null);
		return toDoList;
	}

	// items

	public List<Item> getAllItems() {
		return this.itemRepository.findAll();
	}

	public List<Item> getListItems(long listId) {
		return this.itemRepository.findAllByListId(listId);
	}

	// ////
	// dto object

	public ListItem getListItem(long listId) {
		// get the list
		ToDoList toDoList = this.getList(listId);

		// get the items
		List<Item> items = this.getListItems(listId);

		// create dto listitem
		ListItem listItem = new ListItem();
		listItem.setTodoList(toDoList);
		listItem.setItems(items);

		return listItem;
	}

	public List<ListItem> getUserListItems(int userId) {
		// get the lists
		List<ToDoList> toDoLists = this.getUserLists(userId);

		// create dto listitem
		List<ListItem> listItems = new ArrayList<>();

		for (ToDoList toDoList : toDoLists) {
			listItems.add(this.getListItem(toDoList.getListId()));
		}
		;

		return listItems;
	}

}
