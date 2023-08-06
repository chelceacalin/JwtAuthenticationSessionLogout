package com.auth.authRouting.config;

import com.auth.authRouting.service.interceptor.JwtTokenBlacklistFilter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @NonNull
    private JwtTokenBlacklistFilter jwtTokenBlacklistInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenBlacklistInterceptor);
    }
}