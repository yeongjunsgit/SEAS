package com.ssafy.seas.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.seas.quiz.dto.QuizDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.util.Map;


@Configuration
public class RedisConfig {

    @Value("${spring.redis.data.host}")
    private String host;

    @Value("${spring.redis.data.port}")
    private int port;

    private final RedisTemplate<Integer, Map<Integer, QuizDto.QuizFactorDto>> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisConfig(RedisTemplate<Integer, Map<Integer, QuizDto.QuizFactorDto>> redisTemplate, ObjectMapper objectMapper) {
            this.redisTemplate = redisTemplate;
            this.objectMapper = objectMapper;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    public void saveMap(Integer key, Map<Integer, Map<Integer, ?>> map) throws JsonProcessingException {
        // 내부 맵을 JSON으로 직렬
        QuizDto.QuizFactorDto json = objectMapper.writeValueAsString(map, Object);
        // 레디스에 저장
        redisTemplate.opsForValue().set(key, json);
    }
}
