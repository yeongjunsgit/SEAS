package com.ssafy.seas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@PropertySource("classpath:/seas-config.yml")
@OpenAPIDefinition(servers = {@Server(url = "https://i10a609.p.ssafy.com/api", description="원격 백엔드 서버입니다.")})
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
@EnableCaching
public class SeasApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeasApplication.class, args);
	}
}
