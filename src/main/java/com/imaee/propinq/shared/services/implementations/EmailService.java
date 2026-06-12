package com.imaee.propinq.shared.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaee.propinq.shared.services.interfaces.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.imaee.propinq.shared.utils.ExceptionUtils.runCatching;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.password}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Override
    public void sendEmail(String to, String subject, String content) {
        runCatching(() -> buildAndSendEmail(to, subject, content), SERVICE_UNAVAILABLE);
    }

    private void buildAndSendEmail(String to, String subject, String content) throws Exception {
        String finalSender = "resend".equalsIgnoreCase(sender) ? "Propinq <onboarding@resend.dev>" : sender;
        Map<String, Object> payload = Map.of(
            "from", finalSender,
            "to", List.of(to),
            "subject", subject,
            "html", content
        );
        
        String jsonPayload = objectMapper.writeValueAsString(payload);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.resend.com/emails"))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 300) {
            throw new RuntimeException("Resend API error: HTTP " + response.statusCode() + " - " + response.body());
        }
    }
}