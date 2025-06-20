package com.imaee.propinq.users.utils;



import com.imaee.propinq.users.data.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailBuilder {

    private String frontendURL;
    @Value("${frontend.url}")
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
    @Value("${frontend.url}")
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
}
