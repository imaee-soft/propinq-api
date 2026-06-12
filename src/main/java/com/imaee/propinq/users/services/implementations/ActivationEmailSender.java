package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.utils.EmailBuilder;
import com.imaee.propinq.users.services.IActivationEmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.ACTIVATION_EMAIL_SUBJECT;
import static com.imaee.propinq.users.utils.Constants.NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT;

@Slf4j
@Component
@AllArgsConstructor
public class ActivationEmailSender implements IActivationEmailSender {

    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;

    @Async
    public void sendActivationEmail(User user, String verificationCode) {
        sendEmail(user, verificationCode, ACTIVATION_EMAIL_SUBJECT);
    }

    @Async
    public void sendNewActivationEmail(User user, String verificationCode) {
        sendEmail(user, verificationCode, NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT);
    }

    private void sendEmail(User user, String verificationCode, String subject) {
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    subject,
                    emailBuilder.buildActivationEmailBody(user, verificationCode)
            );
            log.info("Activation email queued for {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send activation email for {}", user.getEmail(), e);
        }
    }
}