package com.ticketerra.backend.ticketerra_api_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests(auth -> auth
    		    .requestMatchers("/api/usuarios/**").permitAll()
    		    .anyRequest().authenticated()
    		)
            .formLogin().disable();
    	http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
