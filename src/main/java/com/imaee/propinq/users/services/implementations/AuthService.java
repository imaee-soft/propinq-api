package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.services.interfaces.IAuthService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {
    private final IUserService userService;

    @Override
    public void signUp(SignUpRequest signUpRequest){
        userService.saveUser(signUpRequest);
    }
}
