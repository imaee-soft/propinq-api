package com.imaee.propinq.neighborhood.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LocalityRequest(
        @NotNull
        @Size(min = 2, max = 64, message = "THE NAME MUST BE BETWEEN 2 AND 64 CHARACTERS")
        @Pattern(regexp = "^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$", message = "THE NAME MUST BE ONLY LETTERS OR NUMBERS, THERE MUST BE NO DOUBLE SPACES OR SPACE AT THE BEGINNING OR END")
        String name
) {
}
