package com.example.ebankingbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Active la configuration CORS définie ailleurs (dans `WebMvcConfigurer`)
                .and()
                .csrf().disable() // Désactive CSRF pour permettre les requêtes POST sans token CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permet toutes les requêtes (sécuriser après pour la prod)
                );

        return http.build();
    }
}