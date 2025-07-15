package com.imaee.propinq.exceptions.data;

import java.time.LocalDateTime;

public record ExceptionMessage(
        String message,
        LocalDateTime timestamp,
        int status
) {
    public static ExceptionMessage of(String message, int status) {
        return new ExceptionMessage(message, LocalDateTime.now(), status);
    }
}