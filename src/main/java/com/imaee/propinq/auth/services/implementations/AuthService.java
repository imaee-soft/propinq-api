package com.imaee.propinq.auth.services.implementations;

import com.imaee.propinq.auth.controllers.requests.CheckTokenRequest;
import com.imaee.propinq.auth.controllers.requests.LoginRequest;
import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.auth.controllers.responses.AuthResponse;
import com.imaee.propinq.auth.controllers.responses.UserAuthResponse;
import com.imaee.propinq.auth.services.interfaces.IAuthService;
import com.imaee.propinq.config.utils.JwtUtils;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final IUserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        userService.saveUser(signUpRequest);
    }

    @Override
    public AuthResponse logIn(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.email(), 
                    loginRequest.password()
                );

            Authentication authentication = authenticationManager.authenticate(authToken);

            String accessToken = jwtUtils.createToken(authentication);

            User user = userService.findUserByEmail(loginRequest.email());

            return new AuthResponse(
                accessToken,
                "REFRESH_TOKEN",
                new UserAuthResponse(
                    user.getUserId(),
                    user.getEmail(),
                    user.getRole()
                )
            );

        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @Override
    public UserAuthResponse checkToken(CheckTokenRequest checkTokenRequest) {
        try {
            DecodedJWT decodedJWT = jwtUtils.validatetoken(checkTokenRequest.accessToken());
            
            String email = jwtUtils.extractUserName(decodedJWT);
            
            User user = userService.findUserByEmail(email);
            
            if (!user.isActivated()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La cuenta del usuario esta desactivada");
            }
            
            return new UserAuthResponse(
                user.getUserId(),
                user.getEmail(),
                user.getRole()
            );
            
        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido o expirado");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Validacion de token fallida");
        }
    }
}