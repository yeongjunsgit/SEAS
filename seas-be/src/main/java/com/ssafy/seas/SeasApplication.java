package com.ssafy.seas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/seas-config.yml")
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
public class SeasApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeasApplication.class, args);
	}

}
