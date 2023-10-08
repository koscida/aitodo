package com.example.aitodo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	// public List<Item> findAllByListId(long listId);

	public List<Item> findAllByIsDeleted(boolean isDeleted);

	public Item findByItemIdAndIsDeleted(long itemId, boolean isDeleted);
}
