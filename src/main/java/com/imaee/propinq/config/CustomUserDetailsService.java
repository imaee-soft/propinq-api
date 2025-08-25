package com.imaee.propinq.config;

import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserService userService;
    
    public CustomUserDetailsService(@Lazy IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.findUserByEmail(email);
            
            if (!user.getActivated()) {
                throw new UsernameNotFoundException("User account is not activated");
            }
            
            // Crear autoridades basadas en el rol del usuario
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
            );
            
            // Crear UserDetails usando Spring Security User builder
            var springUserBuilder = org.springframework.security.core.userdetails.User.builder();
            
            return springUserBuilder
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.getActivated())
                .build();
                
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with email: " + email, e);
        }
    }
}
