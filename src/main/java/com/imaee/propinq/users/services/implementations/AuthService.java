package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.services.interfaces.IAuthService;
import com.imaee.propinq.users.services.interfaces.IUserService;

public class AuthService implements IAuthService {
    private final IUserService userService;


    public AuthService(IUserService userService) {
        this.userService = userService;

    }
    @Override
    public void signUp(SignUpRequest signUpRequest){
        userService.saveUser(signUpRequest);
    }
}
