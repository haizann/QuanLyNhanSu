package com.example.quanlyns.service;

import java.util.List;
import java.util.Optional;

import com.example.quanlyns.entity.User;

public interface UserService {

	User createUser(User user);

	List<User> getAllUsers();

	Optional<User> getUserById(Long id);

	User updateUser(Long id, User updatedUser);

	void deleteUser(Long id);

	User handleGetUserByUsername(String email);
}
