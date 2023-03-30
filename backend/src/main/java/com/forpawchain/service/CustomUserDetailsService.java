package com.forpawchain.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	// @Cacheable(value = CacheKey.USER, key = "#username", unless = "#result == null")
	public UserDetails loadUserByUsername(String id) {
		return userRepository.findById(id)
			.map(this::createUserDetails)
			.orElseThrow(() -> new BaseException(ErrorMessage.USER_NOT_FOUND));
	}

	private UserDetails createUserDetails(UserEntity user) {
		return User.builder()
			.username(user.getUsername())
			.password(user.getPassword())
			.roles(user.getRoles().toArray(new String[0]))
			.build();
	}
}
