package com.ssafy.seas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/seas-config.yml")
public class SeasApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeasApplication.class, args);
	}

}
