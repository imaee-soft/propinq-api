package com.imaee.propinq.config.Filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.imaee.propinq.config.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

public class JwtTokenValidator extends OncePerRequestFilter{

    private JwtUtils jwtUtils; 

    public JwtTokenValidator(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
                                    @NotNull HttpServletRequest request, 
                                    @NotNull HttpServletResponse response, 
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwtToken != null && jwtToken.startsWith("Bearer ")){
            jwtToken = jwtToken.substring(7);

            try {
                DecodedJWT decodedJwt = jwtUtils.validatetoken(jwtToken);

                String username = jwtUtils.extractUserName(decodedJwt); 
                String stringAutorities = jwtUtils.getSpecificClaim(decodedJwt, "authorities").asString();

                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAutorities);

                SecurityContext context = SecurityContextHolder.getContext();

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities); 

                context.setAuthentication(authentication);
                
            } catch (Exception e) {
                // Token inválido, limpiar contexto de seguridad
                SecurityContextHolder.clearContext();
            }
        }    

        filterChain.doFilter(request, response);
    }
}
