package com.atguigu.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration

public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        //判断跨域
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    //是否允许带
    corsConfiguration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource corsConfigurationSource=new UrlBasedCorsConfigurationSource();
    corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
     return new CorsWebFilter(corsConfigurationSource);
    }
   
}

