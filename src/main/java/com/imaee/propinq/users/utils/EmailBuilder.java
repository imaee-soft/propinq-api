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
                + "<h1>Welcome to PropInq</h1>"
                + "<h3>Hey " + user.getFirstName() + " " + user.getLastName() + "!</h3>"
                + "<h4>Thanks for joining us!</h4>"
                + "<p>Click the link below to activate your account</p>"
                + "<a href=\""
                + activationUrl
                + "\">Activate account</a>"
                + "</div>"
                + "</td>"
                + "</tr>"
                + "</table>";
    }

    public String buildWelcomeEmail(User user) {
        return "<table style='width:100%; height:100%;'>"
                + "<tr><td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
                + "<div style='display:inline-block;'>"
                + "<h1>Welcome to PropInq</h1>"
                + "<h3>Hey " + user.getFirstName() + " " +  user.getLastName() + "!</h3>"
                + "<h4>Your account has been activated.</h4>"
                + "<p>Enjoy renting with us!</p>"
                + "</div></td></tr></table>";
    }

    public String buildRecoverPasswordEmail(String username, UUID recoverPasswordTokenId) {
        String recoverPasswordURL = frontendURL + "/auth/recover-password?token=" + recoverPasswordTokenId;
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Password Recovery</title>\n"
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
                + "            <h1>Password Recovery</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <p>Hello " + username + ",</p>\n"
                + "            <p>We received a request to reset your password. Click the button below to create a new password:</p>\n"
                + "            <p style=\"display: flex; flex: content; justify-content: center;\"><a href=\" " + recoverPasswordURL + "\" class=\"button\">Reset Password</a></p>\n"
                + "            <p>If you did not request this change, please ignore this email.</p>\n"
                + "            <p>Best regards,<br>The Support Team</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>PropInq, Villa Maria, Cordoba, Argentina</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }
}
