package com.imaee.propinq.buildings.controllers.requests;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateBuildingRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void emptyName_returnsOnlyNotBlankMessage() {
        var request = requestWithName("");

        Set<ConstraintViolation<CreateBuildingRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("El nombre del edificio no debe estar vacío");
    }

    @Test
    void shortName_returnsLengthMessage() {
        var request = requestWithName("abcd");

        Set<ConstraintViolation<CreateBuildingRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("El nombre del edificio debe tener al menos 5 caracteres");
    }

    private static CreateBuildingRequest requestWithName(String name) {
        return new CreateBuildingRequest(
                name,
                "Descripcion valida",
                "Calle Falsa 123",
                -32.4,
                -63.2,
                UUID.randomUUID(),
                "EDIFICIO"
        );
    }
}
