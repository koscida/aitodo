package com.example.aitodo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

	// public List<ToDoList> findAllByUser(User user);

	public List<ToDoList> findAllByIsDeleted(boolean isDeleted);

	public ToDoList findByListIdAndIsDeleted(long listId, boolean isDeleted);
}
