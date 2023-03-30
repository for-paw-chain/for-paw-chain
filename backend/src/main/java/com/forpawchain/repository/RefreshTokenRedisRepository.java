package com.forpawchain.repository;

import org.springframework.data.repository.CrudRepository;

import com.forpawchain.domain.Entity.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
