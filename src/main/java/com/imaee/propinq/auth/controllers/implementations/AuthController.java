package com.imaee.propinq.auth.controllers.implementations;

import com.imaee.propinq.auth.controllers.interfaces.IAuthController;
import com.imaee.propinq.auth.controllers.requests.CheckTokenRequest;
import com.imaee.propinq.auth.controllers.requests.LoginRequest;
import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.auth.controllers.responses.AuthResponse;
import com.imaee.propinq.auth.controllers.responses.UserAuthResponse;
import com.imaee.propinq.auth.services.interfaces.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController implements IAuthController {

    private final IAuthService authService;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
    }

    @Override
    public AuthResponse logIn(LoginRequest loginRequest) {
        return authService.logIn(loginRequest);
    }

    @Override
    public UserAuthResponse checkToken(CheckTokenRequest checkTokenRequest) {
        return authService.checkToken(checkTokenRequest);
    }
}