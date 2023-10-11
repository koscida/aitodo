package com.example.aitodo.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "LISTS")
public class ToDoList {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "list_id")
	private long listId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "list_name")
	private String listName;

	@Column(name = "is_complete")
	private boolean isComplete;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "last_update")
	private Timestamp lastUpdate;

	@JsonManagedReference
	@OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Item> items = new ArrayList();

	public ToDoList() {
	}

	public ToDoList(User user, String listName, boolean isComplete, boolean isDeleted, Timestamp lastUpdate) {
		this.user = user;
		this.listName = listName;
		this.isComplete = isComplete;
		this.isDeleted = isDeleted;
		this.lastUpdate = lastUpdate;
	}

	public long getListId() {
		return this.listId;
	}

	public void setListId(long listId) {
		this.listId = listId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getListName() {
		return this.listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public boolean isIsComplete() {
		return this.isComplete;
	}

	public boolean getIsComplete() {
		return this.isComplete;
	}

	public void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isIsDeleted() {
		return this.isDeleted;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "ToDoList {" +
				" listId: " + listId +
				" userId: " + user.getUserId() +
				" listName: " + listName +
				" isComplete: " + isComplete +
				" items: " + items +
				" isDeleted: " + isDeleted +
				" lastUpdate: " + lastUpdate +
				"}";
	}

}
