package com.example.aitodo.controllers;
import com.example.aitodo.models.*;
import com.example.aitodo.services.WebService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.hibernate.tool.schema.spi.ExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.setup.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(MockitoExtension.class)
class ListControllerMockMvcStandaloneTest {

	private MockMvc mvc;

	@Mock
	ItemRepository itemRepository;
	@Mock
	ToDoListRepository toDoListRepository;
	@Mock
	UserRepository userRepository;

	@InjectMocks
	ListController listController;

	private JacksonTester<List<Item>> jsonListItems;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());

		// MockMvc standalone approach
		mvc = MockMvcBuilders.standaloneSetup(listController).build();

	}


	@Test
	void getList() throws Exception {
//		mvc.perform(MockMvcRequestBuilders.get("/api/lists/1").accept(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(MediaTypeockMvcResultMatchers.jsonPath("$.listId").exists());


	}

	@Test
	void getListUpdateLast() {
//		ToDoList toDoList = new ToDoList();
//		toDoList.setListName("Test123");
//
//		when(webService.save(any()))
	}

	@Test
	void getListItems() throws Exception{
		// mock
		User user = new User("Test User", "test@email.com", null, false, null);

		ToDoList toDoList = new ToDoList(user, "Test List", false, false, null);
		toDoList.setListId(1);

		Item item1 = new Item(toDoList, 1, "aaa", null, false, false, null);
		Item item2 = new Item(toDoList, 2, "bbb", null, false, false, null);

		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		toDoList.setItems(items);

		// given
		given(toDoListRepository.findByListIdAndIsDeleted(1,false)).willReturn(toDoList);

		// when
		MockHttpServletResponse response = mvc.perform(
			MockMvcRequestBuilders.get("/api/lists/1")
				.accept(MediaType.APPLICATION_JSON))
			.andReturn().getResponse();

		// then
		assertEquals( HttpStatus.OK.value(), response.getStatus() );

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