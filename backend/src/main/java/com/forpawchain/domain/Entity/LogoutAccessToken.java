package com.forpawchain.domain.Entity;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RedisHash("logoutAccessToken")
@AllArgsConstructor
public class LogoutAccessToken {
	@Id
	private String id;

	private String username;

	@TimeToLive
	private Long expiration;

	public LogoutAccessToken of(String accessToken, String username, Long remainingMilliSeconds) {
		return LogoutAccessToken.builder()
			.id(accessToken)
			.username(username)
			.expiration(remainingMilliSeconds / 1000)
			.build();
	}
}