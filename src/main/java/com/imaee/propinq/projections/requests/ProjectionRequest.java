package com.imaee.propinq.projections.requests;

import lombok.Builder;

@Builder
public record ProjectionRequest(
        Double amount,
        String date,
        Integer months,
        String rate
) {}