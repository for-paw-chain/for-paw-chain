package com.forpawchain.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.forpawchain.auth.JwtAuthenticationFilter;
import com.forpawchain.auth.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// security 기본 설정
		http
			// basic auth, csrf 보안, session 미사용
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()// 권한 체크
			.authorizeRequests()
			.antMatchers("/user/").permitAll()
			.antMatchers("/user/login").permitAll()
			.anyRequest().authenticated()
			// .antMatchers("*").permitAll()
			.and() // JwtTokenProvider 필터 추가
 			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	// Bycrypt encoder 사용
	// 	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	// }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new DelegatingPasswordEncoder("bcrypt",
			Collections.singletonMap("bcrypt", new BCryptPasswordEncoder()));
	}
}