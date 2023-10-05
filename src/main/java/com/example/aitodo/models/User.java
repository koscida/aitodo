package com.example.aitodo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	public long getUserId() {
		return this.userId;
	}

	public User() {
	}

	public User(String displayName, String email, String googleId) {
		this.displayName = displayName;
		this.email = email;
		this.googleId = googleId;
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

	@Override
	public String toString() {
		return "User {" +
				" userId: " + userId +
				" displayName: " + displayName +
				" email: " + email +
				" googleId: " + googleId +
				"}";
	}

}
