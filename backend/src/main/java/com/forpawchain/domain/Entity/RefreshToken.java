package com.forpawchain.domain.Entity;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RedisHash("refreshToken")
@AllArgsConstructor
public class RefreshToken {
	@Id
	private String id;

	private String refreshToken;

	@TimeToLive
	private Long expiration;
}