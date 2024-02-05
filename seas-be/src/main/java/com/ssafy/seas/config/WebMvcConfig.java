package com.ssafy.seas.config;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
	private final String host = "https://i10a609.p.ssafy.io";
	private final String localhost = "http://localhost:";
	private final String secureLocalhost = "https://localhost:";
	private final int defaultPort = 80;
	private final int allowedMinPort = 5173;
	private final int allowedMaxPort = 5175;
	private List<String> allowedOrigins = new ArrayList<>();

	// CORS
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 허용할 origin 목록
		int allowedPort = allowedMinPort;
		allowedOrigins.add(host);
		allowedOrigins.add(localhost + defaultPort);
		allowedOrigins.add(secureLocalhost + defaultPort);
		while (allowedPort <= allowedMaxPort) {
			allowedOrigins.add(localhost + allowedPort);
			allowedPort += 1;
		}

		log.info(allowedOrigins.toString());



		registry.addMapping("/**")
			.allowedOrigins(allowedOrigins.toArray(new String[allowedOrigins.size()]))
			.allowedMethods(
				HttpMethod.OPTIONS.name(),
				HttpMethod.GET.name(),
				HttpMethod.HEAD.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.PATCH.name(),
				HttpMethod.DELETE.name())
			.maxAge(3000); // pre-flight 리퀘스트를 캐싱
	}
}
