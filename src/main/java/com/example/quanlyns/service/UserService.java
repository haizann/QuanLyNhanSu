package com.example.quanlyns.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.quanlyns.entity.User;
import com.example.quanlyns.entity.dto.ResultPaginationDTO;
import com.example.quanlyns.entity.response.CreateUserResponse;
import com.example.quanlyns.entity.response.UpdateUserResponse;

public interface UserService {

	User createUser(User user);

	ResultPaginationDTO getAllUsers(Specification<User> spec, Pageable pageable);

	Optional<User> getUserById(Long id);

	User updateUser(Long id, User updatedUser);

	void deleteUser(Long id);

	User handleGetUserByUsername(String email);

	CreateUserResponse convertToCreateUserResponse(User user);

	UpdateUserResponse convertUpdateUserResponse(User user);
}
