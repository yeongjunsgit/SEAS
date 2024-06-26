package com.ssafy.seas.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class ObjectMapperConfig {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		// Register JavaTimeModule for standard Java 8 date/time classes
		objectMapper.registerModule(new JavaTimeModule());

		// Customize serialization for LocalDateTime
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(LocalDateTime.class,
			new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		objectMapper.registerModule(simpleModule);

		return objectMapper;
	}
}
