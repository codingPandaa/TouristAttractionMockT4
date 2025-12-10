package com.practice.wingsMockT4.utility;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.wingsMockT4.entity.UserEntity;
import com.practice.wingsMockT4.repo.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = repo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found" + username));

		UserDetails userDetails = User.withUsername(userEntity.getUsername()).password(userEntity.getPassword())
				.roles(userEntity.getRole()).build();

		return userDetails;
	}

}
