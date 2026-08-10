package com.imaee.propinq.neighborhoods.controllers.implementations.unit.controller.equivalentPartition;

import com.imaee.propinq.neighborhoods.builders.NeighborhoodRequestBuilder;
import com.imaee.propinq.neighborhoods.builders.NeighborhoodResponseBuilder;
import com.imaee.propinq.neighborhoods.controllers.implementations.NeighborhoodController;
import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.services.interfaces.INeighborhoodService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NeighborhoodControllerEquivalentPartitionTest {

    private static final UUID FIXED_NEIGHBORHOOD_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Mock
    private INeighborhoodService neighborhoodService;

    @InjectMocks
    private NeighborhoodController neighborhoodController;

    @Nested
    class CreateNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToService_whenCreateNeighborhood() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Test Neighborhood")
                    .build();

            // When
            neighborhoodController.createNeighborhood(request);

            // Then
            verify(neighborhoodService, times(1)).createNeighborhood(request);
        }

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenServiceThrows() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest().build();
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(neighborhoodService).createNeighborhood(request);

            // When & Then
            assertThatThrownBy(() -> neighborhoodController.createNeighborhood(request))
                    .isInstanceOf(ResponseStatusException.class);
            verify(neighborhoodService, times(1)).createNeighborhood(request);
        }
    }

    @Nested
    class GetNeighborhoods {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnList_whenGetNeighborhoods() {
            // Given
            List<NeighborhoodResponse> expected = List.of(
                    NeighborhoodResponseBuilder.aNeighborhoodResponse()
                            .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                            .withName("Test Neighborhood")
                            .build()
            );
            when(neighborhoodService.getNeighborhoods()).thenReturn(expected);

            // When
            List<NeighborhoodResponse> result = neighborhoodController.getNeighborhoods();

            // Then
            verify(neighborhoodService, times(1)).getNeighborhoods();
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    class GetNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnNeighborhood_whenExists() {
            // Given
            NeighborhoodResponse expected = NeighborhoodResponseBuilder.aNeighborhoodResponse()
                    .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                    .withName("Test Neighborhood")
                    .build();
            when(neighborhoodService.getNeighborhood(FIXED_NEIGHBORHOOD_ID)).thenReturn(expected);

            // When
            NeighborhoodResponse result = neighborhoodController.getNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(neighborhoodService, times(1)).getNeighborhood(FIXED_NEIGHBORHOOD_ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowNotFound_whenDoesNotExist() {
            // Given
            when(neighborhoodService.getNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            // When & Then
            assertThatThrownBy(() -> neighborhoodController.getNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(neighborhoodService, times(1)).getNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowConflict_whenDeleted() {
            // Given
            when(neighborhoodService.getNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));

            // When & Then
            assertThatThrownBy(() -> neighborhoodController.getNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(neighborhoodService, times(1)).getNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class UpdateNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToService_whenUpdateNeighborhood() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Updated Neighborhood")
                    .build();

            // When
            neighborhoodController.updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(neighborhoodService, times(1)).updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenServiceThrows() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest().build();
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(neighborhoodService).updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);

            // When & Then
            assertThatThrownBy(() -> neighborhoodController.updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(neighborhoodService, times(1)).updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class DeleteNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToService_whenDeleteNeighborhood() {
            // When
            neighborhoodController.deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(neighborhoodService, times(1)).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenServiceThrows() {
            // Given
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(neighborhoodService).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // When & Then
            assertThatThrownBy(() -> neighborhoodController.deleteNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(neighborhoodService, times(1)).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class RecoverNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToService_whenRecoverNeighborhood() {
            // When
            neighborhoodController.recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(neighborhoodService, times(1)).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenServiceThrows() {
            // Given
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(neighborhoodService).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // When & Then
            assertThatThrownBy(() -> neighborhoodController.recoverNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(neighborhoodService, times(1)).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }
}
