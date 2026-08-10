package com.imaee.propinq.neighborhoods.services.usecases.implementations.unit.service.equivalentPartition;

import com.imaee.propinq.neighborhoods.builders.NeighborhoodBuilder;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.services.usecases.implementations.GetNeighborhoodUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetNeighborhoodUseCaseEquivalentPartitionTest {

    private static final UUID FIXED_NEIGHBORHOOD_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Mock
    private INeighborhoodRepository neighborhoodRepository;

    @InjectMocks
    private GetNeighborhoodUseCase getNeighborhoodUseCase;

    @Nested
    class GetNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnNeighborhoodResponse_whenExists() {
            // Given
            Neighborhood neighborhood = NeighborhoodBuilder.aNeighborhood()
                    .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                    .withName("Test Neighborhood")
                    .withLatitude(12.34)
                    .withLongitude(56.78)
                    .withDeleted(false)
                    .build();
            when(neighborhoodRepository.findById(FIXED_NEIGHBORHOOD_ID)).thenReturn(Optional.of(neighborhood));

            // When
            NeighborhoodResponse result = getNeighborhoodUseCase.getNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(neighborhoodRepository, times(1)).findById(FIXED_NEIGHBORHOOD_ID);
            assertThat(result).isNotNull();
            assertThat(result.neighborhoodId()).isEqualTo(FIXED_NEIGHBORHOOD_ID);
            assertThat(result.name()).isEqualTo("Test Neighborhood");
            assertThat(result.latitude()).isEqualTo(12.34);
            assertThat(result.longitude()).isEqualTo(56.78);
            assertThat(result.deleted()).isFalse();
        }

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnNeighborhoodResponseWithDeletedTrue_whenExistsAndDeleted() {
            // Given - partition: exists but soft-deleted
            Neighborhood neighborhood = NeighborhoodBuilder.aNeighborhood()
                    .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                    .withName("Deleted Neighborhood")
                    .withDeleted(true)
                    .build();
            when(neighborhoodRepository.findById(FIXED_NEIGHBORHOOD_ID)).thenReturn(Optional.of(neighborhood));

            // When
            NeighborhoodResponse result = getNeighborhoodUseCase.getNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(neighborhoodRepository, times(1)).findById(FIXED_NEIGHBORHOOD_ID);
            assertThat(result).isNotNull();
            assertThat(result.neighborhoodId()).isEqualTo(FIXED_NEIGHBORHOOD_ID);
            assertThat(result.name()).isEqualTo("Deleted Neighborhood");
            assertThat(result.deleted()).isTrue();
        }

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenNotExists() {
            // Given
            when(neighborhoodRepository.findById(FIXED_NEIGHBORHOOD_ID)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> getNeighborhoodUseCase.getNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(neighborhoodRepository, times(1)).findById(FIXED_NEIGHBORHOOD_ID);
        }
    }
}
