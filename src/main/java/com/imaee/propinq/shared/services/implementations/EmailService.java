package com.imaee.propinq.shared.services.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static com.imaee.propinq.shared.utils.ExceptionUtils.runCatching;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmail(String to, String subject, String content) {
        final var message = javaMailSender.createMimeMessage();
        runCatching(() -> buildAndSendEmail(message, to, subject, content), SERVICE_UNAVAILABLE);
    }

    private void buildAndSendEmail(MimeMessage mimeMessage, String to, String subject, String content)
            throws Exception {
        final var messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        messageHelper.setFrom(sender);
        javaMailSender.send(mimeMessage);
    }
}