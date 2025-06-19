package com.imaee.propinq.users.controllers.implementations;

import com.imaee.propinq.users.controllers.interfaces.IAuthController;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.services.interfaces.IAuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements IAuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
    }
}
