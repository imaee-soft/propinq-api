package com.imaee.propinq.auth.services.implementations;

import com.imaee.propinq.auth.services.interfaces.IRecaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class RecaptchaService implements IRecaptchaService {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    // Configura esto en application.properties: google.recaptcha.secret=TU_CLAVE_SECRETA
    @Value("${google.recaptcha.secret:}")
    private String recaptchaSecret;

    private static final float THRESHOLD = 0.5f; // Score mínimo (0.0 a 1.0)

    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Intento de login sin token de reCAPTCHA");
            return false;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
            requestMap.add("secret", recaptchaSecret);
            requestMap.add("response", token);

            Map<String, Object> response = restTemplate.postForObject(RECAPTCHA_VERIFY_URL, requestMap, Map.class);
            log.info("RESPUESTA COMPLETA DE GOOGLE: {}", response);
            if (response == null) return false;

            boolean success = (Boolean) response.getOrDefault("success", false);
            Double score = (Double) response.getOrDefault("score", 0.0);

            log.info("reCAPTCHA resultado -> success: {}, score: {}", success, score);

            return success && score >= THRESHOLD;

        } catch (Exception e) {
            log.error("Error validando reCAPTCHA", e);
            return false;
        }
    }
}
