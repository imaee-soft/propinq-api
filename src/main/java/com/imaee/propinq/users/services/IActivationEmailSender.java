package com.imaee.propinq.users.services;

import com.imaee.propinq.users.data.models.User;
import java.util.UUID;

public interface IActivationEmailSender {
    void sendActivationEmail(User user, String verificationCode);
    void sendNewActivationEmail(User user, String verificationCode);
}
