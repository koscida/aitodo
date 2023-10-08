package com.example.aitodo.services;

import java.util.List;

import com.example.aitodo.models.ToDoList;
import com.example.aitodo.models.Item;

public class ListItem {

	private ToDoList todoList;
	private List<Item> items;

	public ToDoList getTodoList() {
		return this.todoList;
	}

	public void setTodoList(ToDoList todoList) {
		this.todoList = todoList;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
