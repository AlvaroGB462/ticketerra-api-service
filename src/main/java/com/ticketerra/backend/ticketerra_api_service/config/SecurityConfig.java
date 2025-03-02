package com.ticketerra.backend.ticketerra_api_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean // Define un bean que será gestionado por Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF (Cross-Site Request Forgery)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/usuarios/**").permitAll() // Permite acceso público a todas las rutas bajo /api/usuarios
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra ruta
            );
        return http.build(); // Construye y devuelve la cadena de filtros de seguridad
    }
}