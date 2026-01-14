package com.imaee.propinq.auth.services.implementations;

import com.imaee.propinq.auth.controllers.requests.CheckTokenRequest;
import com.imaee.propinq.auth.controllers.requests.LoginRequest;
import com.imaee.propinq.auth.controllers.requests.RefreshTokenRequest;
import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.auth.controllers.responses.AuthResponse;
import com.imaee.propinq.auth.controllers.responses.UserAuthResponse;
import com.imaee.propinq.auth.services.interfaces.IAuthService;
import com.imaee.propinq.auth.services.interfaces.IRecaptchaService;
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
import static com.imaee.propinq.users.data.enums.Role.ADMIN;
import static java.util.UUID.fromString;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final IUserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final IRecaptchaService recaptchaService;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        userService.saveUser(signUpRequest);
    }

    @Override
    public AuthResponse logIn(LoginRequest loginRequest) {
        boolean isHuman = recaptchaService.validateToken(loginRequest.recaptchaToken());

        if (!isHuman) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Verificación de seguridad fallida. Por favor recarga la página.");
        }

        try {
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.email(), 
                    loginRequest.password()
                );

            Authentication authentication = authenticationManager.authenticate(authToken);

            String accessToken = jwtUtils.createToken(authentication);
            String refreshToken = jwtUtils.createRefreshToken(authentication);

            User user = userService.findUserByEmail(loginRequest.email());

            return new AuthResponse(
                accessToken,
                refreshToken,
                new UserAuthResponse(
                    user.getUserId(),
                    user.getEmail(),
                    user.getRole()
                )
            );

        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales Invalidas");
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

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            // Validar el refresh token
            DecodedJWT decodedRefreshToken = jwtUtils.validateRefreshToken(refreshTokenRequest.refreshToken());
            String username = jwtUtils.extractUserName(decodedRefreshToken);
            
            // Buscar el usuario
            User user = userService.findUserByEmail(username);
            
            // Crear nueva autenticación simple para generar nuevos tokens
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    user.getEmail(), 
                    null
                );

            // Generar nuevos tokens
            String newAccessToken = jwtUtils.createToken(authToken);
            String newRefreshToken = jwtUtils.createRefreshToken(authToken);

            return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                new UserAuthResponse(
                    user.getUserId(),
                    user.getEmail(),
                    user.getRole()
                )
            );

        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token inválido o expirado");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error al refrescar token");
        }
    }
}