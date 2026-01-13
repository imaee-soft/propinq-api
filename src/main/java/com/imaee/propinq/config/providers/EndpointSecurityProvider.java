package com.imaee.propinq.config.providers;

import com.imaee.propinq.users.data.enums.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import static com.imaee.propinq.config.utils.Endpoints.*;
import static org.springframework.http.HttpMethod.*;

@Component
public class EndpointSecurityProvider implements IEndpointSecurityProvider {

    @Override
    public void configureEndpointSecurity(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authMatcherRegistry) {
        authMatcherRegistry
                .requestMatchers(OPTIONS).permitAll()
                .requestMatchers(AUTH_ENDPOINTS).permitAll()
                .requestMatchers(DOC_ENDPOINTS).hasRole(Role.ADMIN.name())
                .requestMatchers(GET, RETRIEVE_ENDPOINTS).permitAll()
                .requestMatchers(POST, USER_ACTIVATION_ENDPOINTS).permitAll()
                .anyRequest().authenticated();
    }
}