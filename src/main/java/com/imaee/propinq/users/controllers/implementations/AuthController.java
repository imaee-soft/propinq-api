package com.imaee.propinq.users.controllers.implementations;

import com.imaee.propinq.users.controllers.interfaces.IAuthController;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.services.interfaces.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@AllArgsConstructor
@PreAuthorize("denyAll()")
public class AuthController implements IAuthController {
    private final IAuthService authService;

    @Override
    @PreAuthorize("permitAll()")
    public void signUp(SignUpRequest signUpRequest) {

        authService.signUp(signUpRequest);
    }
}
