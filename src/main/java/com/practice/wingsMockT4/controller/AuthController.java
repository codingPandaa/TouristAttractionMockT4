package com.practice.wingsMockT4.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.wingsMockT4.entity.UserEntity;
import com.practice.wingsMockT4.repo.UserRepo;
import com.practice.wingsMockT4.utility.JwtUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;

	private UserRepo repo;

	private PasswordEncoder passwordEncoder;

	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserEntity user) {

		if (repo.findByUsername(user.getUsername()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "user already present!"));
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		repo.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("msg", "user registered successfully!"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserEntity user) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		String token = jwtUtil.generateToken(user.getUsername());

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("accessToken", token));
	}
}
