package com.imaee.propinq.users.controllers.interfaces;


import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/users")
public interface IUserController {

    @PostMapping("/{userId}/activate")
    void activateUser(@PathVariable UUID userId,
        @RequestBody @Valid ActivateUserRequest activateUserRequest);

    @PostMapping("/recover-password/send-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void sendEmailToRecoverPassword(@RequestBody SendEmailRequest sendEmailRequest);

    @PostMapping("/recover-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void recoverPassword(@RequestBody RecoverPasswordRequest recoverPasswordRequest);

    @PostMapping("/resend-activation-email")
    void resendActivationEmail(@RequestBody SendEmailRequest sendEmailRequest);

    @PostMapping("/send-new-activation-token")
    void sendNewActivationToken(@RequestBody SendNewActivationTokenRequest sendNewActivationTokenRequest);

}