package com.imaee.propinq.users.controllers.implementations;

import com.imaee.propinq.users.controllers.interfaces.IUserController;
import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.services.interfaces.IUserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements IUserController {

    private final IUserService userService;

    public UserController (IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void activateUser(UUID userId, ActivateUserRequest activateUserRequest){
        userService.activateUser(userId, activateUserRequest.activationToken());
    }

    @Override
    public void sendEmailToRecoverPassword(SendEmailRequest sendEmailRequest) {
        userService.sendEmailToRecoverPassword(sendEmailRequest.email());
    }

    @Override
    public void recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
        userService.recoverPassword(recoverPasswordRequest);
    }

    @Override
    public void resendActivationEmail(SendEmailRequest sendEmailRequest) {
        userService.resendActivationEmail(sendEmailRequest);
    }

    @Override
    public void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest) {
        // Implementación no funcional
    }

}
