package com.imaee.propinq.users.controllers.implementations;

import com.imaee.propinq.users.controllers.interfaces.IUserController;
import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;
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
}
