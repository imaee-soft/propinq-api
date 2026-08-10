package com.imaee.propinq.neighborhoods.services.usecases.implementations.unit.service.equivalentPartition;

import com.imaee.propinq.neighborhoods.builders.NeighborhoodBuilder;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.services.usecases.implementations.GetNeighborhoodsUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetNeighborhoodsUseCaseEquivalentPartitionTest {

    private static final UUID FIXED_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID FIXED_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000003");

    @Mock
    private INeighborhoodRepository neighborhoodRepository;

    @InjectMocks
    private GetNeighborhoodsUseCase getNeighborhoodsUseCase;

    @Nested
    class GetNeighborhoods {

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnList_whenMultipleExist() {
            // Given
            Neighborhood neighborhood1 = NeighborhoodBuilder.aNeighborhood()
                    .withNeighborhoodId(FIXED_ID_1)
                    .withName("Neighborhood A")
                    .withLatitude(1.0)
                    .withLongitude(1.0)
                    .build();
            Neighborhood neighborhood2 = NeighborhoodBuilder.aNeighborhood()
                    .withNeighborhoodId(FIXED_ID_2)
                    .withName("Neighborhood B")
                    .withLatitude(2.0)
                    .withLongitude(2.0)
                    .build();
            when(neighborhoodRepository.findAll()).thenReturn(List.of(neighborhood1, neighborhood2));

            // When
            List<NeighborhoodResponse> result = getNeighborhoodsUseCase.getNeighborhoods();

            // Then
            verify(neighborhoodRepository, times(1)).findAll();
            assertThat(result).hasSize(2);
            assertThat(result.get(0).neighborhoodId()).isEqualTo(FIXED_ID_1);
            assertThat(result.get(0).name()).isEqualTo("Neighborhood A");
            assertThat(result.get(1).neighborhoodId()).isEqualTo(FIXED_ID_2);
            assertThat(result.get(1).name()).isEqualTo("Neighborhood B");
        }

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnEmptyList_whenNoneExist() {
            // Given
            when(neighborhoodRepository.findAll()).thenReturn(List.of());

            // When
            List<NeighborhoodResponse> result = getNeighborhoodsUseCase.getNeighborhoods();

            // Then
            verify(neighborhoodRepository, times(1)).findAll();
            assertThat(result).isEmpty();
        }

        @Test
        // Test type: UNIT
        // Layer: service (use case)
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnList_whenOneExists() {
            // Given
            Neighborhood neighborhood = NeighborhoodBuilder.aNeighborhood()
                    .withNeighborhoodId(FIXED_ID_1)
                    .withName("Single Neighborhood")
                    .build();
            when(neighborhoodRepository.findAll()).thenReturn(List.of(neighborhood));

            // When
            List<NeighborhoodResponse> result = getNeighborhoodsUseCase.getNeighborhoods();

            // Then
            verify(neighborhoodRepository, times(1)).findAll();
            assertThat(result).hasSize(1);
            assertThat(result.get(0).neighborhoodId()).isEqualTo(FIXED_ID_1);
            assertThat(result.get(0).name()).isEqualTo("Single Neighborhood");
        }
    }
}
