package com.uploadImage.yolov5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Điều chỉnh đường dẫn theo nhu cầu của bạn
                .allowedOrigins("http://localhost:3000") // Nguồn của ứng dụng React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
