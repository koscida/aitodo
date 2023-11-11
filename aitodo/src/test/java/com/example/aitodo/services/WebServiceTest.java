package com.example.aitodo.services;
import com.example.aitodo.models.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class WebServiceTest {

	private ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
	private ToDoListRepository toDoListRepository = Mockito.mock(ToDoListRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);


	@Test
	void getAllUsers() {



	}

	@Test
	void getUserById() {
//		WebService webService = new WebService(userRepository, toDoListRepository, itemRepository);
//
//		User user = new User("Tester McTesting", "test1@email.com", null, false, null)
//		ResponseEntity<User> expected = new ResponseEntity<>(user, HttpStatus.OK);
//
//		Mockito.when(webService.getUserById(1)).thenReturn(Optional.of(user));
	}

	@Test
	void getUserByEmail() {
	}
	@Test
	void createNewUser() {
	}
	@Test
	void updateUser() {
	}
	@Test
	void deleteUser() {
	}
	@Test
	void getAllLists() {
	}
	@Test
	void getListById() {
	}
	@Test
	void createNewList() {
	}
	@Test
	void updateList() {
	}
	@Test
	void deleteList() {
	}
	@Test
	void getAllItems() {
	}
	@Test
	void getItemById() {
	}
	@Test
	void createNewItem() {
	}
	@Test
	void updateItem() {
	}
	@Test
	void deleteItem() {
	}
}