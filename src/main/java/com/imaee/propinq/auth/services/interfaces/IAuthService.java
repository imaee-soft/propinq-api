package com.imaee.propinq.auth.services.interfaces;

import com.imaee.propinq.auth.controllers.requests.CheckTokenRequest;
import com.imaee.propinq.auth.controllers.requests.LoginRequest;
import com.imaee.propinq.auth.controllers.requests.RefreshTokenRequest;
import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.auth.controllers.responses.AuthResponse;
import com.imaee.propinq.auth.controllers.responses.UserAuthResponse;

public interface IAuthService {
    void signUp(SignUpRequest signUpRequest);
    AuthResponse logIn(LoginRequest loginRequest);
    UserAuthResponse checkToken(CheckTokenRequest checkTokenRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}