package com.imaee.propinq.users.utils;

public class Constants {

    private Constants() {}

    public static final String ACTIVATION_EMAIL_SUBJECT = "Activación de cuenta";
    public static final String WELCOME_EMAIL_SUBJECT = "¡Bienvenido a PropInq!";
    public static final String RECOVER_PASSWORD_EMAIL_SUBJECT = "Recuperación de contraseña";
    public static final String NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT = "Nuevo enlace de activación";
    public static final String EXPIRED_ACTIVATION_TOKEN_MESSAGE = "El token de activación ha expirado.";
    public static final String USER_ALREADY_ACTIVATED_MESSAGE = "El usuario ya está activado.";
    public static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Las contraseñas no coinciden.";
    public static final String TOKEN_NOT_EXPIRED_MESSAGE = "El token aún no ha expirado.";
    public static final String NONEXISTING_TOKEN_MESSAGE = "El token no existe.";
    public static final String TOKEN_USER_MISMATCH_MESSAGE = "El token de activación no corresponde a este usuario.";
}
