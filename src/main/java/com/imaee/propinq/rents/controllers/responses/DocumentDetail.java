package com.imaee.propinq.rents.controllers.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DocumentDetail(
        UUID documentId,
        String name,
        byte[] content
) {}