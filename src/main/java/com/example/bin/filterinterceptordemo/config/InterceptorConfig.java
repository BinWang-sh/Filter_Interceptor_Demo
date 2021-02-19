package com.example.bin.filterinterceptordemo.config;

import com.example.bin.filterinterceptordemo.interceptor.FormerLoginInterceptor;
import com.example.bin.filterinterceptordemo.interceptor.LogInterceptor;
import com.example.bin.filterinterceptordemo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());

        registry.addInterceptor(new FormerLoginInterceptor()).addPathPatterns("/usr/formerLogin");

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/usr/*").excludePathPatterns("/usr/formerLogin");
    }
}