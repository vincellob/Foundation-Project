package com.revature.training.ticketing_reimbursement_system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS to all endpoints
            .allowedOrigins("http://localhost:3000") // Allow requests from React
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these methods
            .allowCredentials(true);
    }
}