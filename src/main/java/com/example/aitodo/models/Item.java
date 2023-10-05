package com.example.aitodo.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ITEMS")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "item_id")
	private long itemId;

	@Column(name = "list_id")
	private long listId;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "is_complete")
	private boolean isComplete;

	public Item() {
	}

	public Item(long itemId, long listId, String itemDescription, Date dueDate, boolean isComplete) {
		this.itemId = itemId;
		this.listId = listId;
		this.itemDescription = itemDescription;
		this.dueDate = dueDate;
		this.isComplete = isComplete;
	}

	public long getItemId() {
		return this.itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getListId() {
		return this.listId;
	}

	public void setListId(long listId) {
		this.listId = listId;
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
				" listId: " + listId +
				" itemDescription: " + itemDescription +
				" dueDate: " + dueDate +
				" isComplete: " + isComplete +
				"}";
	}

}
