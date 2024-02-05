package com.ssafy.seas.config;

import static org.springframework.security.config.Customizer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
// @Slf4j
public class SecurityConfig {
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173/", "http://localhost:5173"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// @Bean
	// public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
	// 	httpSecurity
	// 		.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
	// 		.authorizeHttpRequests(request ->
	// 			request
	// 				.requestMatchers("/**").permitAll()
	// 		);
	//
	// 	return httpSecurity.build();
	// }


	// @Bean
	// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	// 	http
	// 		// if Spring MVC is on classpath and no CorsConfigurationSource is provided,
	// 		// Spring Security will use CORS configuration provided to Spring MVC
	// 		// .cors(withDefaults())
	// 		.cors((corsConfigurer) -> corsConfigurer.configurationSource(corsConfigurationSource()));
	// 		// .authorizeHttpRequests(request ->
	// 		// 	request
	// 		// 		.requestMatchers("/**").permitAll()
	// 		// );
	// 	return http.build();
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// if Spring MVC is on classpath and no CorsConfigurationSource is provided,
			// Spring Security will use CORS configuration provided to Spring MVC
			.cors(CorsConfigurer::disable);

			// .cors(Customizer.withDefaults());
			// .cors((corsConfigurer) -> corsConfigurer.configurationSource(corsConfigurationSource()));
			// .authorizeHttpRequests(request ->
			// 	request
			// 		.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
			// 		.requestMatchers("/**").permitAll()
			// );
		return http.build();
	}

}