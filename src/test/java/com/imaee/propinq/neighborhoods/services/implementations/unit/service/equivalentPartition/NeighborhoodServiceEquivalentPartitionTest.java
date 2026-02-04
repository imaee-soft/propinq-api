package com.imaee.propinq.neighborhoods.services.implementations.unit.service.equivalentPartition;

import com.imaee.propinq.neighborhoods.builders.NeighborhoodRequestBuilder;
import com.imaee.propinq.neighborhoods.builders.NeighborhoodResponseBuilder;
import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.services.implementations.NeighborhoodService;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.*;
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
class NeighborhoodServiceEquivalentPartitionTest {

    private static final UUID FIXED_NEIGHBORHOOD_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Mock
    private ICreateNeighborhoodUseCase createNeighborhoodUseCase;
    @Mock
    private IGetNeighborhoodsUseCase getNeighborhoodsUseCase;
    @Mock
    private IGetNeighborhoodUseCase getNeighborhoodUseCase;
    @Mock
    private IUpdateNeighborhoodUseCase updateNeighborhoodUseCase;
    @Mock
    private IDeleteNeighborhoodUseCase deleteNeighborhoodUseCase;
    @Mock
    private IRecoverNeighborhoodUseCase recoverNeighborhoodUseCase;

    @InjectMocks
    private NeighborhoodService neighborhoodService;

    @Nested
    class CreateNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToUseCase_whenCreateNeighborhood() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Test Neighborhood")
                    .build();

            // When
            neighborhoodService.createNeighborhood(request);

            // Then
            verify(createNeighborhoodUseCase, times(1)).createNeighborhood(request);
        }

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenUseCaseThrows() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest().build();
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(createNeighborhoodUseCase).createNeighborhood(request);

            // When & Then
            assertThatThrownBy(() -> neighborhoodService.createNeighborhood(request))
                    .isInstanceOf(ResponseStatusException.class);
            verify(createNeighborhoodUseCase, times(1)).createNeighborhood(request);
        }
    }

    @Nested
    class GetNeighborhoods {

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnList_whenGetNeighborhoods() {
            // Given
            List<NeighborhoodResponse> expectedResponses = List.of(
                    NeighborhoodResponseBuilder.aNeighborhoodResponse()
                            .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                            .withName("Test Neighborhood")
                            .withDeleted(true)
                            .build()
            );
            when(getNeighborhoodsUseCase.getNeighborhoods()).thenReturn(expectedResponses);

            // When
            List<NeighborhoodResponse> actualResponses = neighborhoodService.getNeighborhoods();

            // Then
            verify(getNeighborhoodsUseCase, times(1)).getNeighborhoods();
            assertThat(actualResponses).isEqualTo(expectedResponses);
        }

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenUseCaseThrows() {
            // Given
            doThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
                    .when(getNeighborhoodsUseCase).getNeighborhoods();

            // When & Then
            assertThatThrownBy(() -> neighborhoodService.getNeighborhoods())
                    .isInstanceOf(ResponseStatusException.class);
            verify(getNeighborhoodsUseCase, times(1)).getNeighborhoods();
        }
    }

    @Nested
    class GetNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnNeighborhood_whenExists() {
            // Given
            NeighborhoodResponse expectedResponse = NeighborhoodResponseBuilder.aNeighborhoodResponse()
                    .withNeighborhoodId(FIXED_NEIGHBORHOOD_ID)
                    .withName("Test Neighborhood")
                    .withDeleted(true)
                    .build();
            when(getNeighborhoodUseCase.getNeighborhood(FIXED_NEIGHBORHOOD_ID)).thenReturn(expectedResponse);

            // When
            NeighborhoodResponse actualResponse = neighborhoodService.getNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(getNeighborhoodUseCase, times(1)).getNeighborhood(FIXED_NEIGHBORHOOD_ID);
            assertThat(actualResponse).isEqualTo(expectedResponse);
        }

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenUseCaseThrows() {
            // Given
            doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                    .when(getNeighborhoodUseCase).getNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // When & Then
            assertThatThrownBy(() -> neighborhoodService.getNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(getNeighborhoodUseCase, times(1)).getNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class UpdateNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToUseCase_whenUpdateNeighborhood() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest()
                    .withName("Updated Neighborhood")
                    .withLatitude(11.11)
                    .withLongitude(22.22)
                    .build();

            // When
            neighborhoodService.updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(updateNeighborhoodUseCase, times(1)).updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenUseCaseThrows() {
            // Given
            NeighborhoodRequest request = NeighborhoodRequestBuilder.aNeighborhoodRequest().build();
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(updateNeighborhoodUseCase).updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);

            // When & Then
            assertThatThrownBy(() -> neighborhoodService.updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(updateNeighborhoodUseCase, times(1)).updateNeighborhood(request, FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class DeleteNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToUseCase_whenDeleteNeighborhood() {
            // When
            neighborhoodService.deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(deleteNeighborhoodUseCase, times(1)).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenUseCaseThrows() {
            // Given
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(deleteNeighborhoodUseCase).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // When & Then
            assertThatThrownBy(() -> neighborhoodService.deleteNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(deleteNeighborhoodUseCase, times(1)).deleteNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }

    @Nested
    class RecoverNeighborhood {

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldDelegateToUseCase_whenRecoverNeighborhood() {
            // When
            neighborhoodService.recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // Then
            verify(recoverNeighborhoodUseCase, times(1)).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }

        @Test
        // Test type: UNIT
        // Layer: service
        // Quality Attribute: Reliability
        // Testing Technique: Equivalent Partition
        void shouldThrowException_whenUseCaseThrows() {
            // Given
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                    .when(recoverNeighborhoodUseCase).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);

            // When & Then
            assertThatThrownBy(() -> neighborhoodService.recoverNeighborhood(FIXED_NEIGHBORHOOD_ID))
                    .isInstanceOf(ResponseStatusException.class);
            verify(recoverNeighborhoodUseCase, times(1)).recoverNeighborhood(FIXED_NEIGHBORHOOD_ID);
        }
    }
}
