package com.imaee.propinq.projections.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectionWrapper(
        boolean success,
        List<Projection> data
) {}