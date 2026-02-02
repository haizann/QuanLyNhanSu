package com.example.quanlyns.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quanlyns.entity.User;
import com.example.quanlyns.entity.dto.Meta;
import com.example.quanlyns.entity.dto.ResultPaginationDTO;
import com.example.quanlyns.repository.UserRepository;
import com.example.quanlyns.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final com.example.quanlyns.repository.UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		return userRepository.save(user);
	}

	public ResultPaginationDTO getAllUsers(Pageable pageable) {
		Page<User> pageUser = this.userRepository.findAll(pageable);
		ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
		Meta meta = new Meta();

		meta.setPage(pageUser.getNumber());
		meta.setSizePage(pageUser.getSize());
		meta.setPages(pageUser.getTotalPages());
		meta.setTotal(pageUser.getTotalElements());

		resultPaginationDTO.setMeta(meta);
		resultPaginationDTO.setResult(pageUser.getContent());

		return resultPaginationDTO;
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User updateUser(Long id, User updatedUser) {
		return userRepository.findById(id).map(user -> {
			user.setName(updatedUser.getName());
			user.setEmail(updatedUser.getEmail());
			return userRepository.save(user);
		}).orElseThrow(() -> new NoSuchElementException("User not found"));
	}

	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new NoSuchElementException("User not found");
		}
		userRepository.deleteById(id);
	}

	public User handleGetUserByUsername(String username) {
		return userRepository.findByEmail(username);
	}
}