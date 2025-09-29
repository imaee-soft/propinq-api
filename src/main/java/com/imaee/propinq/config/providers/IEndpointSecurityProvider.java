package com.imaee.propinq.config.providers;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public interface IEndpointSecurityProvider {
    void configureEndpointSecurity(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authMatcherRegistry);
}
