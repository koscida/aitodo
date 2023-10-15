package com.example.aitodo.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// @CrossOrigin("http://localhost:3000")
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				// .allowedHeaders("header1", "header2", "header3")
				// .exposedHeaders("header1", "header2")
				.allowCredentials(false).maxAge(3600);
	}
}
