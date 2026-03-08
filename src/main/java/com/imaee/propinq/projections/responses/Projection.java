package com.imaee.propinq.projections.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Projection(
        LocalDate date,
        Double value,
        boolean estimated,
        Double dif,
        Double amount
) {}