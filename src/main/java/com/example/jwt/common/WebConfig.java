package com.example.jwt.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final JwtAuthenticationIntercepter jwtAuthenticationIntercepter;
	
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:3000")
		.allowedMethods("GET","PUT","POST","DELETE");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtAuthenticationIntercepter);
	}

}
