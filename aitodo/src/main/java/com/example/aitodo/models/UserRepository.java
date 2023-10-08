package com.example.aitodo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public List<User> findAllByIsDeleted(boolean isDeleted);

	public User findByUserIdAndIsDeleted(long userId, boolean isDeleted);

}
