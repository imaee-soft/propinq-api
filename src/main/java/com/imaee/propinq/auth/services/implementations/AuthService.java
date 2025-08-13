package com.imaee.propinq.auth.services.implementations;

import com.imaee.propinq.auth.controllers.requests.CheckTokenRequest;
import com.imaee.propinq.auth.controllers.requests.LoginRequest;
import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.auth.controllers.responses.AuthResponse;
import com.imaee.propinq.auth.controllers.responses.UserAuthResponse;
import com.imaee.propinq.auth.services.interfaces.IAuthService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.imaee.propinq.users.data.enums.Role.ADMIN;
import static java.util.UUID.fromString;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final IUserService userService;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        userService.saveUser(signUpRequest);
    }

    // TODO: Implement actual JWT extraction logic. This should use AuthProvider to log in
    @Override
    public AuthResponse logIn(LoginRequest loginRequest) {
        return new AuthResponse(
                "ACCESS_TOKEN",
                "REFRESH_TOKEN",
                checkToken(null)
        );
    }

    // TODO: Implement actual JWT extraction logic. This should use JWTService to validate the access token
    @Override
    public UserAuthResponse checkToken(CheckTokenRequest checkTokenRequest) {
        return new UserAuthResponse(
                fromString("0362aba9-e4d9-4ff3-8b5a-886ee42ff468"),
                "esosa",
                ADMIN
        );
    }
}