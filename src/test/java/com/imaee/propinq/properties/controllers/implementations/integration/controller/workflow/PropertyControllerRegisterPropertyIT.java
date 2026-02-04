package com.imaee.propinq.properties.controllers.implementations.integration.controller.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaee.propinq.buildings.builders.UserBuilder;
import com.imaee.propinq.buildings.config.TestImageUploadConfig;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.properties.builders.BuildingEntityBuilder;
import com.imaee.propinq.properties.builders.CreatePropertyRequestBuilder;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PROPINQ-56 / US #02: Registrar vivienda.
 * Verifica la integración de componentes al registrar una property (controlador, servicio, repositorio, mappers).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(TestImageUploadConfig.class)
class PropertyControllerRegisterPropertyIT {

    private static final String BASE_URL = "/api/v1/properties";

    private static final byte[] MINIMAL_PNG = new byte[]{
            (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A,
            0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,
            0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01,
            0x08, 0x06, 0x00, 0x00, 0x00, 0x1F, 0x15, (byte) 0xC4,
            (byte) 0x89, 0x00, 0x00, 0x00, 0x0A, 0x49, 0x44, 0x41,
            0x54, 0x78, (byte) 0x9C, 0x63, 0x00, 0x01, 0x00, 0x00,
            0x05, 0x00, 0x01, 0x0D, 0x0A, 0x2D, (byte) 0xB4, 0x00,
            0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, (byte) 0xAE,
            0x42, 0x60, (byte) 0x82
    };

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IPropertyRepository propertyRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBuildingRepository buildingRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = UserBuilder.aUser().build();
        savedUser = userRepository.saveAndFlush(user);
    }

    private MockMultipartFile propertyPart(CreatePropertyRequestBuilder builder) throws Exception {
        return new MockMultipartFile(
                "property",
                "property",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(builder.build())
        );
    }

    /** One minimal valid image part (API requires "images" part). */
    private MockMultipartFile oneMinimalImage() {
        return new MockMultipartFile("images", "img.png", "image/png", MINIMAL_PNG);
    }

    @Nested
    class Scenario1_RegisterApartmentWithImages {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201AndPersistPropertyWithImages_whenValidApartmentAndImages() throws Exception {
            // Given
            Building building = BuildingEntityBuilder.aBuilding().withUser(savedUser).build();
            building = buildingRepository.saveAndFlush(building);
            var request = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withBuildingId(building.getBuildingId())
                    .withType("APARTAMENTO")
                    .build();
            MockMultipartFile image = new MockMultipartFile("images", "img.png", "image/png", MINIMAL_PNG);

            // When
            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(CreatePropertyRequestBuilder.aCreatePropertyRequest()
                                    .withUserId(savedUser.getUserId())
                                    .withBuildingId(building.getBuildingId())
                                    .withType("APARTAMENTO")))
                            .file(image)
                            .with(csrf()))
                    .andExpect(status().isCreated());

            // Then
            List<Property> properties = propertyRepository.findAll();
            assertThat(properties).hasSize(1);
            assertThat(properties.get(0).getImages()).isNotEmpty();
        }
    }

    @Nested
    class Scenario2_RegisterHouseWithImages {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201AndPersistPropertyWithImages_whenValidHouseNoBuildingAndImages() throws Exception {
            // Given - CASA: no building, floor/number empty
            var request = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withFloor(null)
                    .withNumber("")
                    .withBuildingId(null)
                    .build();
            MockMultipartFile image = new MockMultipartFile("images", "img.png", "image/png", MINIMAL_PNG);

            // When
            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(CreatePropertyRequestBuilder.aCreatePropertyRequest()
                                    .withUserId(savedUser.getUserId())
                                    .withType("CASA")
                                    .withFloor(null)
                                    .withNumber("")
                                    .withBuildingId(null)))
                            .file(image)
                            .with(csrf()))
                    .andExpect(status().isCreated());

            // Then
            List<Property> properties = propertyRepository.findAll();
            assertThat(properties).hasSize(1);
            assertThat(properties.get(0).getImages()).isNotEmpty();
        }
    }

    @Nested
    class Scenario3_RegisterApartmentWithoutImages {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201AndPersistProperty_whenValidApartmentAndMinimalImages() throws Exception {
            // Given - API requires "images" part; send one minimal image
            Building building = BuildingEntityBuilder.aBuilding().withUser(savedUser).build();
            building = buildingRepository.saveAndFlush(building);
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withBuildingId(building.getBuildingId())
                    .withType("APARTAMENTO");

            // When
            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isCreated());

            // Then
            List<Property> properties = propertyRepository.findAll();
            assertThat(properties).hasSize(1);
        }
    }

    @Nested
    class Scenario4_RegisterHouseWithoutImages {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201AndPersistProperty_whenValidHouseAndMinimalImages() throws Exception {
            // Given - API requires "images" part; send one minimal image
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withFloor(null)
                    .withNumber("")
                    .withBuildingId(null);

            // When
            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isCreated());

            // Then
            List<Property> properties = propertyRepository.findAll();
            assertThat(properties).hasSize(1);
        }
    }

    @Nested
    class Scenario5_DescriptionTooLong {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenDescriptionOver125Characters() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withDescription("x".repeat(126));

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La descripción de la vivienda debe tener como máximo 125 caracteres")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario6_NegativePrice {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenPriceNegative() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withPrice(-100_000.0);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El precio de la vivienda no puede ser negativo")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario7_PriceNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenPriceNull() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withPrice(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El precio de la vivienda no debe ser nulo")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario8_NegativeBedrooms {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenBedroomsNegative() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withBedrooms(-2);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La cantidad de habitaciones no puede ser negativa")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario9_BedroomsNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenBedroomsNull() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withBedrooms(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La cantidad de habitaciones no puede ser nula")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario10_NegativeBathrooms {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenBathroomsNegative() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withBathrooms(-2);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La cantidad de baños no puede ser negativa")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario11_BathroomsNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenBathroomsNull() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withBathrooms(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La cantidad de baños no puede ser nula")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario12_PetsAllowedNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenPetsAllowedNull() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withPetsAllowed(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La aceptación de mascotas no puede ser nula")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario13_HasFurnitureNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenHasFurnitureNull() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withHasFurniture(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La aceptación de mascotas no puede ser nula")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario14_PaysExpensesNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenPaysExpensesNull() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withPaysExpenses(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La aceptación de mascotas no puede ser nula")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario15_TypeEmpty {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenTypeEmpty() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("")
                    .withBuildingId(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("tipo de propiedad")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario16_InvalidType {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenTypeNotApartmentNorHouse() throws Exception {
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("HOTEL")
                    .withBuildingId(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El tipo de propiedad debe ser uno de los siguientes: APARTAMENTO, CASA")));
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario17_UserNotFound {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Reliability
        @WithMockUser
        void shouldReturn4xxAndNotPersist_whenUserIdDoesNotExist() throws Exception {
            UUID nonExistentUserId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(nonExistentUserId)
                    .withType("CASA")
                    .withBuildingId(null);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().is4xxClientError());
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario18_BuildingNotFound {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Reliability
        @WithMockUser
        void shouldReturn4xxAndNotPersist_whenBuildingIdDoesNotExist() throws Exception {
            UUID nonExistentBuildingId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            var builder = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withBuildingId(nonExistentBuildingId)
                    .withType("APARTAMENTO");

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(builder))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().is4xxClientError());
            assertThat(propertyRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario19_DuplicateApartmentNumberInBuilding {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Reliability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenSameFloorAndNumberAlreadyExistsInBuilding() throws Exception {
            Building building = BuildingEntityBuilder.aBuilding().withUser(savedUser).build();
            building = buildingRepository.saveAndFlush(building);
            var first = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withBuildingId(building.getBuildingId())
                    .withType("APARTAMENTO")
                    .withFloor(1)
                    .withNumber("2");
            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(first))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isCreated());

            var duplicate = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withBuildingId(building.getBuildingId())
                    .withType("APARTAMENTO")
                    .withFloor(1)
                    .withNumber("2");

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(duplicate))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("Ya existe un apartamento con ese número en ese edificio")));
            assertThat(propertyRepository.findAll()).hasSize(1);
        }
    }

    @Nested
    class Scenario20_DuplicateAddressByCoordinates {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Reliability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenPropertyAlreadyExistsAtSameCoordinates() throws Exception {
            double lat = -32.41;
            double lng = -63.24;
            var first = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withAddress("Catamarca123")
                    .withLatitude(lat)
                    .withLongitude(lng);
            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(first))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isCreated());

            var duplicate = CreatePropertyRequestBuilder.aCreatePropertyRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .withBuildingId(null)
                    .withAddress("Catamarca123")
                    .withLatitude(lat)
                    .withLongitude(lng);

            mockMvc.perform(multipart(BASE_URL)
                            .file(propertyPart(duplicate))
                            .file(oneMinimalImage())
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("Ya existe una vivienda con esa dirección")));
            assertThat(propertyRepository.findAll()).hasSize(1);
        }
    }
}
