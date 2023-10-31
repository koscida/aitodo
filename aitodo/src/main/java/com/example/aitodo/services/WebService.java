package com.example.aitodo.services;

// import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
		ZonedDateTime now = ZonedDateTime.now();
		user.setLastUpdate(now);

		return this.userRepository.saveAndFlush(user);
	}

	public User updateUser(User userUpdating, User userEdits) {
		userUpdating.setDisplayName(userEdits.getDisplayName());
		userUpdating.setEmail(userEdits.getEmail());
		userUpdating.setGoogleId(userEdits.getGoogleId());

		ZonedDateTime now = ZonedDateTime.now();
		userUpdating.setLastUpdate(now);

		return this.userRepository.saveAndFlush(userUpdating);
	}

	public void deleteUser(User user) {
		ZonedDateTime now = ZonedDateTime.now();
		user.setLastUpdate(now);

		user.setIsDeleted(true);

		this.userRepository.saveAndFlush(user);
	}

	//
	// lists

	public List<ToDoList> getAllLists() {
		return this.toDoListRepository.findAllByIsDeleted(false);
	}

	public ToDoList getListById(long listId) {
		return this.toDoListRepository.findByListIdAndIsDeleted(listId, false);
	}

	public ToDoList createNewList(User user, ToDoList toDoList) {
		ZonedDateTime now = ZonedDateTime.now();

		user.setLastUpdate(now);
		this.updateUser(user, user);

		toDoList.setUser(user);
		toDoList.setLastUpdate(now);
		return this.toDoListRepository.saveAndFlush(toDoList);
	}

	public ToDoList updateList(ToDoList toDoListUpdating, ToDoList toDoListEdits) {
		ZonedDateTime now = ZonedDateTime.now();

		User user = getUserById(toDoListUpdating.getUser().getUserId());
		user.setLastUpdate(now);
		this.updateUser(user, user);

		toDoListUpdating.setListName(toDoListEdits.getListName());
		toDoListUpdating.setIsComplete(toDoListEdits.getIsComplete());
		toDoListUpdating.setLastUpdate(now);
		return this.toDoListRepository.saveAndFlush(toDoListUpdating);
	}

	public void deleteList(ToDoList list) {
		ZonedDateTime now = ZonedDateTime.now();
		list.setLastUpdate(now);

		list.setIsDeleted(true);

		this.toDoListRepository.saveAndFlush(list);
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
		ZonedDateTime now = ZonedDateTime.now();

		toDoList.setLastUpdate(now);
		this.updateList(toDoList, toDoList);

		User user = this.getUserById(toDoList.getUser().getUserId());
		user.setLastUpdate(now);
		this.updateUser(user, user);

		item.setToDoList(toDoList);
		item.setLastUpdate(now);
		return this.itemRepository.saveAndFlush(item);
	}

	public Item updateItem(Item updatingItem, Item itemEdits) {
		ZonedDateTime now = ZonedDateTime.now();

		ToDoList toDoList = updatingItem.getToDoList();
		toDoList.setLastUpdate(now);
		this.updateList(toDoList, toDoList);

		User user = toDoList.getUser();
		user.setLastUpdate(now);
		this.updateUser(user, user);

		if (itemEdits.getItemOrder() > 0)
			updatingItem.setItemOrder(itemEdits.getItemOrder());
		if (itemEdits.getItemDescription() != null)
			updatingItem.setItemDescription(itemEdits.getItemDescription());
		if (itemEdits.getDueDate() != null)
			updatingItem.setDueDate(itemEdits.getDueDate());
		updatingItem.setIsComplete(itemEdits.getIsComplete());

		updatingItem.setLastUpdate(now);

		return this.itemRepository.saveAndFlush(updatingItem);
	}

	public void deleteItem(Item item) {
		ZonedDateTime now = ZonedDateTime.now();
		item.setLastUpdate(now);

		item.setIsDeleted(true);

		this.itemRepository.saveAndFlush(item);
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
