package com.forpawchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.forpawchain.auth.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;

	// security 기본 설정
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable() // basic auth 미사용
			.csrf().disable() // csrf 보안 미사용
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 미사용
			.and()
			.authorizeRequests() // 요청에 대한 권한 체크
			// .antMatchers("/user/login").permitAll() // 로그인 페이지 모두에게 허용
			// .anyRequest().authenticated() // 그 외 페이지 유저만 허용
			.antMatchers("*").permitAll();
		// .and()
		// .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	// Bycrypt encoder 사용
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}