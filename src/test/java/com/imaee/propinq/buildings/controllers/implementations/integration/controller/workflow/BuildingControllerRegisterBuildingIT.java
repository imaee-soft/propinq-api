package com.imaee.propinq.buildings.controllers.implementations.integration.controller.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaee.propinq.buildings.builders.CreateBuildingRequestBuilder;
import com.imaee.propinq.buildings.config.TestImageUploadConfig;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.builders.UserBuilder;
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

import static com.imaee.propinq.shared.Constants.MAXIMUM_FILE_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PROPINQ-55 / US #1: Registrar inmuebles.
 * Verifica la integración de componentes al registrar un building (controlador, servicio, repositorio, mappers).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(TestImageUploadConfig.class)
class BuildingControllerRegisterBuildingIT {

    private static final String BASE_URL = "/api/v1/buildings";

    // Minimal valid 1x1 PNG (67 bytes)
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
    private IBuildingRepository buildingRepository;

    @Autowired
    private IUserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = UserBuilder.aUser().build();
        savedUser = userRepository.saveAndFlush(user);
    }

    @Nested
    class Scenario1_RegisterBuildingWithImages {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201AndPersistBuildingWithImages_whenValidRequestAndTwoImages() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );
            MockMultipartFile image1 = new MockMultipartFile(
                    "images",
                    "img1.png",
                    "image/png",
                    MINIMAL_PNG
            );
            MockMultipartFile image2 = new MockMultipartFile(
                    "images",
                    "img2.png",
                    "image/png",
                    MINIMAL_PNG
            );

            // When
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .file(image1)
                            .file(image2)
                            .with(csrf()))
                    .andExpect(status().isCreated());

            // Then
            List<Building> buildings = buildingRepository.findAll();
            assertThat(buildings).hasSize(1);
            assertThat(buildings.get(0).getName()).isEqualTo("Torre Villa María");
            assertThat(buildings.get(0).getImages()).hasSize(2);
        }
    }

    @Nested
    class Scenario2_RegisterBuildingWithoutImages {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201AndPersistBuilding_whenValidRequestAndNoImages() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isCreated());

            // Then
            List<Building> buildings = buildingRepository.findAll();
            assertThat(buildings).hasSize(1);
            assertThat(buildings.get(0).getImages()).isEmpty();
        }
    }

    @Nested
    class Scenario3_EmptyName {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenNameEmpty() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withName("")
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El nombre del edificio no debe estar vacío")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario4_NameTooShort {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenNameFourCharactersOrLess() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withName("EDIF")
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El nombre del edificio debe tener al menos 5 caracteres")));
            assertThat(buildingRepository.findAll()).isEmpty();
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
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withDescription("x".repeat(126))
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La descripción del edificio debe tener como máximo 125 caracteres")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario6_EmptyDescriptionAllowed {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Limit Values
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201AndPersist_whenDescriptionEmpty() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withDescription("")
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isCreated());

            // Then
            List<Building> buildings = buildingRepository.findAll();
            assertThat(buildings).hasSize(1);
            assertThat(buildings.get(0).getDescription()).isEmpty();
        }
    }

    @Nested
    class Scenario7_EmptyAddress {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenAddressEmpty() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withAddress("")
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La dirección del edificio no debe estar vacía")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario8_AddressTooShort {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenAddressFourCharactersOrLess() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withAddress("XXXX")
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La dirección del edificio debe tener al menos 5 caracteres")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario9_LatitudeNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenLatitudeNull() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withLatitude(null)
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La latitud del edificio no debe ser nula")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario10_LongitudeNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenLongitudeNull() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withLongitude(null)
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("La longitud del edificio no debe ser nula")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario11_UserIdNull {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenUserIdNull() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(null)
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El ID del usuario no debe ser nulo")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario12_TypeEmpty {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenTypeEmpty() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("")
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El tipo de edificio no debe estar vacío")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario13_InvalidType {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenTypeNotEdificioNorComplejo() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withType("CASA")
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El tipo del edificio debe ser uno de los siguientes: EDIFICIO, COMPLEJO")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario14_ImageExceedsMaxSize {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenImageExceeds5MB() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );
            byte[] oversized = new byte[(int) (MAXIMUM_FILE_SIZE + 1)];
            MockMultipartFile bigImage = new MockMultipartFile(
                    "images",
                    "big.png",
                    "image/png",
                    oversized
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .file(bigImage)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("Las imágenes no pueden superar los 5MB")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario15_InvalidImageFormat {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenImageFormatInvalid() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .build();
            MockMultipartFile buildingPart = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(request)
            );
            MockMultipartFile pdfFile = new MockMultipartFile(
                    "images",
                    "doc.pdf",
                    "application/pdf",
                    new byte[]{1, 2, 3}
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPart)
                            .file(pdfFile)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("Las imágenes deben estar en formato jpg, jepg, png o webp")));
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Scenario16_DuplicateName {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller / Workflow
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Reliability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenNameAlreadyExists() throws Exception {
            // Given - persist existing building with same name
            var existingRequest = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withName("Torre Villa María")
                    .build();
            MockMultipartFile buildingPartFirst = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(existingRequest)
            );
            mockMvc.perform(multipart(BASE_URL).file(buildingPartFirst).with(csrf()))
                    .andExpect(status().isCreated());

            var duplicateRequest = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .withName("Torre Villa María")
                    .build();
            MockMultipartFile buildingPartDuplicate = new MockMultipartFile(
                    "building",
                    "building",
                    MediaType.APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsBytes(duplicateRequest)
            );

            // When & Then
            mockMvc.perform(multipart(BASE_URL)
                            .file(buildingPartDuplicate)
                            .with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("El nombre del edificio ya está registrado")));
            assertThat(buildingRepository.findAll()).hasSize(1);
        }
    }

    @Nested
    class Scenario17_JsonWithoutMultipart {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400AndNotPersist_whenPostJsonWithoutMultipart() throws Exception {
            // Given
            var request = CreateBuildingRequestBuilder.aCreateBuildingRequest()
                    .withUserId(savedUser.getUserId())
                    .build();
            String json = objectMapper.writeValueAsString(request);

            // When & Then - without multipart, Spring returns 400 (e.g. required part 'building' not present)
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
            assertThat(buildingRepository.findAll()).isEmpty();
        }
    }
}
