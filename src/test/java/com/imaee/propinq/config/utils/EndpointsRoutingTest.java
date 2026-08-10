package com.imaee.propinq.config.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EndpointsRoutingTest {

    @Test
    // Test type: UNIT
    // Layer: config
    // Quality Attribute: Compatibility
    // Testing Technique: Equivalent Partition
    void parametersEndpoint_shouldBeUnderApiV1() {
        assertThat(Endpoints.PARAMETERS_ENDPOINTS).isEqualTo("/api/v1/parameters/**");
    }

    @Test
    // Test type: UNIT
    // Layer: config
    // Quality Attribute: Compatibility
    // Testing Technique: Equivalent Partition
    void retrieveEndpoints_shouldExposePublicCatalogUnderApiV1() {
        assertThat(Endpoints.RETRIEVE_ENDPOINTS).contains(
                "/api/v1/buildings/**",
                "/api/v1/properties/**",
                "/api/v1/provinces/**"
        );
    }

    @Test
    // Test type: UNIT
    // Layer: config
    // Quality Attribute: Compatibility
    // Testing Technique: Equivalent Partition
    void recoverPasswordEndpoints_shouldBePublic() {
        assertThat(Endpoints.USER_ACTIVATION_ENDPOINTS).contains(
                "/api/v1/users/recover-password/send-email",
                "/api/v1/users/recover-password"
        );
    }
}
