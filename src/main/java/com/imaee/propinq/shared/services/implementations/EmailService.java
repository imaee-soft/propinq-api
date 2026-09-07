package com.imaee.propinq.shared.services.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static com.imaee.propinq.shared.utils.ExceptionUtils.runCatching;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
public class EmailService implements IEmailService {

    private final RestClient resendClient;
    private final String apiKey;
    private final String fromEmail;

    public EmailService(
            RestClient.Builder restClientBuilder,
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from-email}") String fromEmail,
            @Value("${resend.base-url:https://api.resend.com}") String baseUrl
    ) {
        this.apiKey = apiKey;
        this.fromEmail = fromEmail;
        this.resendClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public void sendEmail(String to, String subject, String content) {
        runCatching(() -> postEmail(to, subject, content), SERVICE_UNAVAILABLE);
    }

    private void postEmail(String to, String subject, String content) {
        resendClient.post()
                .uri("/emails")
                .header(AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "from", fromEmail,
                        "to", List.of(to),
                        "subject", subject,
                        "html", content
                ))
                .retrieve()
                .toBodilessEntity();
    }
}
