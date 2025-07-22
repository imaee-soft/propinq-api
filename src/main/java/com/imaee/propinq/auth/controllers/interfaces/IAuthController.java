package com.imaee.propinq.auth.controllers.interfaces;

import com.imaee.propinq.auth.controllers.requests.CheckTokenRequest;
import com.imaee.propinq.auth.controllers.responses.UserAuthResponse;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Operaciones relacionadas con autenticación y registro")
public interface IAuthController {

    @Operation(
            summary = "Registrar usuario",
            description = "Registra un nuevo usuario en el sistema y envía email de activación"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de registro inválidos", content = @Content()),
            @ApiResponse(responseCode = "409", description = "Usuario ya registrado", content = @Content())
    })
    @PostMapping("/signup")
    @ResponseStatus(CREATED)
    void signUp(@RequestBody @Valid SignUpRequest signUpRequest);

    @Operation(
            summary = "Chequear token",
            description = "Devuelve la autenticación correspondiente en caso de que el token sea válido"
    )
    @PostMapping("/check-token")
    @ResponseStatus(OK)
    UserAuthResponse checkToken(@RequestBody @Valid CheckTokenRequest checkTokenRequest);
}