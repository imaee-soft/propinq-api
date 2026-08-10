package com.imaee.propinq.neighborhoods.controllers.implementations.integration.controller.limitValues;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaee.propinq.neighborhoods.builders.NeighborhoodRequestBuilder;
import com.imaee.propinq.neighborhoods.controllers.implementations.NeighborhoodController;
import com.imaee.propinq.neighborhoods.services.interfaces.INeighborhoodService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NeighborhoodController.class)
class NeighborhoodControllerLimitValuesIT {

    private static final UUID FIXED_NEIGHBORHOOD_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final String BASE_URL = "/api/v1/neighborhoods";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private INeighborhoodService neighborhoodService;

    @Nested
    class CreateNeighborhood_NeighborhoodRequestValidation {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn201_whenNameHasMinLength() throws Exception {
            // Given - name exactly 2 chars (min boundary)
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Ab")
                    .build();
            doNothing().when(neighborhoodService).createNeighborhood(any());

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
            verify(neighborhoodService, times(1)).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenNameBelowMinLength() throws Exception {
            // Given - name 1 char (below min)
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("A")
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn201_whenNameHasMaxLength() throws Exception {
            // Given - name exactly 64 chars (max boundary)
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("A".repeat(64))
                    .build();
            doNothing().when(neighborhoodService).createNeighborhood(any());

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
            verify(neighborhoodService, times(1)).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenNameAboveMaxLength() throws Exception {
            // Given - name 65 chars (above max)
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("A".repeat(65))
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn201_whenNameMatchesPattern() throws Exception {
            // Given - valid pattern: letters, numbers, parentheses, single spaces
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Barrio (Norte) 123")
                    .build();
            doNothing().when(neighborhoodService).createNeighborhood(any());

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
            verify(neighborhoodService, times(1)).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenNameHasLeadingSpaces() throws Exception {
            // Given - pattern forbids leading/trailing spaces
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName(" Barrio")
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenNameHasTrailingSpaces() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Barrio ")
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenNameIsNull() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName(null)
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenLocalityIdIsNull() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withLocalityId(null)
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenLatitudeIsNull() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withLatitude(null)
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenLongitudeIsNull() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withLongitude(null)
                    .build();

            // When & Then
            mockMvc.perform(post(BASE_URL)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).createNeighborhood(any());
        }
    }

    @Nested
    class UpdateNeighborhood_NeighborhoodRequestValidation {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn200_whenNameHasMinLength() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Ab")
                    .build();
            doNothing().when(neighborhoodService).updateNeighborhood(any(), eq(FIXED_NEIGHBORHOOD_ID));

            // When & Then
            mockMvc.perform(put(BASE_URL + "/" + FIXED_NEIGHBORHOOD_ID)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
            verify(neighborhoodService, times(1)).updateNeighborhood(any(), eq(FIXED_NEIGHBORHOOD_ID));
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenNameBelowMinLength() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("A")
                    .build();

            // When & Then
            mockMvc.perform(put(BASE_URL + "/" + FIXED_NEIGHBORHOOD_ID)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).updateNeighborhood(any(), eq(FIXED_NEIGHBORHOOD_ID));
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Limit Values
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenNameAboveMaxLength() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("A".repeat(65))
                    .build();

            // When & Then
            mockMvc.perform(put(BASE_URL + "/" + FIXED_NEIGHBORHOOD_ID)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(neighborhoodService, never()).updateNeighborhood(any(), eq(FIXED_NEIGHBORHOOD_ID));
        }
    }
}
