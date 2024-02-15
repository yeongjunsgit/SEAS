package com.ssafy.seas.member.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenUtil {
	private final RedisTemplate<String, String> redisTemplate;
	private final MemberUtil memberUtil;

	public void setRefreshToken(String refreshToken) {
		redisTemplate.opsForValue().set("token" + memberUtil.getLoginMemberId(), refreshToken);
	}

	public String getRefreshToken() {
		log.info("Redis에 저장되어 있는 토큰 ::::::::::::::: {}", redisTemplate.opsForValue().get("token" + memberUtil.getLoginMemberId()));
		return redisTemplate.opsForValue().get("token" + memberUtil.getLoginMemberId());
	}

	public boolean deleteRefreshToken() {
		return redisTemplate.delete("token" + memberUtil.getLoginMemberId());
	}

	public boolean checkRefreshTokenEquals(String refreshToken) {
		return refreshToken.equals(getRefreshToken());
	}
}
