package com.example.aitodo.controllers;
import com.example.aitodo.models.*;
import com.example.aitodo.services.WebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
@AutoConfigureJsonTesters
@WebMvcTest(ListController.class)
class ListControllerMockMvcWithContextTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ItemRepository itemRepository;
	@MockBean
	private ToDoListRepository toDoListRepository;
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private WebService webService;

	@Autowired
	private JacksonTester<ToDoList> jsonToDoList;
	@Autowired
	private JacksonTester<List<Item>> jsonListItems;

	User mockUser;
	ToDoList mockToDoList;
	Item mockItem1;
	List<Item> mockItems;

	@BeforeEach
	void setup() {
		mockUser = new User("Test User", "test@email.com", null, false, null);

		mockToDoList = new ToDoList(mockUser, "Test List", false, false, null);
		mockToDoList.setListId(1);

		mockItems = new ArrayList<>();
		mockItem1 = new Item(mockToDoList, 1, "Item a", null, false, false, null);
		mockItems.add(mockItem1);
		Item mockItem2 = new Item(mockToDoList, 2, "Item b", null, false, false, null);
		mockItems.add(mockItem2);
		mockToDoList.setItems(mockItems);
	}

	@Test
	void getList() {
	}

	@Test
	void getListUpdateLast() throws Exception {
		// given
		given(webService.getListById(1)).willReturn(mockToDoList);

		// when
		MockHttpServletResponse response = mvc.perform(
				MockMvcRequestBuilders.get("/api/lists/1").accept(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonToDoList.write(mockToDoList).getJson(), response.getContentAsString());
	}

	@Test
	void getListItems() throws Exception {

		// given
		given(webService.getListById(1)).willReturn(mockToDoList);

		// when
		MockHttpServletResponse response = mvc.perform(
				MockMvcRequestBuilders.get("/api/lists/1")
					.accept(MediaType.APPLICATION_JSON)
			)
			.andReturn().getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonToDoList.write(mockToDoList).getJson(), response.getContentAsString());
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