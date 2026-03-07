package com.example.quanlyns.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.quanlyns.entity.User;
import com.example.quanlyns.entity.dto.Meta;
import com.example.quanlyns.entity.dto.ResultPaginationDTO;
import com.example.quanlyns.entity.response.CreateUserResponse;
import com.example.quanlyns.entity.response.UpdateUserResponse;
import com.example.quanlyns.repository.UserRepository;
import com.example.quanlyns.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		return this.userRepository.save(user);
	}

	@Override
	public ResultPaginationDTO getAllUsers(Specification<User> spec, Pageable pageable) {
		Page<User> pageUser = this.userRepository.findAll(spec, pageable);

		ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
		Meta meta = new Meta();

		meta.setPage(pageable.getPageNumber() + 1);
		meta.setSizePage(pageable.getPageSize());
		meta.setPages(pageUser.getTotalPages());
		meta.setTotal(pageUser.getTotalElements());

		resultPaginationDTO.setMeta(meta);
		resultPaginationDTO.setResult(pageUser.getContent());

		return resultPaginationDTO;
	}

	@Override
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User updateUser(Long id, User updatedUser) {

		User user = userRepository.findById(id).orElse(null);

		if (user == null) {
			throw new NoSuchElementException("User not found");
		}

		if (userRepository.existsByEmail(updatedUser.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}

		user.setName(updatedUser.getName());
		user.setEmail(updatedUser.getEmail());
		user.setAddress(updatedUser.getAddress());
		user.setAge(updatedUser.getAge());
		user.setGender(updatedUser.getGender());

		// User user2 = this.userRepository.save(user);

		// UpdateUserResponse updateUserResponse = new UpdateUserResponse(id,
		// user2.getName(), user2.getEmail(),
		// user2.getAge(), user2.getGender(), user2.getAddress(), user2.getUpdatedAt(),
		// user2.getUpdatedBy());

		return this.userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new NoSuchElementException("User not found");
		}
		userRepository.deleteById(id);
	}

	@Override
	public User handleGetUserByUsername(String username) {
		return userRepository.findByEmail(username);
	}

	@Override
	public CreateUserResponse convertToCreateUserResponse(User user) {
		return new CreateUserResponse(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getGender(),
				user.getAddress(), user.getCreatedAt(), user.getCreatedBy());
	}

	@Override
	public UpdateUserResponse convertUpdateUserResponse(User user) {
		return new UpdateUserResponse(user.getId(), user.getName(), user.getEmail(), user.getAge(),
				user.getGender(), user.getAddress(), user.getUpdatedAt(), user.getUpdatedBy());
	}
}