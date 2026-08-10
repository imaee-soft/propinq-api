package com.imaee.propinq.config.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EndpointsRoutingTest {

    @Test
    void apiPrefix_shouldBeApiV1() {
        assertThat(Endpoints.API).isEqualTo("/api/v1");
    }

    @Test
    void parametersEndpoint_shouldBeUnderApiV1() {
        assertThat(Endpoints.PARAMETERS_ENDPOINTS).isEqualTo("/api/v1/parameters/**");
    }

    @Test
    void authEndpoint_shouldBeUnderApiV1() {
        assertThat(Endpoints.AUTH_ENDPOINTS).isEqualTo("/api/v1/auth/**");
    }

    @Test
    void retrieveEndpoints_shouldExposePublicCatalogUnderApiV1() {
        assertThat(Endpoints.RETRIEVE_ENDPOINTS).contains(
                "/api/v1/buildings/**",
                "/api/v1/properties/**",
                "/api/v1/provinces/**"
        );
    }

    @Test
    void recoverPasswordEndpoints_shouldBePublic() {
        assertThat(Endpoints.USER_ACTIVATION_ENDPOINTS).contains(
                "/api/v1/users/recover-password/send-email",
                "/api/v1/users/recover-password"
        );
    }
}
