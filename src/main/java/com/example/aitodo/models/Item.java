package com.example.aitodo.models;

import java.util.Date;

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
	// @ManyToOne(fetch = FetchType.EAGER, optional = false)
	@ManyToOne
	@JoinColumn(name = "LIST_ID", nullable = false)
	private ToDoList toDoList;

	// @Column(name = "list_id")
	// private long listId;

	@Column(name = "item_order")
	private int itemOrder;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "is_complete")
	private boolean isComplete;

	public Item() {
	}

	public Item(ToDoList toDoList, int itemOrder, String itemDescription, Date dueDate,
			boolean isComplete) {
		this.toDoList = toDoList;
		this.itemOrder = itemOrder;
		this.itemDescription = itemDescription;
		this.dueDate = dueDate;
		this.isComplete = isComplete;
	}

	// public Item(long listId, int itemOrder, String itemDescription, Date dueDate,
	// boolean isComplete) {
	// this.listId = listId;
	// this.itemOrder = itemOrder;
	// this.itemDescription = itemDescription;
	// this.dueDate = dueDate;
	// this.isComplete = isComplete;
	// }

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

	// public long getListId() {
	// return this.listId;
	// }

	// public void setListId(long listId) {
	// this.listId = listId;
	// }

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

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
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

	@Override
	public String toString() {
		return "Item {" +
				" itemId: " + itemId +
				" listId: " + toDoList.getListId() +
				" itemOrder: " + itemOrder +
				" itemDescription: " + itemDescription +
				" dueDate: " + dueDate +
				" isComplete: " + isComplete +
				"}";
	}

}
