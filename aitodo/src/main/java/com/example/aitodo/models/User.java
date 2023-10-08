package com.example.aitodo.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private long userId;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "email")
	private String email;

	@Column(name = "google_id")
	private String googleId;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<ToDoList> toDoLists = new ArrayList<>();

	public User() {
	}

	public User(String displayName, String email, String googleId, boolean isDeleted) {
		this.displayName = displayName;
		this.email = email;
		this.googleId = googleId;
		this.isDeleted = isDeleted;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGoogleId() {
		return this.googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public List<ToDoList> getToDoLists() {
		return this.toDoLists;
	}

	public void setToDoLists(List<ToDoList> toDoLists) {
		this.toDoLists = toDoLists;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "User {" +
				" userId: " + userId +
				" displayName: " + displayName +
				" email: " + email +
				" googleId: " + googleId +
				" toDoLists: " + toDoLists +
				" isDeleted: " + isDeleted +
				"}";
	}

}
