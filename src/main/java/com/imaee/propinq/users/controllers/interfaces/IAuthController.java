package com.imaee.propinq.users.controllers.interfaces;

import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


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
    @ResponseStatus(HttpStatus.CREATED)
    void signUp(@RequestBody @Valid SignUpRequest signUpRequest);


}
