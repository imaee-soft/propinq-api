package com.imaee.propinq.shared.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

class EmailServiceResendTest {

    private static final String BASE_URL = "https://api.resend.com";
    private static final String API_KEY = "re_test_key";
    private static final String FROM = "PropInq <notificaciones@propinq.online>";

    private MockRestServiceServer server;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        emailService = new EmailService(builder, API_KEY, FROM, BASE_URL);
    }

    @Test
    void shouldPostEmailToResend_whenSendEmail() {
        server.expect(requestTo(BASE_URL + "/emails"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer " + API_KEY))
                .andExpect(jsonPath("$.from").value(FROM))
                .andExpect(jsonPath("$.to[0]").value("user@example.com"))
                .andExpect(jsonPath("$.subject").value("Recuperar contraseña"))
                .andExpect(jsonPath("$.html").value("<p>hola</p>"))
                .andRespond(withSuccess("{\"id\":\"email_1\"}", MediaType.APPLICATION_JSON));

        emailService.sendEmail("user@example.com", "Recuperar contraseña", "<p>hola</p>");

        server.verify();
    }

    @Test
    void shouldThrow503_whenResendRejectsCredentials() {
        server.expect(requestTo(BASE_URL + "/emails"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withUnauthorizedRequest());

        assertThatThrownBy(() -> emailService.sendEmail("user@example.com", "x", "<p>y</p>"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().value())
                .isEqualTo(503);

        server.verify();
    }
}
