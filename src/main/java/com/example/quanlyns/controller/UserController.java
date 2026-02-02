package com.example.quanlyns.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quanlyns.entity.User;
import com.example.quanlyns.entity.dto.ResultPaginationDTO;
import com.example.quanlyns.entity.response.ApiResponse;
import com.example.quanlyns.service.UserService;

import jakarta.validation.Valid;

@RestController
// @CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User created = userService.createUser(user);

		var result = new ApiResponse<User>(HttpStatus.CREATED, "Create", created, null);
		// ApiResponse<User> result1 = new ApiResponse<User>(HttpStatus.CREATED,
		// "Create", created, null);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllUsers(
			@RequestParam(value = "current", defaultValue = "1") String sCurrent,
			@RequestParam(value = "pageSize", defaultValue = "10") String sPageSize) {

		int current = Integer.parseInt(sCurrent);
		int pageSize = Integer.parseInt(sPageSize);
		Pageable pageable = PageRequest.of(current - 1, pageSize);

		var result = new ApiResponse<>(HttpStatus.OK, "getAllUsers", userService.getAllUsers(pageable), null);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
		return userService.getUserById(id).map(user -> {
			var response = new ApiResponse<>(HttpStatus.OK, "getUserById", user, null);
			return ResponseEntity.ok(response);

		}).orElseGet(() -> {
			ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
					"Không tìm thấy user với ID: " + id, null, "USER_NOT_FOUND");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		});
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updated = userService.updateUser(id, user);
		var result = new ApiResponse<User>(HttpStatus.OK, "updated", updated, null);

		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		var result = new ApiResponse<User>(HttpStatus.NO_CONTENT, "deleted", null, null);

		return ResponseEntity.ok(result);
	}
}