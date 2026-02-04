package com.imaee.propinq.neighborhoods.controllers.implementations.integration.controller.equivalentPartition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaee.propinq.neighborhoods.builders.NeighborhoodRequestBuilder;
import com.imaee.propinq.neighborhoods.builders.NeighborhoodResponseBuilder;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.services.interfaces.INeighborhoodService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(com.imaee.propinq.neighborhoods.controllers.implementations.NeighborhoodController.class)
class NeighborhoodControllerEquivalentPartitionIT {

    private static final UUID FIXED_NEIGHBORHOOD_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final String BASE_URL = "/api/v1/neighborhoods";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private INeighborhoodService neighborhoodService;

    @Nested
    class GetNeighborhoods {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn200AndList_whenExists() throws Exception {
            // Given
            List<NeighborhoodResponse> expected = List.of(
                    NeighborhoodResponseBuilder.aNeighborhoodResponse()
                            .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                            .withName("Test Neighborhood")
                            .build()
            );
            when(neighborhoodService.getNeighborhoods()).thenReturn(expected);

            // When & Then
            mockMvc.perform(get(BASE_URL).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].neighborhoodId").value(FIXED_NEIGHBORHOOD_ID.toString()))
                    .andExpect(jsonPath("$[0].name").value("Test Neighborhood"));
            verify(neighborhoodService, times(1)).getNeighborhoods();
        }
    }

    @Nested
    class GetNeighborhood {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn200AndBody_whenExists() throws Exception {
            // Given
            NeighborhoodResponse expected = NeighborhoodResponseBuilder.aNeighborhoodResponse()
                    .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                    .withName("Test Neighborhood")
                    .withLatitude(12.34)
                    .withLongitude(56.78)
                    .build();
            when(neighborhoodService.getNeighborhood(FIXED_NEIGHBORHOOD_ID)).thenReturn(expected);

            // When & Then
            mockMvc.perform(get(BASE_URL + "/" + FIXED_NEIGHBORHOOD_ID).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.neighborhoodId").value(FIXED_NEIGHBORHOOD_ID.toString()))
                    .andExpect(jsonPath("$.name").value("Test Neighborhood"))
                    .andExpect(jsonPath("$.latitude").value(12.34))
                    .andExpect(jsonPath("$.longitude").value(56.78));
            verify(neighborhoodService, times(1)).getNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Reliability
        @WithMockUser
        void shouldReturn400_whenNotExists() throws Exception {
            // Given
            when(neighborhoodService.getNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .thenThrow(org.springframework.web.server.ResponseStatusException.class);

            // When & Then
            mockMvc.perform(get(BASE_URL + "/" + FIXED_NEIGHBORHOOD_ID).with(csrf()))
                    .andExpect(status().is4xxClientError());
            verify(neighborhoodService, times(1)).getNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class CreateNeighborhood {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201_whenValidRequest() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("New Neighborhood")
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
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Usability
        @WithMockUser
        void shouldReturn400_whenInvalidRequest() throws Exception {
            // Given - name too short (min 2)
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
    }

    @Nested
    class UpdateNeighborhood {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn200_whenValidRequest() throws Exception {
            // Given
            var request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Updated Neighborhood")
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
    }

    @Nested
    class DeleteNeighborhood {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn204_whenDelete() throws Exception {
            // Given
            doNothing().when(neighborhoodService).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // When & Then
            mockMvc.perform(delete(BASE_URL + "/" + FIXED_NEIGHBORHOOD_ID).with(csrf()))
                    .andExpect(status().isNoContent());
            verify(neighborhoodService, times(1)).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class RecoverNeighborhood {

        @Test
        // Test Type: INTEGRATION
        // Layer: Controller
        // Testing Technique: Equivalent Partition
        // Quality Attribute: Functionality
        @WithMockUser
        void shouldReturn201_whenRecover() throws Exception {
            // Given
            doNothing().when(neighborhoodService).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // When & Then
            mockMvc.perform(post(BASE_URL + "/" + FIXED_NEIGHBORHOOD_ID + "/recover").with(csrf()))
                    .andExpect(status().isCreated());
            verify(neighborhoodService, times(1)).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }
}
