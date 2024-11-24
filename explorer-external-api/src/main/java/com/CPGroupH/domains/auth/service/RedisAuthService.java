package com.CPGroupH.domains.auth.security.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisAuthService {
    private final StringRedisTemplate stringRedisTemplate;

    public void setRefreshToken(Long memberId, String value, Long expireTime) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = generateRefreshTokenKey(memberId);
        ops.set(key, value, Duration.ofSeconds(expireTime));
    }

    public String getRefreshToken(Long memberId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = generateRefreshTokenKey(memberId);
        return ops.get(key);
    }

    public void deleteRefreshToken(Long memberId) {
        String key = generateRefreshTokenKey(memberId);
        stringRedisTemplate.delete(key);
    }

    public Boolean isTokenInBlackList(String token) {
        String key = generateBlackedTokenKey(token);
        return stringRedisTemplate.hasKey(key);
    }

    private String generateRefreshTokenKey(Long memberId) {
        return "auth:refresh:" + memberId;
    }

    private String generateBlackedTokenKey(String accessToken) {
        return "auth:blacked:" + accessToken;
    }
}

