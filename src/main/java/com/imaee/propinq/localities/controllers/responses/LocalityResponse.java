package com.imaee.propinq.localities.controllers.responses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LocalityResponse(
        UUID localityId,
        String name,
        UUID provinceId,
        Boolean deleted,
        Double latitude,
        Double longitude
){
}
