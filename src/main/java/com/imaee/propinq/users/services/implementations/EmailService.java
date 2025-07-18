package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.exceptions.custom_exceptions.EmailSendingException;
import com.imaee.propinq.users.services.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender; 

    @Override
    public void sendEmail(String to, String subject, String content) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            messageHelper.setFrom(sender);
            
            javaMailSender.send(message);
            
        } catch (MessagingException e) {
            
            throw new EmailSendingException("Error al enviar email: " + e.getMessage());

        } catch (Exception e) {

            throw new EmailSendingException("Error inesperado al enviar email: " + e.getMessage());
        }
    }
}