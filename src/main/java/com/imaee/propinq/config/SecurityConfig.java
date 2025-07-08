package com.imaee.propinq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import static com.imaee.propinq.config.utils.Endpoints.AUTH_ENDPOINTS;
import static com.imaee.propinq.config.utils.Endpoints.DOC_ENDPOINTS;

import com.imaee.propinq.users.data.enums.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/v1/auth/signup").permitAll()
                    .requestMatchers("/api/v1/buildings/**").permitAll()
                    .requestMatchers("/api/v1/users/*/activate").permitAll()
                .requestMatchers(DOC_ENDPOINTS).hasRole(Role.ADMIN.name())
                .requestMatchers(AUTH_ENDPOINTS).permitAll()
                .requestMatchers( "/api/v1/users/**").permitAll()
                    .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> {})
            .sessionManagement(sesion -> sesion
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }

    // public AuthenticationSuccessHandler successHandler(){
    //     return ((request, response, authentication) -> {
    //         response.sendRedirect("/propinq");// Url a donde se va a ir luego de iniciar sesión
    //     });
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());    
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        
        UserDetails userDetails = User.withUsername("nacho")
            .password(passwordEncoder().encode("1234")) // Encriptar la contraseña
            .roles("ADMIN")
            .authorities("READ", "CREATE")
            .build();
        
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}