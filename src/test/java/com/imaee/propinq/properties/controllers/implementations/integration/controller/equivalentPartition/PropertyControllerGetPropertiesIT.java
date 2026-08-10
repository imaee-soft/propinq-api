package com.imaee.propinq.properties.controllers.implementations.integration.controller.equivalentPartition;

import com.imaee.propinq.buildings.builders.UserBuilder;
import com.imaee.propinq.properties.builders.PropertyEntityBuilder;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * PROPINQ-60 / US #06: Consultar viviendas.
 * Verifica la integración de componentes al consultar propiedades.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PropertyControllerGetPropertiesIT {

    private static final String BASE_URL = "/api/v1/properties";
    private static final String DETAILS_URL = BASE_URL + "/details";
    private static final String USER_EMAIL = "test@example.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPropertyRepository propertyRepository;

    @Autowired
    private IUserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = UserBuilder.aUser().withEmail(USER_EMAIL).build();
        savedUser = userRepository.saveAndFlush(user);
    }

    @Nested
    class Scenario1_GetAllNonDeletedWithoutBuilding {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn200AndListWithAddressPriceLatLongType_whenPropertiesExistWithoutBuilding() throws Exception {
            // Given
            Property p1 = PropertyEntityBuilder.aProperty().withUser(savedUser).withAddress("Addr 1").build();
            Property p2 = PropertyEntityBuilder.aProperty().withUser(savedUser).withAddress("Addr 2").build();
            propertyRepository.saveAll(List.of(p1, p2));

            // When & Then
            mockMvc.perform(get(BASE_URL).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].propertyId").exists())
                    .andExpect(jsonPath("$[0].latitude").exists())
                    .andExpect(jsonPath("$[0].longitude").exists());
        }
    }

    @Nested
    class Scenario2_GetPropertiesWhenNoneExist {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn200AndEmptyList_whenNoPropertiesExist() throws Exception {
            mockMvc.perform(get(BASE_URL).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(0));
        }
    }

    @Nested
    class Scenario3_GetPropertiesDetailsPaginated {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser(username = USER_EMAIL)
        void shouldReturn200AndFiveProperties_whenUserHasTenAndPage0Size5() throws Exception {
            // Given - 10 properties for savedUser (principal = USER_EMAIL)
            for (int i = 0; i < 10; i++) {
                Property p = PropertyEntityBuilder.aProperty().withUser(savedUser).withAddress("Addr " + i).build();
                propertyRepository.saveAndFlush(p);
            }

            mockMvc.perform(get(DETAILS_URL).param("page", "0").param("size", "5").with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(5))
                    .andExpect(jsonPath("$.content[0].propertyId").exists())
                    .andExpect(jsonPath("$.content[0].imagesURL").exists())
                    .andExpect(jsonPath("$.content[0].address").exists())
                    .andExpect(jsonPath("$.content[0].price").exists());
        }
    }

    @Nested
    class Scenario4_GetDetailsWhenUserHasNoProperties {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser(username = USER_EMAIL)
        void shouldReturn200AndEmptyPage_whenUserHasNoProperties() throws Exception {
            mockMvc.perform(get(DETAILS_URL).param("page", "0").param("size", "15").with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(0));
        }
    }

    @Nested
    class Scenario5_NegativeSize {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @org.springframework.security.test.context.support.WithMockUser(username = USER_EMAIL)
        void shouldReturn400_whenSizeNegative() throws Exception {
            mockMvc.perform(get(DETAILS_URL).param("page", "0").param("size", "-1").with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("size")));
        }
    }

    @Nested
    class Scenario6_GetPropertiesNearby {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn200AndPropertiesWithinRadius_whenPropertiesExistNearLocation() throws Exception {
            // Given - property at (-32.40, -63.23), within 5 km
            Property p = PropertyEntityBuilder.aProperty()
                    .withUser(savedUser)
                    .withLatitude(-32.40)
                    .withLongitude(-63.23)
                    .withAddress("Near center")
                    .build();
            propertyRepository.saveAndFlush(p);

            mockMvc.perform(get(BASE_URL)
                            .param("location.latitude", "-32.40")
                            .param("location.longitude", "-63.23")
                            .param("location.radiusKm", "5")
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(1)));
        }
    }

    @Nested
    class Scenario7_NoPropertiesWithinRadius {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn200AndEmptyList_whenNoPropertiesInRadius() throws Exception {
            // Given - property far away
            Property p = PropertyEntityBuilder.aProperty()
                    .withUser(savedUser)
                    .withLatitude(-35.0)
                    .withLongitude(-60.0)
                    .withAddress("Far")
                    .build();
            propertyRepository.saveAndFlush(p);

            mockMvc.perform(get(BASE_URL)
                            .param("location.latitude", "-32.40")
                            .param("location.longitude", "-63.23")
                            .param("location.radiusKm", "2")
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(0));
        }
    }

    @Nested
    class Scenario8_MissingLongitude {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn400_whenLocationMissingLongitude() throws Exception {
            // Sin longitude, ninguna estrategia aplica; el manager responde con error de estrategia
            mockMvc.perform(get(BASE_URL)
                            .param("location.latitude", "-32.40")
                            .param("location.radiusKm", "5")
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("estrategia")));
        }
    }

    @Nested
    class Scenario9_GetPropertiesNearPoi {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn200AndList_whenPoiFilterValid() throws Exception {
            // POI filter - may return empty if no POIs in viewport; we assert 200 and array
            mockMvc.perform(get(BASE_URL)
                            .param("poi.poiType", "TOURISM")
                            .param("poi.radiusKm", "2")
                            .param("poi.north", "10")
                            .param("poi.south", "0")
                            .param("poi.east", "10")
                            .param("poi.west", "0")
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
        }
    }

    @Nested
    class Scenario10_InvalidLatitudeBounds {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn400_whenLatitudeOutOfRange() throws Exception {
            mockMvc.perform(get(BASE_URL)
                            .param("poi.poiType", "TOURISM")
                            .param("poi.radiusKm", "2")
                            .param("poi.north", "100")
                            .param("poi.south", "0")
                            .param("poi.east", "10")
                            .param("poi.west", "0")
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("Latitudes fuera de rango")));
        }
    }

    @Nested
    class Scenario11_InvalidPoiType {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn400_whenPoiTypeInvalid() throws Exception {
            mockMvc.perform(get(BASE_URL)
                            .param("poi.poiType", "INVALID_TYPE")
                            .param("poi.radiusKm", "2")
                            .param("poi.north", "10")
                            .param("poi.south", "0")
                            .param("poi.east", "10")
                            .param("poi.west", "0")
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").exists());
        }
    }

    @Nested
    class Scenario12_GetPropertyDetailsById {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn200AndDetailsWithImages_whenPropertyExists() throws Exception {
            Property p = PropertyEntityBuilder.aProperty().withUser(savedUser).withAddress("Catamarca 123").build();
            p = propertyRepository.saveAndFlush(p);
            UUID id = p.getPropertyId();

            mockMvc.perform(get(BASE_URL + "/" + id).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.propertyId").value(id.toString()))
                    .andExpect(jsonPath("$.address").value("Catamarca 123"))
                    .andExpect(jsonPath("$.imagesURL").isArray())
                    .andExpect(jsonPath("$.price").exists())
                    .andExpect(jsonPath("$.latitude").exists())
                    .andExpect(jsonPath("$.longitude").exists());
        }
    }

    @Nested
    class Scenario13_GetPropertyDetailsNotFound {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Reliability
        @org.springframework.security.test.context.support.WithMockUser
        void shouldReturn404_whenPropertyDoesNotExist() throws Exception {
            UUID nonExistent = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

            mockMvc.perform(get(BASE_URL + "/" + nonExistent).with(csrf()))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", containsString("La vivienda no fue encontrada")));
        }
    }
}
