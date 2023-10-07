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

	//
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

	public User updateUser(User userUpdating, User userEdits) {
		userUpdating.setDisplayName(userEdits.getDisplayName());
		userUpdating.setEmail(userEdits.getEmail());
		userUpdating.setGoogleId(userEdits.getGoogleId());
		return this.userRepository.save(userUpdating);
	}

	// public void deleteAllUsers() {
	// this.userRepository.deleteAll();
	// }

	public void deleteUser(long userId) {
		this.userRepository.deleteById(userId);
	}

	//
	// lists

	public List<ToDoList> getAllLists() {
		return this.toDoListRepository.findAll();
	}

	public ToDoList getListById(long listId) {
		Optional<ToDoList> toDoListOptional = this.toDoListRepository.findById(listId);
		ToDoList toDoList = toDoListOptional.orElse(null);
		return toDoList;
	}

	// public List<ToDoList> getUserLists(long listId) {

	// return this.toDoListRepository.findAllByUser(listId);
	// }

	public ToDoList createNewList(User user, ToDoList listId) {
		listId.setUser(user);
		return this.toDoListRepository.save(listId);
	}

	public ToDoList updateList(ToDoList toDoListUpdating, ToDoList toDoListEdits) {
		toDoListUpdating.setListName(toDoListEdits.getListName());
		toDoListUpdating.setIsComplete(toDoListEdits.getIsComplete());
		return this.toDoListRepository.save(toDoListUpdating);
	}

	public void deleteList(long listId) {
		this.toDoListRepository.deleteById(listId);
	}

	//
	// items

	public List<Item> getAllItems() {
		return this.itemRepository.findAll();
	}

	public List<Item> getListItems(long listId) {
		return new ArrayList<>();
		// return this.itemRepository.findAllByListId(listId);
	}

	// ////
	// dto object

	public ListItem getListItem(long listId) {
		// get the list
		ToDoList toDoList = this.getListById(listId);

		// get the items
		List<Item> items = this.getListItems(listId);

		// create dto listitem
		ListItem listItem = new ListItem();
		listItem.setTodoList(toDoList);
		listItem.setItems(items);

		return listItem;
	}

	// public List<ListItem> getUserListItems(int userId) {
	// // get the lists
	// List<ToDoList> toDoLists = this.getUserLists(userId);

	// // create dto listitem
	// List<ListItem> listItems = new ArrayList<>();

	// for (ToDoList toDoList : toDoLists) {
	// listItems.add(this.getListItem(toDoList.getListId()));
	// }
	// ;

	// return listItems;
	// }

}
