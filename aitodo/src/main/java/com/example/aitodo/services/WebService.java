package com.example.aitodo.services;

// import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

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
		return this.userRepository.findAllByIsDeleted(false);
	}

	public User getUserById(long userId) {
		return this.userRepository.findByUserIdAndIsDeleted(userId, false);
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
		userUpdating.setLastUpdate(LocalDateTime.now());
		return this.userRepository.save(userUpdating);
	}

	public void deleteUser(User user) {
		user.setIsDeleted(true);
		this.userRepository.save(user);
		// this.userRepository.deleteById(userId);
	}

	//
	// lists

	public List<ToDoList> getAllLists() {
		return this.toDoListRepository.findAllByIsDeleted(false);
	}

	public ToDoList getListById(long listId) {
		return this.toDoListRepository.findByListIdAndIsDeleted(listId, false);
	}

	public ToDoList createNewList(User user, ToDoList listId) {
		listId.setUser(user);
		return this.toDoListRepository.save(listId);
	}

	public ToDoList updateList(ToDoList toDoListUpdating, ToDoList toDoListEdits) {
		toDoListUpdating.setListName(toDoListEdits.getListName());
		toDoListUpdating.setIsComplete(toDoListEdits.getIsComplete());
		toDoListUpdating.setLastUpdate(LocalDateTime.now());
		return this.toDoListRepository.save(toDoListUpdating);
	}

	public void deleteList(ToDoList list) {
		list.setIsDeleted(true);
		this.toDoListRepository.save(list);
		// this.toDoListRepository.deleteById(listId);
	}

	//
	// items

	public List<Item> getAllItems() {
		return this.itemRepository.findAllByIsDeleted(false);
	}

	public Item getItemById(long itemId) {
		return this.itemRepository.findByItemIdAndIsDeleted(itemId, false);
	}

	public Item createNewItem(ToDoList toDoList, Item item) {
		item.setToDoList(toDoList);
		return this.itemRepository.save(item);
	}

	public Item updateItem(Item updatingItem, Item itemEdits) {
		updatingItem.setItemOrder(itemEdits.getItemOrder());
		updatingItem.setItemDescription(itemEdits.getItemDescription());
		updatingItem.setDueDate(itemEdits.getDueDate());
		updatingItem.setIsComplete(itemEdits.isIsComplete());
		updatingItem.setLastUpdate(LocalDateTime.now());
		return this.itemRepository.save(updatingItem);
	}

	public void deleteItem(Item item) {
		item.setIsDeleted(true);
		this.itemRepository.save(item);
		// this.itemRepository.deleteById(itemId);
	}

	// public List<Item> getListItems(long listId) {
	// return new ArrayList<>();
	// // return this.itemRepository.findAllByListId(listId);
	// }

	// ////
	// dto object

	// public ListItem getListItem(long listId) {
	// // get the list
	// ToDoList toDoList = this.getListById(listId);

	// // get the items
	// List<Item> items = this.getListItems(listId);

	// // create dto listitem
	// ListItem listItem = new ListItem();
	// listItem.setTodoList(toDoList);
	// listItem.setItems(items);

	// return listItem;
	// }

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
