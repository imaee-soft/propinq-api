package com.imaee.propinq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Permitir acceso público a Swagger UI y OpenAPI docs
                .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                // Permitir acceso a recursos estáticos
                .requestMatchers("/webjars/**", "/swagger-resources/**").permitAll()
                // Permitir acceso público a endpoints de autenticación
                .requestMatchers("/auth/**").permitAll()
                // Permitir acceso público a endpoints de usuarios (activación, recuperación, etc.)
                .requestMatchers("/api/v1/users/**").permitAll()
                // Todas las demás rutas requieren autenticación
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}