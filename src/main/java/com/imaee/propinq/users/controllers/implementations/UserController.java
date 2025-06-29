package com.imaee.propinq.users.controllers.implementations;

import com.imaee.propinq.users.controllers.interfaces.IUserController;
import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.IUserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController implements IUserController {

    private final IUserService userService;

    @Override
    public void activateUser(UUID userId, @Valid ActivateUserRequest activateUserRequest){
        userService.activateUser(userId, activateUserRequest.activationToken());
    }

    @Override
    public void sendEmailToRecoverPassword(@Valid SendEmailRequest sendEmailRequest) {
        userService.sendEmailToRecoverPassword(sendEmailRequest.email());
    }

    @Override
    public void recoverPassword(@Valid RecoverPasswordRequest recoverPasswordRequest) {
        userService.recoverPassword(recoverPasswordRequest);
    }

    @Override
    public void resendActivationEmail(@Valid SendEmailRequest sendEmailRequest) {
        userService.resendActivationEmail(sendEmailRequest);
    }

    @Override
    public void sendNewActivationToken(@Valid SendNewActivationTokenRequest sendNewActivationTokenRequest) {
        userService.sendNewActivationToken(sendNewActivationTokenRequest);
    }

}
