package com.imaee.propinq.users.utils;



import com.imaee.propinq.users.data.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

Component
public class EmailBuilder {

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
