package com.springboot.user_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Cho phép client gửi cookie/authorization header
        config.setAllowCredentials(true);

        // Nếu muốn chỉ cho React app truy cập, thay bằng:
        config.addAllowedOrigin("http://localhost:3000");

        // Cho phép mọi header
        config.addAllowedHeader("*");

        // Cho phép mọi method GET, POST, PUT, DELETE, OPTIONS…
        config.addAllowedMethod("*");

        // Đăng ký CORS cho tất cả đường dẫn
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
