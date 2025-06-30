package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.requests.SignUpRequest;

public interface IAuthService {
    void signUp(SignUpRequest signUpRequest);

}
