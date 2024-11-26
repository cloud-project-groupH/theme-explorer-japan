package com.CPGroupH.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .allowedOrigins(
                        "http://localhost:8080",
                        "http://localhost:3000",
                        "http://43.200.175.236:8080",
                        "http://43.200.175.236:3000"
                );
    }
}
