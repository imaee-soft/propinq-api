package com.imaee.propinq.users.controllers.requests;

import jakarta.validation.constraints.Email;

public record SendEmailRequest(
        @Email(message = "Invalid email")
        String email
) {
}
