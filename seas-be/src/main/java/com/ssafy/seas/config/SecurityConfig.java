package com.ssafy.seas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.seas.member.jwt.JwtFilter;
import com.ssafy.seas.member.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	private final AuthenticationConfiguration authenticationConfiguration;
	private final TokenProvider tokenProvider;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(CorsConfigurer::disable) // WebMvcConfig에 있는 cors 설정 사용
			// .csrf(CsrfConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable) //csrf 비활성
			.httpBasic(AbstractHttpConfigurer::disable) //HTTP 기본인증 비활성
			// 시큐리티가 세션을 만들지도 사용하지도 않음.
			.sessionManagement((sessionManagement) ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authorizeHttpRequests(r ->
				// r.requestMatchers("/auth/**").authenticated()
					// .requestMatchers("/**").permitAll()
					// r.anyRequest().permitAll()
				r.requestMatchers("/auth/**").permitAll()
					.requestMatchers("/**").authenticated()
			)



			// filter 등록을 이렇게 해줘야 JwtFilter를 통해 Token을 먼저 검사한다.
			// .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
			// .addFilter(new JwtFilter(tokenProvider))

			.addFilterAfter(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)

		// .addFilter(JWTfilter)
		// JwtFilter
		// JwtProvider
		// 401, 403 Exception
		// doFilter
		;
		return http.build();
	}

	// ----------------------

	// @Bean
	// public AuthenticationFilter getAuthenticationFilter() throws Exception{
	// 	AuthenticationFilterCustom authenticationFilter = new AuthenticationFilterCustom();
	// 	authenticationFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
	//
	// 	return authenticationFilter;
	// }
	//
	// @Bean
	// public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	// 	return authenticationConfiguration.getAuthenticationManager();
	// }
}