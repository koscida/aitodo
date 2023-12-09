package com.example.aitodo.controllers;
import com.example.aitodo.models.*;
import com.example.aitodo.services.WebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListControllerSpringBootTest {

	@MockBean
	private ToDoListRepository toDoListRepository;
	@MockBean
	private ItemRepository itemRepository;
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private WebService webService;

	@Autowired
	private TestRestTemplate restTemplate;

	User mockUser;
	ToDoList mockToDoList;
	Item mockItem1;
	List<Item> mockItems;

	// ////
// Before

	@BeforeEach
	void setup() {
		mockUser = new User("Test User", "test@email.com", null, false, null);
		mockUser.setUserId(1);

		mockToDoList = new ToDoList(null, "Test List", false, false, null);
		mockToDoList.setListId(1);

		mockItems = new ArrayList<>();
		mockItem1 = new Item(mockToDoList, 1, "Item a", null, false, false, null);
		mockItem1.setItemId(1);
		mockItems.add(mockItem1);
		Item mockItem2 = new Item(mockToDoList, 2, "Item b", null, false, false, null);
		mockItem2.setItemId(1);
		mockItems.add(mockItem2);
		mockToDoList.setItems(mockItems);
	}

	// ////
	// Tests

	@Test
	void getList() {
		// given
		given(toDoListRepository.findByListIdAndIsDeleted(1,false)).willReturn(mockToDoList);
		given(webService.getListById(1)).willReturn(mockToDoList);

		// when
		ResponseEntity<ToDoList> response = restTemplate.getForEntity("/api/lists/1", ToDoList.class);
//		System.out.println(response);

//		System.out.println(mockToDoList);


		// then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(mockToDoList);
	}

	@Test
	void getListUpdateLast() {
	}

	@Test
	void getListItems() {
	}

	@Test
	void createItem() {
	}

	@Test
	void updateList() {
	}

	@Test
	void updateItemThroughList() {
	}

	@Test
	void deleteList() {
	}

	@Test
	void deleteListItem() {
	}
}