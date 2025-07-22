package com.imaee.propinq.auth.services.interfaces;

import com.imaee.propinq.auth.controllers.requests.CheckTokenRequest;
import com.imaee.propinq.auth.controllers.responses.UserAuthResponse;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;

public interface IAuthService {
    void signUp(SignUpRequest signUpRequest);
    UserAuthResponse checkToken(CheckTokenRequest checkTokenRequest);
}