package com.forpawchain.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.forpawchain.auth.JwtAuthenticationFilter;
import com.forpawchain.auth.JwtEntryPoint;
import com.forpawchain.auth.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtEntryPoint jwtEntryPoint;

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
			.antMatchers("/v2/api-docs", "/swagger*/**").permitAll()
			.antMatchers("/user/").permitAll()
			.antMatchers("/user/regist").permitAll()
			.antMatchers("/user/login").permitAll()
			.antMatchers("/user/reissue").permitAll()
			.antMatchers("/file/**").permitAll()
			.anyRequest().authenticated()
			// .antMatchers("*").permitAll()
			.and() // 에러핸들링
			.exceptionHandling()
			.authenticationEntryPoint(jwtEntryPoint)
			.and() // JwtTokenProvider 필터 추가
 			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new DelegatingPasswordEncoder("bcrypt",
			Collections.singletonMap("bcrypt", new BCryptPasswordEncoder()));
	}
}