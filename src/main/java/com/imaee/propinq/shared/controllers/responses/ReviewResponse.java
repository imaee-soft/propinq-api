package com.imaee.propinq.shared.controllers.responses;

import java.util.UUID;

public record ReviewResponse(
        UUID reviewId,
        String content,
        Integer rating,
        UUID authorId
) {
}
