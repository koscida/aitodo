package com.example.aitodo.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ITEMS")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "item_id")
	private long itemId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "LIST_ID", nullable = false)
	private ToDoList toDoList;

	@Column(name = "item_order")
	private int itemOrder;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "due_date")
	private LocalDateTime dueDate;

	@Column(name = "is_complete")
	private boolean isComplete;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "last_update")
	private ZonedDateTime lastUpdate;

	public Item() {
	}

	public Item(ToDoList toDoList, int itemOrder, String itemDescription, LocalDateTime dueDate,
			boolean isComplete, boolean isDeleted, ZonedDateTime lastUpdate) {
		this.toDoList = toDoList;
		this.itemOrder = itemOrder;
		this.itemDescription = itemDescription;
		this.dueDate = dueDate;
		this.isComplete = isComplete;
		this.isDeleted = isDeleted;
		this.lastUpdate = lastUpdate;
	}

	public long getItemId() {
		return this.itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public ToDoList getToDoList() {
		return this.toDoList;
	}

	public void setToDoList(ToDoList toDoList) {
		this.toDoList = toDoList;
	}

	public int getItemOrder() {
		return this.itemOrder;
	}

	public void setItemOrder(int itemOrder) {
		this.itemOrder = itemOrder;
	}

	public String getItemDescription() {
		return this.itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public LocalDateTime getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
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

	public boolean isIsDeleted() {
		return this.isDeleted;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public ZonedDateTime getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(ZonedDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "Item {" +
				" itemId: " + itemId +
				(toDoList != null ? (" listId: " + toDoList.getListId()) : "") +
				" itemOrder: " + itemOrder +
				" itemDescription: " + itemDescription +
				" dueDate: " + dueDate +
				" isComplete: " + isComplete +
				" isDeleted: " + isDeleted +
				" lastUpdate: " + lastUpdate +
				"}";
	}

}
