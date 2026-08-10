package com.imaee.propinq.parameters.controllers.implementations.unit.controller.equivalentPartition;

import com.imaee.propinq.parameters.controllers.implementations.ParameterController;
import com.imaee.propinq.parameters.services.IParameterService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParameterControllerEquivalentPartitionTest {

    @Mock
    private IParameterService parameterService;

    @InjectMocks
    private ParameterController parameterController;

    @Nested
    class MaxPrice {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnMaxPrice_whenServiceReturnsValue() {
            when(parameterService.maxPrice()).thenReturn(250000.0);

            Double result = parameterController.maxPrice();

            assertThat(result).isEqualTo(250000.0);
            verify(parameterService).maxPrice();
        }
    }

    @Nested
    class MinPrice {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnMinPrice_whenServiceReturnsValue() {
            when(parameterService.minPrice()).thenReturn(10000.0);

            Double result = parameterController.minPrice();

            assertThat(result).isEqualTo(10000.0);
            verify(parameterService).minPrice();
        }
    }

    @Nested
    class Rooms {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnRoomOptions_whenServiceReturnsList() {
            when(parameterService.rooms()).thenReturn(List.of(1, 2, 3));

            List<Integer> result = parameterController.rooms();

            assertThat(result).containsExactly(1, 2, 3);
            verify(parameterService).rooms();
        }
    }

    @Nested
    class Bathrooms {

        @Test
        // Test type: UNIT
        // Layer: controller
        // Quality Attribute: Functionality
        // Testing Technique: Equivalent Partition
        void shouldReturnBathroomOptions_whenServiceReturnsList() {
            when(parameterService.bathrooms()).thenReturn(List.of(1, 2));

            List<Integer> result = parameterController.bathrooms();

            assertThat(result).containsExactly(1, 2);
            verify(parameterService).bathrooms();
        }
    }
}
