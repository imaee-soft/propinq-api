package com.imaee.propinq.users.utils;

import com.imaee.propinq.users.data.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailBuilder {

    @Value("${frontend.url}")
    private String frontendURL;

    public String buildActivationEmailBody(User user, UUID activationTokenId) {
        String activationUrl = frontendURL + "/auth/activate?userId="
                + user.getUserId() + "&activationToken=" + activationTokenId;
        return "<table style='width:100%; height:100%;'>"
                + "<tr>"
                + "<td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
                + "<div style='display:inline-block;'>"
                + "<h1>Bienvenido a PropInq</h1>"
                + "<h3>Hola " + user.getFirstName() + " " + user.getLastName() + "</h3>"
                + "<h4>¡Gracias por registrarte!</h4>"
                + "<p>Hacé clic en el enlace de abajo para activar tu cuenta</p>"
                + "<a href=\""
                + activationUrl
                + "\">Activar cuenta</a>"
                + "</div>"
                + "</td>"
                + "</tr>"
                + "</table>";
    }

    public String buildWelcomeEmail(User user) {
        return "<table style='width:100%; height:100%;'>"
                + "<tr><td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
                + "<div style='display:inline-block;'>"
                + "<h1>Bienvenido a PropInq</h1>"
                + "<h3>Hola " + user.getFirstName() + " " + user.getLastName() + "</h3>"
                + "<h4>Tu cuenta fue activada correctamente.</h4>"
                + "<p>¡Disfrutá alquilar con nosotros!</p>"
                + "</div></td></tr></table>";
    }

    public String buildRecoverPasswordEmail(String username, UUID recoverPasswordTokenId) {
        String recoverPasswordURL = frontendURL + "/auth/recover-password?token=" + recoverPasswordTokenId;
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Recuperación de contraseña</title>\n"
                + "    <style>\n"
                + "        body { font-family: Arial, sans-serif; }\n"
                + "        .container { width: 80%; margin: 0 auto; }\n"
                + "        .header { background-color: #f4f4f4; padding: 20px; text-align: center; }\n"
                + "        .content { padding: 20px; }\n"
                + "        .footer { background-color: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            padding: 10px 20px;\n"
                + "            font-size: 16px;\n"
                + "            color: #fff;\n"
                + "            background-color: #1ae866;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "        .button:hover {\n"
                + "            background-color: #15d67c;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h1>Recuperación de contraseña</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <p>Hola " + username + ",</p>\n"
                + "            <p>Recibimos una solicitud para restablecer tu contraseña. Hacé clic en el botón de abajo para crear una nueva:</p>\n"
                + "            <p style=\"display: flex; flex: content; justify-content: center;\"><a href=\" " + recoverPasswordURL + "\" class=\"button\">Restablecer contraseña</a></p>\n"
                + "            <p>Si no solicitaste este cambio, podés ignorar este correo.</p>\n"
                + "            <p>Saludos,<br>El equipo de PropInq</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>PropInq, Villa María, Córdoba, Argentina</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }
}
