package com.imaee.propinq.users.controllers.interfaces;


import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Tag(name = "Users", description = "Operaciones relacionadas con la gestión de usuarios")
public interface IUserController {


    @Operation(
        summary = "Activar usuario", 
        description = "Activa un usuario utilizando el token de activación enviado por email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario activado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Token de activación inválido o usuario ya activado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping("/{userId}/activate")
    void activateUser(
        @Parameter(description = "ID del usuario a activar") @PathVariable UUID userId,
        @RequestBody @Valid ActivateUserRequest activateUserRequest
    );

    @Operation(
        summary = "Enviar email para recuperar contraseña", 
        description = "Envía un email con el token para recuperar la contraseña"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Email enviado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Email no encontrado")
    })
    @PostMapping("/recover-password/send-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void sendEmailToRecoverPassword(@RequestBody SendEmailRequest sendEmailRequest);

    @Operation(
        summary = "Recuperar contraseña", 
        description = "Establece una nueva contraseña utilizando el token de recuperación"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contraseña cambiada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Token de recuperación inválido o expirado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping("/recover-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void recoverPassword(@RequestBody RecoverPasswordRequest recoverPasswordRequest);

    @Operation(
        summary = "Reenviar email de activación", 
        description = "Reenvía el email de activación con el token existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email de activación reenviado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Usuario ya activado"),
        @ApiResponse(responseCode = "404", description = "Email no encontrado")
    })
    @PostMapping("/resend-activation-email")
    void resendActivationEmail(@RequestBody SendEmailRequest sendEmailRequest);

    @Operation(
        summary = "Enviar nuevo token de activación", 
        description = "Genera y envía un nuevo token de activación cuando el anterior ha expirado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nuevo token de activación enviado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Usuario ya activado"),
        @ApiResponse(responseCode = "404", description = "Email no encontrado")
    })
    @PostMapping("/send-new-activation-token")
    void sendNewActivationToken(@RequestBody SendNewActivationTokenRequest sendNewActivationTokenRequest);

}