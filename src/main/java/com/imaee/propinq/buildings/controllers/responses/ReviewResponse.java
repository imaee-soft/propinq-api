package com.imaee.propinq.buildings.controllers.responses;

import java.util.UUID;

public record ReviewResponse(
        UUID reviewId,
        String content,
        Integer rating,
        UUID authorId,
) {
}
